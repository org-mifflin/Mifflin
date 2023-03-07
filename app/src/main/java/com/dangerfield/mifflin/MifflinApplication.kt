package com.dangerfield.mifflin

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.dangerfield.core.config.OfflineFirstAppConfigRepository
import com.dangerfield.features.matchmaker.ProfilePictureImageLoader
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject

@HiltAndroidApp
class MifflinApplication : Application(), ImageLoaderFactory {

    @Inject
    lateinit var offlineFirstAppConfigRepository: OfflineFirstAppConfigRepository

    @Inject
    lateinit var profilePictureImageLoader: ProfilePictureImageLoader

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(offlineFirstAppConfigRepository)

        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
    }

    override fun newImageLoader(): ImageLoader {
        return profilePictureImageLoader.imageLoader
    }
}
