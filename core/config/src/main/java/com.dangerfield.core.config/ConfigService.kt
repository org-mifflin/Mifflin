package com.dangerfield.core.config

import com.google.gson.JsonObject
import retrofit2.http.GET

/**
 * Responsible for fetching the config from the backend
 */
interface ConfigService {

    @GET("config")
    suspend fun getConfig(): ConfigResponse
}

typealias ConfigResponse = JsonObject
