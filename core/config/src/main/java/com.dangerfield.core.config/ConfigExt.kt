package com.dangerfield.core.config

import com.google.gson.Gson
import com.google.gson.JsonObject

fun ConfigResponse.parseMap(): Map<String, Any> {
    val gson = Gson()
    val jsonObject = gson.fromJson(this, JsonObject::class.java)
    return jsonObject.entrySet().associate { it.key to it.value }
}

fun String.parseConfigMap(): Map<String, Any> {
    val gson = Gson()
    val jsonObject = gson.fromJson(this, JsonObject::class.java)
    return jsonObject.entrySet().associate { it.key to it.value }
}
