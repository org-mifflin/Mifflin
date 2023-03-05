package com.dangerfield.core.people;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001:\u0001\u0003B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/dangerfield/core/people/PeopleAppModule;", "", "()V", "Bindings", "people_debug"})
@dagger.Module(includes = {com.dangerfield.core.people.PeopleAppModule.Bindings.class})
public final class PeopleAppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.dangerfield.core.people.PeopleAppModule INSTANCE = null;
    
    private PeopleAppModule() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'\u00a8\u0006\u0006"}, d2 = {"Lcom/dangerfield/core/people/PeopleAppModule$Bindings;", "", "bindsPeopleRepository", "Lcom/dangerfield/core/people/api/PeopleRepository;", "impl", "Lcom/dangerfield/core/people/OfflineFirstPeopleRepository;", "people_debug"})
    @dagger.hilt.migration.DisableInstallInCheck()
    @dagger.Module()
    public static abstract interface Bindings {
        
        @org.jetbrains.annotations.NotNull()
        @dagger.Binds()
        public abstract com.dangerfield.core.people.api.PeopleRepository bindsPeopleRepository(@org.jetbrains.annotations.NotNull()
        com.dangerfield.core.people.OfflineFirstPeopleRepository impl);
    }
}