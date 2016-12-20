package com.gomart.guildbuddy.module;

import com.gomart.guildbuddy.ui.GuildMembersActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by glaubermartins on 2016-12-05.
 */

@Module
public class GuildModule {

    private final GuildMembersActivity activity;

    public GuildModule(GuildMembersActivity guildMembersActivity){
        this.activity = guildMembersActivity;
    }

    @Provides
    public GuildMembersActivity provideGuild(){
        return this.activity;
    }
}
