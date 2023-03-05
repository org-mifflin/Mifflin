package com.dangerfield.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

const val StateStreamTimeoutNoSub = 5_000L

abstract class UdfViewModel<STATE, ACTION> : ViewModel() {

    private val actionStream = MutableSharedFlow<ACTION>()

    protected abstract val initialState: STATE

    protected fun submitAction(action: ACTION) = viewModelScope.launch(Dispatchers.Main) {
        actionStream.emit(action)
    }

    val state: STATE
        get() = stateStream.value

    val stateStream by lazy {
        transformActionFlow(actionStream)
            .onEach { println("emitting item $it") }
            .catch { println("state stream emitted an error ${it.message}") }
            .onCompletion { println("state stream unexpectedly completed") }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(StateStreamTimeoutNoSub),
                initialState
            )
    }

    suspend fun waitForState(predicate: (STATE) -> Boolean): STATE = stateStream.first { predicate(it) }

    protected abstract fun transformActionFlow(actionFlow: Flow<ACTION>): Flow<STATE>
}
