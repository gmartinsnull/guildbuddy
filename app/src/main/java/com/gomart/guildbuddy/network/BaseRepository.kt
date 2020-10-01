package com.gomart.guildbuddy.network

import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.data.SharedPrefs
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-09-30
 *   Description:
 */
abstract class BaseRepository {

    @Inject
    lateinit var apiInterceptor: ApiInterceptor

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    val locale: String by lazy {
        sharedPrefs.getSharedPrefsByKey("region")?.let { region ->
            if (region == "eu")
                BuildConfig.LOCALE_EU
            else
                BuildConfig.LOCALE
        } ?: BuildConfig.LOCALE
    }

    val namespace: String by lazy {
        sharedPrefs.getSharedPrefsByKey("region")?.let { region ->
            if (region == "eu")
                BuildConfig.NAMESPACE_EU
            else
                BuildConfig.NAMESPACE
        } ?: BuildConfig.NAMESPACE
    }

    val token: String by lazy {
        sharedPrefs.getSharedPrefsByKey("token") ?: ""
    }
}