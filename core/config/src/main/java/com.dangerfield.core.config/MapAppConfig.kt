package com.dangerfield.core.config

import com.dangerfield.core.config.api.AppConfig

/**
 * App config implementation based on simple map passed as input
 */
class MapAppConfig(private val map: Map<String, Any> = mutableMapOf()) : AppConfig {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> value(path: String): T? = map[path] as? T
}
