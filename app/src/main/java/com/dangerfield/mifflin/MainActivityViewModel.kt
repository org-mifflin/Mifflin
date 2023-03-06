package com.dangerfield.mifflin

import com.dangerfield.core.config.AppConfigRepository
import com.dangerfield.core.ui.UdfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) : UdfViewModel<MainActivityViewModel.State, MainActivityViewModel.Action>() {

    override val initialState = State(isLoading = true)

    override fun transformActionFlow(actionFlow: Flow<Action>): Flow<State> {
        return actionFlow.flatMapMerge {
            flow {
                when (it) {
                    Action.InitializeApp -> handleAppInitialization()
                }
            }
        }
    }

    fun initializeApp() {
        submitAction(Action.InitializeApp)
    }

    private suspend fun FlowCollector<State>.handleAppInitialization() {
        appConfigRepository.refreshConfig()
        emit(state.copy(isLoading = false))
    }

    data class State(val isLoading: Boolean)

    sealed class Action {
        object InitializeApp : Action()
    }
}
