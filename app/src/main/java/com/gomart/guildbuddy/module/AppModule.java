package com.gomart.guildbuddy.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gomart.guildbuddy.manager.DataManager;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by glaubermartins on 2016-12-06.
 */

@Module
public class AppModule {

    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Singleton
    @Provides
    public Context provideContext(){
        return application;
    }

    @Singleton
    @Provides
    public Picasso providePicasso(){
        return new Picasso.Builder(application).build();
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Singleton
    @Provides
    public DataManager provideDataManager(SharedPreferences sharedPreferences){
        return new DataManager(sharedPreferences);
    }
}
