package com.dangerfield.core.config

import com.dangerfield.core.config.api.AppConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Provides
    fun providesAppConfig(): AppConfig = DefaultAppConfig(
        mapOf(
            "profiles" to listOf("name", "photo", "gender", "about", "school", "hobbies")
        )
    )
}
