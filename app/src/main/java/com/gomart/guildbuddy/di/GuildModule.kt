package com.gomart.guildbuddy.di

import com.gomart.guildbuddy.network.services.GuildService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@Module
@InstallIn(ApplicationComponent::class)
object GuildModule {
    @Singleton
    @Provides
    fun provideGuildService(@RetrofitClient retrofit: Retrofit): GuildService =
            retrofit.create(GuildService::class.java)
}