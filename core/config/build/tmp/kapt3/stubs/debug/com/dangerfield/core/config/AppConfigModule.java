package com.dangerfield.core.config;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007\u00a8\u0006\u0005"}, d2 = {"Lcom/dangerfield/core/config/AppConfigModule;", "", "()V", "providesAppConfig", "Lcom/dangerfield/core/config/api/AppConfig;", "config_debug"})
@dagger.Module()
public final class AppConfigModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.dangerfield.core.config.AppConfigModule INSTANCE = null;
    
    private AppConfigModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.dangerfield.core.config.api.AppConfig providesAppConfig() {
        return null;
    }
}