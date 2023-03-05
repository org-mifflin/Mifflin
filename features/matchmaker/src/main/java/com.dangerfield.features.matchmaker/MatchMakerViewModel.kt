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
    profileConfig: ProfileConfig
) : UdfViewModel<MatchMakerViewModel.State, MatchMakerViewModel.Action>() {

    private val potentialMatches: Queue<Person> = LinkedList()

    override val initialState = State(PeopleStatus.Idle, profileConfig.getProfileSectionOrder())

    override val initialAction = Action.LoadPeople

    override fun transformActionFlow(actionFlow: Flow<Action>): Flow<State> {
        return actionFlow.flatMapMerge {
            flow {
                when (it) {
                    Action.LoadPeople -> handleLoadPeople()
                    is Action.LoadNextPerson -> handleLoadNextPerson(it.previousPersonId)
                    Action.SetErrorHandled -> emit(state.copy(peopleStatus = PeopleStatus.Idle))
                }
            }
        }
    }

    fun loadNextPerson(previousPersonId: Int) {
        submitAction(Action.LoadNextPerson(previousPersonId))
    }

    fun loadPeople() {
        submitAction(Action.LoadPeople)
    }

    fun onErrorHandled() {
        submitAction(Action.SetErrorHandled)
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
