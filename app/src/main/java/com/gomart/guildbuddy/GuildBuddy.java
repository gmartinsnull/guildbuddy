package com.gomart.guildbuddy;

import android.app.Application;

import com.gomart.guildbuddy.component.AppComponent;
import com.gomart.guildbuddy.component.DaggerAppComponent;
import com.gomart.guildbuddy.manager.DataManager;
import com.gomart.guildbuddy.module.AppModule;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public class GuildBuddy extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.inject(this);

        DataManager.getInstance().init(getApplicationContext());
    }



}
