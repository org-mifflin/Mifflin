package com.dangerfield.core.config.api

/**
 * app configuration back by a simple map.
 *
 * usage:
 *  val result = appConfig.value<List<String>>(KEY)
 */
interface AppConfig {
    /**
     * Get the value with a given path from the app config
     */
    fun <T : Any> value(path: String): T?
}
