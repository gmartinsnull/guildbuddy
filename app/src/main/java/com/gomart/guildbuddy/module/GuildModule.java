package com.gomart.guildbuddy.module;

import com.gomart.guildbuddy.model.Guild;

import java.io.Serializable;
import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by glaubermartins on 2016-12-05.
 */

@Module
public class GuildModule {

    private final Guild guild;

    public GuildModule(Guild guild){
        this.guild = guild;
    }

    @Provides
    public Guild provideGuild(){
        return this.guild;
    }
}
