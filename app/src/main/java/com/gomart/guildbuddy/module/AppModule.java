package com.gomart.guildbuddy.module;

import com.gomart.guildbuddy.GuildBuddy;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by glaubermartins on 2016-12-06.
 */

@Module
public class AppModule {
    private final GuildBuddy app;

    public AppModule(GuildBuddy app){
        this.app = app;
    }

    @Provides
    @Singleton
    public GuildBuddy app(){
        return this.app;
    }
}
