package com.dangerfield.core.config

import com.dangerfield.core.config.api.AppConfig
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction to encapsulate the logic behind the local storage of the app config.
 *
 */
interface ConfigLocalDataSource {
    /**
     * @return a flow that emits a value for every update to the local app config
     */
    fun getConfigFlow(): Flow<AppConfig>

    /**
     * updates the locally stored app config
     */
    suspend fun updateConfig(config: JsonObject)
}
