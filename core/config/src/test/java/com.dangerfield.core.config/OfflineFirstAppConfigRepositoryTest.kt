package com.dangerfield.core.config

import app.cash.turbine.test
import com.dangerfield.core.test.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class OfflineFirstAppConfigRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val configService: ConfigService = mockk()
    private val fallbackConfig = MapAppConfig()

    lateinit var dataSource: FakeConfigLocalDataSource
    lateinit var appConfigRepository: OfflineFirstAppConfigRepository

    @Before
    fun setup() {
        dataSource = FakeConfigLocalDataSource()

        appConfigRepository = OfflineFirstAppConfigRepository(
            dispatcherProvider = coroutineTestRule.dispatcherProvider,
            configService = configService,
            configLocalDataSource = dataSource,
            fallbackConfig = fallbackConfig,
        )
    }

    @Test
    fun `GIVEN refresh fails, WHEN no config has been loaded, THEN fallback is the last value`() =
        coroutineTestRule.test {
            coEvery { configService.getConfig() } throws Error()

            appConfigRepository.configStream().test {

                assertThat(awaitItem()).isEqualTo(fallbackConfig)

                appConfigRepository.refreshConfig()

                assertThat(appConfigRepository.config()).isEqualTo(fallbackConfig)

                coVerify(exactly = 1) { configService.getConfig() }
            }
        }

    @Test
    fun `GIVEN refresh succeeds, WHEN a config is fetched, THEN remote config should be given`() =
        coroutineTestRule.test {
            val remoteSectionOrder = listOf("hobbies", "name", "gender", "photo", "school")
            coEvery { configService.getConfig() } returns profileJsonObject(remoteSectionOrder) andThenThrows Error()

            appConfigRepository.configStream().test {

                assertThat(awaitItem()).isEqualTo(fallbackConfig)

                appConfigRepository.refreshConfig()

                val fetchedConfig = awaitItem()
                assertThat(fetchedConfig.value<List<String>>("profile")).isEqualTo(remoteSectionOrder)

                coVerify(exactly = 1) { configService.getConfig() }
            }
        }

    @Test
    fun `GIVEN refresh succeeds, WHEN remote config changes, THEN new config should be produced`() =
        coroutineTestRule.test {
            val remoteSectionOrder1 = listOf("hobbies", "name", "gender", "photo", "school")
            val remoteSectionOrder2 = listOf("name", "hobbies", "gender", "photo", "school")

            coEvery {
                configService.getConfig()
            } returns profileJsonObject(remoteSectionOrder1) andThen profileJsonObject(remoteSectionOrder2)

            appConfigRepository.configStream().test {

                assertThat(awaitItem()).isEqualTo(fallbackConfig)

                appConfigRepository.refreshConfig()

                val fetchedConfig1 = awaitItem()
                assertThat(fetchedConfig1.value<List<String>>("profile")).isEqualTo(remoteSectionOrder1)

                appConfigRepository.refreshConfig()

                val fetchedConfig2 = awaitItem()
                assertThat(fetchedConfig2.value<List<String>>("profile")).isEqualTo(remoteSectionOrder2)

                coVerify(exactly = 2) { configService.getConfig() }
            }
        }

    @Test
    fun `GIVEN config starts refreshing, WHEN 5 intervals pass, THEN config should fetch 5 times`() =
        coroutineTestRule.test {
            val expectedRefreshCount = 5
            val refreshDelayMs = ConfigRefreshRate.inWholeMilliseconds
            val refreshIntervalMs = refreshDelayMs * expectedRefreshCount

            coEvery {
                configService.getConfig()
            } returns profileJsonObject(listOf("name", "hobbies", "gender", "photo", "school"))

            // data source updates will suspend until collected so we must observe the stream
            appConfigRepository.configStream().test {

                appConfigRepository.onStart(mockk())
                advanceTimeBy(refreshIntervalMs)
                appConfigRepository.onStop(mockk())

                coVerify(exactly = expectedRefreshCount) { configService.getConfig() }
                this.cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN config starts refreshing, WHEN 100 intervals pass, THEN config should fetch 100 times`() =
        coroutineTestRule.test {
            val expectedRefreshCount = 100
            val refreshDelayMs = ConfigRefreshRate.inWholeMilliseconds
            val refreshIntervalMs = refreshDelayMs * expectedRefreshCount

            coEvery {
                configService.getConfig()
            } returns profileJsonObject(listOf("name", "hobbies", "gender", "photo", "school"))

            // data source updates will suspend until collected so we must observe the stream
            appConfigRepository.configStream().test {

                appConfigRepository.onStart(mockk())
                advanceTimeBy(refreshIntervalMs)
                appConfigRepository.onStop(mockk())

                coVerify(exactly = expectedRefreshCount) { configService.getConfig() }
                this.cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `GIVEN fetch times out, WHEN previous config has been fetched, THEN no new config should be produced `() =
        coroutineTestRule.test {
            val previouslyStoredSectionOrder = listOf("hobbies", "name", "gender", "photo", "school")
            val remoteOrder = listOf("name", "hobbies", "gender", "photo", "school")

            coEvery { configService.getConfig() } coAnswers {
                delay(11_000)
                profileJsonObject(remoteOrder)
            }

            appConfigRepository.configStream().test {

                assertThat(awaitItem()).isEqualTo(fallbackConfig)

                // data source emits previously stored data
                dataSource.updateConfig(profileJsonObject(previouslyStoredSectionOrder))

                val fetchedConfig = awaitItem()
                assertThat(fetchedConfig.value<List<String>>("profile")).isEqualTo(previouslyStoredSectionOrder)

                backgroundScope.launch {
                    appConfigRepository.refreshConfig()
                }

                advanceTimeBy(15_000)

                // no other items should be emitted as the timeout failure should have been hit
                expectNoEvents()
                assertThat(
                    appConfigRepository.config().value<List<String>>("profile")
                ).isEqualTo(previouslyStoredSectionOrder)
            }
        }

    @Test
    fun `GIVEN fetch times out, WHEN no previous config has been fetched, THEN no new config should be produced `() =
        coroutineTestRule.test {
            val remoteOrder = listOf("name", "hobbies", "gender", "photo", "school")

            coEvery { configService.getConfig() } coAnswers {
                delay(11_000)
                profileJsonObject(remoteOrder)
            }

            appConfigRepository.configStream().test {

                assertThat(awaitItem()).isEqualTo(fallbackConfig)

                backgroundScope.launch { appConfigRepository.refreshConfig() }

                advanceTimeBy(15_000)

                // no other items should be emitted as the timeout failure should have been hit
                expectNoEvents()
                assertThat(appConfigRepository.config()).isEqualTo(fallbackConfig)
            }
        }

    private fun profileJsonObject(sectionOrder: List<String>) = JsonObject().apply {
        this.add(
            "profile",
            JsonArray().apply {
                sectionOrder.forEach { add(it) }
            }
        )
    }
}
