package com.dangerfield.core.config

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import com.dangerfield.core.common.DispatcherProvider
import com.dangerfield.core.common.runCancellableCatching
import com.dangerfield.core.config.api.AppConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@VisibleForTesting
val ConfigRefreshRate = 15.seconds // TODO change this back
private val ConfigRefreshTimeout = 10.seconds

/**
 * Repository responsible for exposing the app config and a method to refresh it.
 * Starts refreshing the app config on a set cadence (short polling) upon app start
 * and stops when the app lifecycle stops.
 *
 * internally backed by datastore, allowing for offline support.
 *
 * NOTE:
 * The interface is not in the api because this class should not be injected for most usecases.
 * Rather prefer to inject the app config or the flow of app config.
 */
@Singleton
class OfflineFirstAppConfigRepository @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val configService: ConfigService,
    private val configLocalDataSource: ConfigLocalDataSource,
    @Named(FallbackConfigName) private val fallbackConfig: AppConfig,
) : AppConfigRepository {

    private val appLifecycleScope: CoroutineScope = CoroutineScope(dispatcherProvider.io)

    private val configStream = configLocalDataSource.getConfigFlow()
        .stateIn(
            appLifecycleScope,
            // triggers upstream immediately and never stops regardless of subscribers
            SharingStarted.Eagerly,
            initialValue = fallbackConfig
        )

    /**
     * Supplies the app config value
     */
    override fun config(): AppConfig = configStream.value

    /**
     * Supplies the app config stream, updates on a set cadence (short polling)
     */
    override fun configStream(): Flow<AppConfig> = configStream

    override fun onStart(owner: LifecycleOwner) {
        startAppConfigRefresh()
    }

    override fun onStop(owner: LifecycleOwner) {
        Timber.i("Starting app config refresh")
        stopAppConfigRefresh()
    }

    /**
     * starts refreshing the app config on an interval
     */
    private fun startAppConfigRefresh() {
        appLifecycleScope.launch(dispatcherProvider.io) {
            while (isActive) {
                refreshConfig()
                delay(ConfigRefreshRate)
            }
        }
    }

    /**
     * stops refreshing the app config
     */
    private fun stopAppConfigRefresh() {
        Timber.i("Stopping app config refresh")
        appLifecycleScope.cancel()
    }

    /**
     * refreshes the app config stored in datastore from the backend
     */
    override suspend fun refreshConfig() {
        Timber.i("Refreshing the app config")
        val jsonConfig = runCancellableCatching {
            withTimeout(ConfigRefreshTimeout) {
                configService.getConfig()
            }
        }
            .onFailure { Timber.e("App Config Error", it) }
            .getOrNull()

        jsonConfig?.let { json ->
            Timber.i("Saving updated app config: $json")
            configLocalDataSource.updateConfig(json)
        }
    }
}
