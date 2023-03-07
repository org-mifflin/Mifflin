package com.dangerfield.mifflin

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.dangerfield.core.config.OfflineFirstAppConfigRepository
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject

@HiltAndroidApp
class MifflinApplication : Application() {

    @Inject
    lateinit var offlineFirstAppConfigRepository: OfflineFirstAppConfigRepository

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(offlineFirstAppConfigRepository)

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }
}
