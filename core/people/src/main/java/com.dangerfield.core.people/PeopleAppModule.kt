package com.dangerfield.core.people

import com.dangerfield.core.people.api.PeopleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck

@Module(includes = [PeopleAppModule.Bindings::class])
@InstallIn(SingletonComponent::class)
object PeopleAppModule {

    @Module
    @DisableInstallInCheck
    interface Bindings {

        @Binds
        fun bindsPeopleRepository(impl: OfflineFirstPeopleRepository): PeopleRepository
    }
}
