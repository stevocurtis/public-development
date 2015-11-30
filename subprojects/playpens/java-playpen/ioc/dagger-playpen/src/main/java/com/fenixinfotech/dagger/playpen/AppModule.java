package com.fenixinfotech.dagger.playpen;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class AppModule
{
    InjectMe injectMe;

    public AppModule(InjectMe injectMe)
    {
        this.injectMe = injectMe;
    }

    @Provides
    @Singleton
    InjectMe providesInjectMe()
    {
        return injectMe;
    }
}
