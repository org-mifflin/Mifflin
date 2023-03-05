package com.dangerfield.features.matchmaker

import app.cash.turbine.test
import com.dangerfield.core.people.api.PeopleRepository
import com.dangerfield.core.people.api.Person
import com.dangerfield.core.people.api.ProfileSection.About
import com.dangerfield.core.people.api.ProfileSection.Gender
import com.dangerfield.core.people.api.ProfileSection.Hobbies
import com.dangerfield.core.people.api.ProfileSection.Name
import com.dangerfield.core.people.api.ProfileSection.Photo
import com.dangerfield.core.people.api.ProfileSection.School
import com.dangerfield.core.test.CoroutinesTestRule
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Empty
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Failed
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Idle
import com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Showing
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

    private val peopleRepository: PeopleRepository = mockk {
        coEvery { setPersonSeen(any()) } returns Unit
    }

    private val profileConfig: ProfileConfig = mockk {
        every { getProfileSectionOrder() } returns defaultProfileSectionOrder
    }

    private val analytics: MatchMakerAnalytics = mockk(relaxed = true)

    lateinit var viewModel: MatchMakerViewModel

    @Before
    fun setup() {
        viewModel = MatchMakerViewModel(peopleRepository, analytics, profileConfig)
    }

    @Test
    fun `GIVEN load succeeds, WHEN loading people, THEN response is reflected in vm`() = coroutineTestRule.test {
        val person = Person(about = "", gender = "", id = 0, name = "", photo = "", hobbies = listOf(), school = null)
        coEvery { peopleRepository.getNextPeople() } returns listOf(person)

        viewModel.stateStream.test {
            assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
            viewModel.loadPeople()
            val state = awaitItem()
            val peopleStatus = state.peopleStatus
            assertThat(peopleStatus is Showing && peopleStatus.potentialMatch == person).isTrue()
            verify { analytics.trackProfileImpression(person.id) }
            verify(exactly = 0) { analytics.trackNextClick() }
        }
    }

    @Test
    fun `GIVEN failure, WHEN loading people, THEN response is reflected in vm`() = coroutineTestRule.test {
        val error = Error()
        coEvery { peopleRepository.getNextPeople() } throws error

        viewModel.stateStream.test {
            assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
            viewModel.loadPeople()
            val state = awaitItem()
            val peopleStatus = state.peopleStatus
            assertThat(peopleStatus is Failed && peopleStatus.throwable == error).isTrue()
            verify(exactly = 0) { analytics.trackProfileImpression(any()) }
            verify(exactly = 0) { analytics.trackNextClick() }
        }
    }

    @Test
    fun `GIVEN loadNextPerson, WHEN there are people to show, THEN the next person should show`() =
        coroutineTestRule.test {

            coEvery { peopleRepository.getNextPeople() } returns listOf(fakePerson(1), fakePerson(2))

            viewModel.stateStream.test {
                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))

                viewModel.loadPeople()

                val state = awaitItem()
                val peopleStatus = state.peopleStatus
                assertThat(peopleStatus is Showing && peopleStatus.potentialMatch.id == 1).isTrue()

                viewModel.loadNextPerson(1)
                verify(exactly = 1) { analytics.trackNextClick() }

                val nextState = awaitItem()
                val nextPeopleStatus = nextState.peopleStatus
                assertThat(nextPeopleStatus is Showing && nextPeopleStatus.potentialMatch.id == 2).isTrue()
                verify(exactly = 2) { analytics.trackProfileImpression(any()) }
            }
        }

    @Test
    fun `GIVEN loadNextPerson, WHEN there are not people to show, THEN empty response is given`() =
        coroutineTestRule.test {
            coEvery { peopleRepository.getNextPeople() } returns listOf(fakePerson(1))

            viewModel.stateStream.test {

                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, defaultProfileSectionOrder))
                viewModel.loadPeople()

                val state = awaitItem()
                val peopleStatus = state.peopleStatus
                assertThat(peopleStatus is Showing && peopleStatus.potentialMatch.id == 1).isTrue()

                viewModel.loadNextPerson(1)
                verify(exactly = 1) { analytics.trackNextClick() }

                val nextState = awaitItem()
                assertThat(nextState.peopleStatus).isInstanceOf(Empty::class.java)
                verify(exactly = 1) { analytics.trackProfileImpression(any()) }
            }
        }

    @Test
    fun `GIVEN a profile order, WHEN loading people, THEN the profile order should e reflected in state`() =
        coroutineTestRule.test {
            val order = listOf(Hobbies, School, Name, Photo)
            every { profileConfig.getProfileSectionOrder() } returns order

            viewModel = MatchMakerViewModel(peopleRepository, analytics, profileConfig)

            coEvery { peopleRepository.getNextPeople() } returns listOf(fakePerson(1))

            viewModel.stateStream.test {
                assertThat(awaitItem()).isEqualTo(MatchMakerViewModel.State(Idle, order))
            }
        }

    private fun fakePerson(id: Int) =
        Person(about = "", gender = "", id = id, name = "", photo = "", hobbies = listOf(), school = null)
}
