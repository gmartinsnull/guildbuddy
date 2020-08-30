package com.gomart.guildbuddy.di

import com.gomart.guildbuddy.data.AppDatabase
import com.gomart.guildbuddy.data.CharacterDao
import com.gomart.guildbuddy.network.services.CharacterService
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
object CharacterModule {
    @Singleton
    @Provides
    fun provideGuildService(@RetrofitClient retrofit: Retrofit): CharacterService =
            retrofit.create(CharacterService::class.java)

    @Singleton
    @Provides
    fun provideCharacterDao(@DatabaseClient appDatabase: AppDatabase): CharacterDao =
            appDatabase.characterDao()
}