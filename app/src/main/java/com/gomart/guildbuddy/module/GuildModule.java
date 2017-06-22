package com.gomart.guildbuddy.module;

import android.content.Context;

import com.gomart.guildbuddy.model.Guild;
import com.gomart.guildbuddy.ui.presenter.GuildPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by glaubermartins on 2016-12-05.
 */

@Module
public class GuildModule {

    private Guild guild;
    private Context context;

    public GuildModule(Context context, Guild guild){
        this.guild = guild;
        this.context = context;
    }

    @Singleton
    @Provides
    GuildPresenter provideGuildPresenter(){
        return new GuildPresenter(context, guild);
    }
}
