package com.dangerfield.core.config

import com.google.gson.JsonObject
import retrofit2.http.GET

interface ConfigService {

    @GET("config")
    suspend fun getConfig(): ConfigResponse
}

typealias ConfigResponse = JsonObject
