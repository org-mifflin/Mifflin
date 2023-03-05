package com.dangerfield.core.config

import com.dangerfield.core.config.api.AppConfig

data class DefaultAppConfig(private val map: Map<String, Any>) : AppConfig {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> value(path: String): T? {
        return map[path] as? T
    }
}
