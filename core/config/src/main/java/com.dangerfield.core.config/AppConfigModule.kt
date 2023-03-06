package com.dangerfield.core.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dangerfield.core.config.api.AppConfig
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppConfigModule {

    @Provides
    fun providesDefaultAppConfig(@ApplicationContext context: Context): DefaultAppConfig {
        val config = DefaultAppConfig()
        config.load(context)
        return config
    }

    @Provides
    fun providesAppConfigStream(appConfigRepository: AppConfigRepository): Flow<AppConfig> {
        return appConfigRepository.configStream()
    }

    @Provides
    fun providesAppConfig(appConfigRepository: AppConfigRepository): AppConfig {
        return appConfigRepository.config()
    }

    @Singleton
    @Provides
    fun providesConfigService(
        gson: Gson,
    ): ConfigService {

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BASIC
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        return Retrofit.Builder()
            .baseUrl("http://hinge-ue1-dev-cli-android-homework.s3-website-us-east-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(ConfigService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.configDatastore
    }

    private val Context.configDatastore: DataStore<Preferences> by preferencesDataStore(name = "mifflin_config")
}
