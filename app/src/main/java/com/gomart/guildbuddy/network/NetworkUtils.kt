package com.gomart.guildbuddy.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.gomart.guildbuddy.testing.OpenForTesting
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 *   Created by gmartins on 2020-09-16
 *   Description:
 */
@OpenForTesting
class NetworkUtils constructor(@ApplicationContext private val context: Context) {
    /**
     * checks for internet connection
     */
    fun checkConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val result = cm?.activeNetwork?.let { network ->
            cm.getNetworkCapabilities(network)?.let { networkCapabilities ->
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            }
        }

        return result != null
    }
}