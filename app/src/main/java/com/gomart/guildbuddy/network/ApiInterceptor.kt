package com.gomart.guildbuddy.network

import android.util.Log
import com.gomart.guildbuddy.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException


/**
 *   Created by gmartins on 2020-09-30
 *   Description:
 */
class ApiInterceptor : Interceptor {

    private var region = "us"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = if (region == "eu") {
            val host = request.url.newBuilder()
                    .host(BuildConfig.URL_EU)
                    .build()
            request.newBuilder()
                    .url(host)
                    .build()
        } else {
            request.newBuilder()
                    .url(request.url)
                    .build()
        }

        if (BuildConfig.DEBUG)
            Log.d("API_INTERCEPTOR", "request: ${request.url}")

        return chain.proceed(request)
    }

    /**
     * sets region for url host
     */
    fun setRegion(realmRegion: String) {
        region = realmRegion
    }
}