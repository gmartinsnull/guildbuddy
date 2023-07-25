package com.gomart.guildbuddy.di

import com.gomart.guildbuddy.data.AppDatabase
import com.gomart.guildbuddy.data.GuildDao
import com.gomart.guildbuddy.network.services.GuildService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object GuildModule {
    @Singleton
    @Provides
    fun provideGuildService(@RetrofitClient retrofit: Retrofit): GuildService =
            retrofit.create(GuildService::class.java)

    @Singleton
    @Provides
    fun provideGuildDao(@DatabaseClient appDatabase: AppDatabase): GuildDao =
            appDatabase.guildDao()
}