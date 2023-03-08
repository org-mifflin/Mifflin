package com.dangerfield.core.users

import api.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [UserAppModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object UserAppModule {

    @Module
    @DisableInstallInCheck
    interface Bindings {

        @Binds
        fun bindsUserRepository(impl: OfflineFirstUserRepository): UserRepository
    }
}
