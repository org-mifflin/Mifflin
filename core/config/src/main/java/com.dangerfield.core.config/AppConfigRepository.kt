package com.dangerfield.core.config

import androidx.lifecycle.DefaultLifecycleObserver
import com.dangerfield.core.config.api.AppConfig
import kotlinx.coroutines.flow.Flow

/**
 * Repository to manage fetching and exposing the app config
 */
interface AppConfigRepository : DefaultLifecycleObserver {

    /**
     * Exposes the most recent app config
     */
    fun config(): AppConfig

    /**
     * Exposes the app config stream set to update on a cadence
     */
    fun configStream(): Flow<AppConfig>

    /**
     * Force refreshes the app config
     * this function is main safe and does not need context switching before calling
     */
    suspend fun refreshConfig()
}
