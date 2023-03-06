package com.dangerfield.core.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.dangerfield.core.common.AppLifecycleScope
import com.dangerfield.core.common.DispatcherProvider
import com.dangerfield.core.common.ExponentialBackoff
import com.dangerfield.core.common.runCancellableCatching
import com.dangerfield.core.config.api.AppConfig
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

private val ConfigRefreshRate = 15.days // TODO change this back
private const val ConfigKey = "config"

class AppConfigRepository @Inject constructor(
    defaultAppConfig: DefaultAppConfig,
    private val dispatcherProvider: DispatcherProvider,
    private val appLifecycleScope: AppLifecycleScope,
    private val configService: ConfigService,
    private val dataStore: DataStore<Preferences>
) : DefaultLifecycleObserver {

    private var refreshJob: Job? = null

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private val configStream = dataStore.data
        .mapNotNull {
            it[stringPreferencesKey(ConfigKey)]?.let { configString ->
                val configMap = configString.parseConfigMap()
                MapAppConfig(configMap)
            }
        }
        .stateIn(
            appLifecycleScope,
            // triggers upstream immediately and never stops regardless of subscribers
            SharingStarted.Eagerly,
            initialValue = defaultAppConfig
        )

    /**
     * starts refreshing the app config on an interval
     */
    override fun onStart(owner: LifecycleOwner) {
        Timber.i("Starting app config refresh")
        refreshJob = appLifecycleScope.launch(dispatcherProvider.io) {
            while (isActive) {
                refreshConfig()
                delay(ConfigRefreshRate)
            }
        }
    }

    private fun stopAppConfigRefresh() {
        Timber.i("Stopping app config refresh")
        refreshJob?.cancel()
    }

    suspend fun refreshConfig(backoff: ExponentialBackoff? = null) {
        Timber.i("Refreshing the app config")
        val jsonConfig = runCancellableCatching(backoff) { configService.getConfig() }
            .onFailure { stopAppConfigRefresh() }
            .getOrNull()

        jsonConfig?.let { json ->
            Timber.i("Saving updated app config")
            dataStore.edit {
                it[stringPreferencesKey(ConfigKey)] = json.toString()
            }
        }
        Timber.i("Finished Saving updated app config")
    }

    fun config(): AppConfig = configStream.value

    fun configStream(): Flow<AppConfig> = configStream
}
