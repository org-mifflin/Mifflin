package com.dangerfield.core.config

import com.dangerfield.core.config.api.AppConfig

/**
 * App config implementation based on simple map passed as input
 */
class MapAppConfig(private val map: Map<String, Any> = mutableMapOf()) : AppConfig {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> value(path: String): T? {
        return map[path] as? T
    }
}

/*
so how do I want to store the app config?

String -> JsonString
or String -> Any

well if im storing it in Datastore its gotta be a json object or I have to use protobuf. And I dont have time for that.

so deal the actual storage will be String -> JsonString (String -> String)
but the actual app config map will be String -> Any.
so when building it, these things need to be parsed?  how can they be parsed when we dont know what the object type is
they have to be parsed as they are retrived. So then the actual map is String -> Json String


 */
