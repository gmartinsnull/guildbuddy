package com.gomart.guildbuddy.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.gomart.guildbuddy.data.AppDatabase
import com.gomart.guildbuddy.data.SharedPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
            context.getSharedPreferences("sharedprefs", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPrefs(sharedPreferences: SharedPreferences): SharedPrefs = SharedPrefs(sharedPreferences)

    @DatabaseClient
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
            Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "guildbuddy_db"
            ).build()
}