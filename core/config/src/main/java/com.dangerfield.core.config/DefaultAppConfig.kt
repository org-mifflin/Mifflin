package com.dangerfield.core.config

import android.content.Context
import com.dangerfield.core.config.api.AppConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Default implementation of the [AppConfig] class that pulls from the default config
 * located under assets/default_config.json
 *
 * must be loaded using the load function
 */
class DefaultAppConfig : AppConfig {

    private val map: MutableMap<String, Any> = mutableMapOf()

    /**
     * loads the default config from assets/default_config.json into the map
     */
    fun load(context: Context) {
        val inputStream = context.assets.open("default_config.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charsets.UTF_8)
        val config: Map<String, Any> = Gson().fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
        map.putAll(config)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> value(path: String): T? {
        return map[path] as? T
    }
}
