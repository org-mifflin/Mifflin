package com.dangerfield.core.config

import com.dangerfield.core.config.api.AppConfig
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface ConfigLocalDataSource {
    fun getConfigFlow(): Flow<AppConfig>
    suspend fun updateConfig(config: JsonObject)
}
