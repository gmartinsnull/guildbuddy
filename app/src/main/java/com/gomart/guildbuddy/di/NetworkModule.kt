package com.gomart.guildbuddy.di

import android.content.Context
import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.network.ApiInterceptor
import com.gomart.guildbuddy.network.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @RetrofitClient
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(apiInterceptor: ApiInterceptor): OkHttpClient =
            OkHttpClient.Builder().addInterceptor(apiInterceptor).build()

    @Singleton
    @Provides
    fun provideApiInterceptor() = ApiInterceptor()

    @Singleton
    @Provides
    fun provideNetworkUtils(@ApplicationContext context: Context) = NetworkUtils(context)
}