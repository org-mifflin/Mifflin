package com.dangerfield.core.config

import com.dangerfield.core.config.api.AppConfig
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeConfigLocalDataSource : ConfigLocalDataSource {

    private val jsonAdapter = Gson().getAdapter(object : TypeToken<Map<String, Any>>() {})

    private val flow = MutableSharedFlow<AppConfig>()

    override fun getConfigFlow(): Flow<AppConfig> = flow

    override suspend fun updateConfig(config: JsonObject) {
        val configMap = jsonAdapter.fromJson(config.toString())
        flow.emit(MapAppConfig(configMap))
    }
}
