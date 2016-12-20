package com.gomart.guildbuddy.component;

import com.gomart.guildbuddy.GuildBuddy;
import com.gomart.guildbuddy.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by glaubermartins on 2016-12-06.
 */

@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {
    void inject(GuildBuddy app);
    GuildBuddy app();
}
