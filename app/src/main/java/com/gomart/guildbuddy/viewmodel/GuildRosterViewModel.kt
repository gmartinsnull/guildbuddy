package com.gomart.guildbuddy.viewmodel

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.vo.Resource
import com.gomart.guildbuddy.vo.Token
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class GuildRosterViewModel @ViewModelInject constructor(
        @ApplicationContext private val context: Context,
        private val repository: GuildRepository,
        private val sharedPrefs: SharedPrefs,
        @Assisted private val savedStateHandle: SavedStateHandle //needed by Hilt
) : ViewModel() {
    val data = liveData(Dispatchers.IO) {
        try {
            Log.d("TEST", "sharedprefs: ${sharedPrefs.getSharedPrefsByKey("token")}")
            if (checkConnection()) {
                val result = repository.getGuildRoster(
                        "tichondrius",
                        "chicken-lords",
                        BuildConfig.NAMESPACE,
                        BuildConfig.LOCALE,
                        "${sharedPrefs.getSharedPrefsByKey("token")}"
                )
                emit(Resource.success(result))
            }
        } catch (exception: NetworkErrorException) {
            //emit(Resource.Error<Throwable>(Throwable(exception)))
        }
    }

    /**
     * fetches token from blizzard api
     */
    fun fetchToken() {
        viewModelScope.launch(Dispatchers.IO) {
            saveToken(repository.fetchToken())
        }
    }

    /**
     * saves token in shared prefs
     */
    private fun saveToken(token: Token) {
        sharedPrefs.setSharedPrefsByKey("token", token.accessToken)
    }

    /**
     * checks for internet connection
     */
    private fun checkConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val result = cm.activeNetwork?.let { network ->
            cm.getNetworkCapabilities(network)?.let { networkCapabilities ->
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            }
        }

        return result != null
    }
}