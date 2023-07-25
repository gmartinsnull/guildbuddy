package com.gomart.guildbuddy.di

import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.network.services.OAuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideOAuthService(): OAuthService =
            Retrofit.Builder()
                    .baseUrl(BuildConfig.URL_TOKEN)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OAuthService::class.java)
}

