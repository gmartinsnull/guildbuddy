package com.gomart.guildbuddy.di

import com.gomart.guildbuddy.CoroutinesContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 *   Created by gmartins on 2020-09-28
 *   Description:
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContextProvider() = CoroutinesContextProvider()
}