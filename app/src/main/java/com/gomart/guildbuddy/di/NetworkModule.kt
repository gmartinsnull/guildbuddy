package com.gomart.guildbuddy.di

import com.gomart.guildbuddy.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @RetrofitClient
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient =
                if (BuildConfig.DEBUG){
                    OkHttpClient.Builder()
                            .addInterceptor(interceptor)
                            .build()
                }else{
                    OkHttpClient.Builder()
                            .build()
                }

        return Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

}