package com.gomart.guildbuddy.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.vo.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-29
 *   Description:
 */
@HiltViewModel
class GuildSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle, //needed by Hilt
    private val repository: GuildRepository,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {
    /**
     * fetches token from blizzard api
     */
    fun fetchToken() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = repository.fetchToken()
            saveToken(token)
        }
    }

    /**
     * saves token in shared prefs
     */
    private fun saveToken(token: Token) {
        sharedPrefs.setSharedPrefsByKey("token", token.accessToken)
    }

    /**
     * retrieves search data
     */
    fun getSearchData(): List<String> {
        sharedPrefs.getSharedPrefsByKey("realm")?.let { realm ->
            sharedPrefs.getSharedPrefsByKey("guild")?.let { guild ->
                sharedPrefs.getSharedPrefsByKey("region")?.let { region ->
                    return listOf(realm, guild.replace("-", " "), region)
                }
            }
        }
        return listOf()
    }

    /**
     * get existing guild from db
     */
    fun getGuild() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(repository.getGuild())
    }
}