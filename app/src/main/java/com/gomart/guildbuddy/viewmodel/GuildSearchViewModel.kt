package com.gomart.guildbuddy.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.vo.Token
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *   Created by gmartins on 2020-08-29
 *   Description:
 */
class GuildSearchViewModel @ViewModelInject constructor(
        private val repository: GuildRepository,
        private val sharedPrefs: SharedPrefs,
        @Assisted
        private val savedStateHandle: SavedStateHandle //needed by Hilt
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
                return listOf(realm, guild.replace("-", " "))
            }
        }
        return listOf()
    }
}