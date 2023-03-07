package com.dangerfield.core.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dangerfield.core.config.api.AppConfig
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

private const val ConfigKey = "config"

/**
 * data store implementation of [ConfigLocalDataSource]
 */
class DataStoreConfigDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    gson: Gson
) : ConfigLocalDataSource {

    private val jsonAdapter = gson.getAdapter(object : TypeToken<Map<String, Any>>() {})

    override fun getConfigFlow(): Flow<AppConfig> = dataStore.data
        .mapNotNull {
            it[stringPreferencesKey(ConfigKey)]?.let { configJsonString ->
                val configMap = jsonAdapter.fromJson(configJsonString)
                MapAppConfig(configMap)
            }
        }

    override suspend fun updateConfig(config: JsonObject) {
        dataStore.edit {
            it[stringPreferencesKey(ConfigKey)] = config.toString()
        }
    }
}
