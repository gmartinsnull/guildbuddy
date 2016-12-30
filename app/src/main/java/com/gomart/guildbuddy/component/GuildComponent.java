package com.gomart.guildbuddy.component;

import com.gomart.guildbuddy.model.Guild;
import com.gomart.guildbuddy.module.GuildModule;
import com.gomart.guildbuddy.ui.GuildMembersActivity;

import dagger.Component;

/**
 * Created by glaubermartins on 2016-12-05.
 */

@Component(
        modules = GuildModule.class
)
public interface GuildComponent {
    void inject(GuildMembersActivity guildActivity);
}
