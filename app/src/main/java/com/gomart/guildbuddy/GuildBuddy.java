package com.gomart.guildbuddy;

import android.app.Application;

import com.gomart.guildbuddy.component.AppComponent;
import com.gomart.guildbuddy.component.DaggerAppComponent;
import com.gomart.guildbuddy.module.AppModule;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public class GuildBuddy extends Application {

    private static GuildBuddy app;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        appComponent = initDagger(this);

    }

    public static GuildBuddy app(){
        return app;
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

    protected AppComponent initDagger(GuildBuddy application){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

}
