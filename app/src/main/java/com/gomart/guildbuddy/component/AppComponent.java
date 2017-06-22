package com.gomart.guildbuddy.component;

import com.gomart.guildbuddy.module.AppModule;
import com.gomart.guildbuddy.ui.GuildMemberProfileActivity;
import com.gomart.guildbuddy.ui.GuildMembersActivity;
import com.gomart.guildbuddy.ui.MainActivity;
import com.gomart.guildbuddy.ui.adapter.GuildMembersAdapter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by glaubermartins on 2016-12-06.
 */

@Singleton
@Component(
        modules = { AppModule.class}
)
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(GuildMembersActivity guildMembersActivity);
    void inject(GuildMembersAdapter guildMembersAdapter);
    void inject(GuildMemberProfileActivity guildMemberProfileActivity);
}
