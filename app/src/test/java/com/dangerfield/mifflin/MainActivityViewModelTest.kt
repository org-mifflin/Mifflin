package com.dangerfield.mifflin

import app.cash.turbine.test
import com.dangerfield.core.config.AppConfigRepository
import com.dangerfield.core.test.CoroutinesTestRule
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val appConfigRepository: AppConfigRepository = mockk {
        coEvery { refreshConfig() } just Runs
    }

    lateinit var viewModel: MainActivityViewModel

    @Before
    fun setup() {
        viewModel = MainActivityViewModel(appConfigRepository)
    }

    @Test
    fun `GIVEN app, WHEN initialized, THEN refresh config is called and loading is set to false`() =
        coroutineTestRule.test {
            viewModel.stateStream.test {
                assertThat(awaitItem()).isEqualTo(MainActivityViewModel.State(isLoading = true))
                viewModel.initializeApp()
                val state = awaitItem()
                assertThat(state.isLoading).isFalse()
                coVerify(exactly = 1) { appConfigRepository.refreshConfig() }
            }
        }
}
