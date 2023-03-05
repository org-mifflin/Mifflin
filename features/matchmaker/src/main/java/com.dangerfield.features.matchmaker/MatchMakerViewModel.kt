package com.dangerfield.features.matchmaker

import com.dangerfield.core.common.runCancellableCatching
import com.dangerfield.core.people.api.PeopleRepository
import com.dangerfield.core.people.api.Person
import com.dangerfield.core.people.api.ProfileSection
import com.dangerfield.core.ui.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

@HiltViewModel
class MatchMakerViewModel @Inject constructor(
    private val peopleRepository: PeopleRepository,
    private val analytics: MatchMakerAnalytics,
    profileConfig: ProfileConfig
) : UdfViewModel<MatchMakerViewModel.State, MatchMakerViewModel.Action>() {

    private val potentialMatches: Queue<Person> = LinkedList()

    override val initialState = State(PeopleStatus.Idle, profileConfig.getProfileSectionOrder())

    override fun transformActionFlow(actionFlow: Flow<Action>): Flow<State> {
        return actionFlow.flatMapMerge {
            flow {
                when (it) {
                    Action.LoadPeople -> handleLoadPeople()
                    is Action.LoadNextPerson -> handleLoadNextPerson(it.previousPersonId)
                    Action.SetErrorHandled -> emit(state.copy(peopleStatus = PeopleStatus.Idle))
                    is Action.TrackProfileScroll -> analytics.trackProfileScroll(it.scrollPercent, it.id)
                }
            }
        }
    }

    /**
     * Loads the next person from the queue into the view state
     * @param previousPersonId the id of the previous person shown before the next person
     */
    fun loadNextPerson(previousPersonId: Int) {
        analytics.trackNextClick()
        submitAction(Action.LoadNextPerson(previousPersonId))
    }

    /**
     * Loads people from the repository
     */
    fun loadPeople() {
        submitAction(Action.LoadPeople)
    }

    /**
     * Marks the People Status error as handled to remove it from the state.
     * see: https://developer.android.com/topic/architecture/ui-layer/events
     */
    fun onErrorHandled() {
        submitAction(Action.SetErrorHandled)
    }

    /**
     * Tracks the profile scroll percentage of a profile
     * @param percentScroll the percentage the user has scrolled (0 = top, 100 = bottom)
     * @param id the id of the profile being scrolled on
     *
     * This function is an unfortunate workaround to not being able to inject analytics into the view. This is not
     * inherently the view models responsibility.
     */
    fun trackProfileScroll(percentScroll: Int, id: Int) {
        submitAction(Action.TrackProfileScroll(percentScroll, id))
    }

    private suspend fun FlowCollector<State>.handleLoadPeople() {
        emit(state.copy(peopleStatus = PeopleStatus.Loading))
        runCancellableCatching {
            val people = peopleRepository.getNextPeople()
            potentialMatches.addAll(people)
        }
            .onFailure { emit(state.copy(peopleStatus = PeopleStatus.Failed(it))) }
            .onSuccess { pollPersonQueue() }
    }

    private suspend fun FlowCollector<State>.pollPersonQueue() {
        val peopleStatus = potentialMatches.poll()?.let { PeopleStatus.Showing(it) } ?: PeopleStatus.Empty
        if (peopleStatus is PeopleStatus.Showing) {
            analytics.trackProfileImpression(peopleStatus.potentialMatch.id)
        }
        emit(state.copy(peopleStatus = peopleStatus))
    }

    private suspend fun FlowCollector<State>.handleLoadNextPerson(previousPersonId: Int) {
        peopleRepository.setPersonSeen(previousPersonId)
        pollPersonQueue()
    }

    sealed class Action {
        object LoadPeople : Action()
        class LoadNextPerson(val previousPersonId: Int) : Action()
        object SetErrorHandled : Action()
        class TrackProfileScroll(val scrollPercent: Int, val id: Int) : Action()
    }

    sealed class PeopleStatus {
        object Idle : PeopleStatus()
        object Loading : PeopleStatus()
        object Empty : PeopleStatus()
        class Failed(val throwable: Throwable) : PeopleStatus()
        class Showing(val potentialMatch: Person) : PeopleStatus()
    }

    data class State(
        val peopleStatus: PeopleStatus,
        val profileOrder: List<ProfileSection>,
    )
}
