package com.dangerfield.core.common

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import timber.log.Timber
import javax.inject.Inject

class AppLifecycleScope @Inject constructor(
    dispatcherProvider: DispatcherProvider
) : CoroutineScope, DefaultLifecycleObserver {

    private val job = SupervisorJob()
    override val coroutineContext = dispatcherProvider.main + job

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        job.invokeOnCompletion { Timber.i("App Lifecycle Job Completed") }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        job.cancelChildren()
    }
}
