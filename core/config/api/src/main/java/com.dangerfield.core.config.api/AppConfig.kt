package com.dangerfield.core.config.api

interface AppConfig {
    /**
     * Get the value with a given path from the app config
     */
    fun <T : Any> value(path: String): T?
}
