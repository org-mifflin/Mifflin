package com.dangerfield.features.matchmaker

import api.ProfileSection.About
import api.ProfileSection.Gender
import api.ProfileSection.Hobbies
import api.ProfileSection.Name
import api.ProfileSection.Photo
import api.ProfileSection.School
import api.User
import api.UserRepository
import app.cash.turbine.test
import com.dangerfield.core.test.CoroutinesTestRule
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Empty
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Failed
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Idle
import com.dangerfield.features.matchmaker.MatchMakerViewModel.UserResult.Loaded
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val defaultProfileSectionOrder = listOf(Photo, Name, About, Gender, School, Hobbies)

class MatchMakerViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private val userRepository: UserRepository = mockk {
        coEvery { setUserSeen(any()) } returns Unit
    }

    private val profileConfig: ProfileConfig = mockk {
        every { getProfileSectionOrder() } returns defaultProfileSectionOrder
    }

    private val analytics: MatchMakerAnalytics = mockk(relaxed = true)

    lateinit var viewModel: MatchMakerViewModel

    @Before
    fun setup() {
        viewModel = MatchMakerViewModel(userRepository, analytics, profileConfig)
    }

    @Test
    fun `GIVEN load succeeds, WHEN loading users, THEN response is reflected in vm`() = coroutineTestRule.test {
        val user = User(about = "", gender = "", id = 0, name = "", photo = "", hobbies = listOf(), school = null)
        coEvery { userRepository.getNextUsers() } returns listOf(user)

        viewModel.stateStream.test {
            assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
            viewModel.loadUsers()
            val state = awaitItem()
            val userResult = state.userResult
            assertThat(userResult is Loaded && userResult.user == user).isTrue()
            verify { analytics.trackProfileImpression(user.id) }
            verify(exactly = 0) { analytics.trackNextClick() }
        }
    }

    @Test
    fun `GIVEN failure, WHEN loading users, THEN response is reflected in vm`() = coroutineTestRule.test {
        val error = Error()
        coEvery { userRepository.getNextUsers() } throws error

        viewModel.stateStream.test {
            assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
            viewModel.loadUsers()
            val state = awaitItem()
            val userResult = state.userResult
            assertThat(userResult is Failed && userResult.throwable == error).isTrue()
            verify(exactly = 0) { analytics.trackProfileImpression(any()) }
            verify(exactly = 0) { analytics.trackNextClick() }
        }
    }

    @Test
    fun `GIVEN loadNextPerson, WHEN there are users to show, THEN the next person should show`() =
        coroutineTestRule.test {

            coEvery { userRepository.getNextUsers() } returns listOf(fakePerson(1), fakePerson(2))

            viewModel.stateStream.test {
                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))

                viewModel.loadUsers()

                val state = awaitItem()
                val userResult = state.userResult
                assertThat(userResult is Loaded && userResult.user.id == 1).isTrue()

                viewModel.loadNextUser(1)
                verify(exactly = 1) { analytics.trackNextClick() }

                val nextState = awaitItem()
                val nextPeopleStatus = nextState.userResult
                assertThat(nextPeopleStatus is Loaded && nextPeopleStatus.user.id == 2).isTrue()
                verify(exactly = 2) { analytics.trackProfileImpression(any()) }
            }
        }

    @Test
    fun `GIVEN loadNextPerson, WHEN there are not users to show, THEN empty response is given`() =
        coroutineTestRule.test {
            coEvery { userRepository.getNextUsers() } returns listOf(fakePerson(1))

            viewModel.stateStream.test {

                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
                viewModel.loadUsers()

                val state = awaitItem()
                val userResult = state.userResult
                assertThat(userResult is Loaded && userResult.user.id == 1).isTrue()

                viewModel.loadNextUser(1)
                verify(exactly = 1) { analytics.trackNextClick() }

                val nextState = awaitItem()
                assertThat(nextState.userResult).isInstanceOf(Empty::class.java)
                verify(exactly = 1) { analytics.trackProfileImpression(any()) }
            }
        }

    @Test
    fun `GIVEN a profile order, WHEN loading users, THEN the profile order should e reflected in state`() =
        coroutineTestRule.test {
            val order = listOf(Hobbies, School, Name, Photo)
            every { profileConfig.getProfileSectionOrder() } returns order

            viewModel = MatchMakerViewModel(userRepository, analytics, profileConfig)

            coEvery { userRepository.getNextUsers() } returns listOf(fakePerson(1))

            viewModel.stateStream.test {
                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, order))
            }
        }

    private fun fakePerson(id: Int) =
        User(about = "", gender = "", id = id, name = "", photo = "", hobbies = listOf(), school = null)
}
