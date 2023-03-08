package com.dangerfield.core.config

import android.content.Context
import com.dangerfield.core.config.api.AppConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Default implementation of the [AppConfig] class that pulls from the default config
 * located under assets/default_config.json
 **/
class DefaultConfig @Inject constructor(
    @ApplicationContext context: Context,
    gson: Gson
) : AppConfig {

    private val jsonAdapter = gson.getAdapter(object : TypeToken<Map<String, Any>>() {})

    @Suppress("TooGenericExceptionCaught")
    private val map: Map<String, Any> by lazy {
        try {
            val inputStream = context.assets.open("default_config.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            jsonAdapter.fromJson(json)
        } catch (e: Exception) {
            Timber.e(e)
            emptyMap()
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> value(path: String): T? {
        return map[path] as? T
    }
}
