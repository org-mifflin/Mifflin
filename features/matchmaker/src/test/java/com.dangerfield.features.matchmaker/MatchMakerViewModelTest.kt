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
        every { profileSectionOrder } returns defaultProfileSectionOrder
    }

    private val analytics: MatchMakerAnalytics = mockk(relaxed = true)

    private val profilePictureImageLoader: ProfilePictureImageLoader = mockk(relaxed = true)

    lateinit var viewModel: MatchMakerViewModel

    @Before
    fun setup() {
        viewModel = MatchMakerViewModel(userRepository, analytics, profilePictureImageLoader, profileConfig)
    }

    @Test
    fun `GIVEN load succeeds, WHEN loading users, THEN response is reflected in vm`() = coroutineTestRule.test {
        val url1 = "url1"
        val url2 = "url2"

        val user = User(
            about = "I like blackjack and making chili",
            gender = "Male",
            id = 0,
            name = "Kevin",
            photo = url1,
            hobbies = listOf("skittles"),
            school = null
        )

        val user2 = User(
            about = "I like blackjack and making chili",
            gender = "Male",
            id = 0,
            name = "Kevin",
            photo = url2,
            hobbies = listOf("skittles"),
            school = null
        )

        coEvery { userRepository.getNextUsers() } returns listOf(user, user2)

        viewModel.stateStream.test {
            assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
            viewModel.loadUsers()
            val state = awaitItem()
            val userResult = state.userResult
            assertThat(userResult is Loaded && userResult.user == user).isTrue()
            verify { analytics.trackProfileImpression(user.id) }
            verify { profilePictureImageLoader.prefetchImages(listOf(url1, url2)) }
            verify(exactly = 0) { analytics.trackNextClick() }
        }
    }

    @Test
    fun `GIVEN load succeeds, WHEN clicking next, THEN image cache is cleared`() = coroutineTestRule.test {
        val url1 = "url1"
        val url2 = "url2"

        val user = User(
            about = "I like blackjack and making chili",
            gender = "Male",
            id = 0,
            name = "Kevin",
            photo = url1,
            hobbies = listOf("skittles"),
            school = null
        )

        val user2 = User(
            about = "I like blackjack and making chili",
            gender = "Male",
            id = 0,
            name = "Kevin",
            photo = url2,
            hobbies = listOf("skittles"),
            school = null
        )

        coEvery { userRepository.getNextUsers() } returns listOf(user, user2)

        viewModel.stateStream.test {

            assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))

            viewModel.loadUsers()

            val state = awaitItem()
            val userResult = state.userResult
            assertThat(userResult is Loaded && userResult.user == user).isTrue()

            verify { analytics.trackProfileImpression(user.id) }
            verify { profilePictureImageLoader.prefetchImages(listOf(url1, url2)) }
            verify(exactly = 0) { analytics.trackNextClick() }

            viewModel.loadNextUser(user)

            val state2 = awaitItem()
            val userResult2 = state2.userResult
            assertThat(userResult2 is Loaded && userResult2.user == user2).isTrue()

            verify { profilePictureImageLoader.deleteImage(url1) }
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
            verify(exactly = 0) { profilePictureImageLoader.prefetchImages(any()) }
            verify(exactly = 0) { analytics.trackNextClick() }
        }
    }

    @Test
    fun `GIVEN loadNextPerson, WHEN there are users to show, THEN the next person should show`() =
        coroutineTestRule.test {

            val user1 = fakePerson(1)
            val user2 = fakePerson(2)
            coEvery { userRepository.getNextUsers() } returns listOf(user1, user2)

            viewModel.stateStream.test {
                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))

                viewModel.loadUsers()

                val state = awaitItem()
                val userResult = state.userResult
                assertThat(userResult is Loaded && userResult.user.id == 1).isTrue()

                viewModel.loadNextUser(previousUser = user1)
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
            val user1 = fakePerson(1)
            coEvery { userRepository.getNextUsers() } returns listOf(user1)

            viewModel.stateStream.test {

                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
                viewModel.loadUsers()

                val state = awaitItem()
                val userResult = state.userResult
                assertThat(userResult is Loaded && userResult.user.id == 1).isTrue()

                viewModel.loadNextUser(user1)
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
            every { profileConfig.profileSectionOrder } returns order

            viewModel = MatchMakerViewModel(userRepository, analytics, profilePictureImageLoader, profileConfig)

            coEvery { userRepository.getNextUsers() } returns listOf(fakePerson(1))

            viewModel.stateStream.test {
                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, order))
            }
        }

    private fun fakePerson(id: Int) =
        User(
            about = "I like long walks on the beach, music, water, squirrels.... and I might be a dog",
            gender = "dog",
            id = id,
            name = "doggo",
            photo = "url$id",
            hobbies = listOf("Going for walks", "eating"),
            school = "Harvard"
        )
}
