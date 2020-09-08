package com.gomart.guildbuddy.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.vo.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class GuildRosterViewModel @ViewModelInject constructor(
        @ApplicationContext private val context: Context,
        private val guildRepo: GuildRepository,
        private val characterRepo: CharacterRepository,
        private val sharedPrefs: SharedPrefs
) : ViewModel() {
    private val queryMap = MutableLiveData<Map<String, String>>()

    val roster = liveData(Dispatchers.IO) {
        if (checkConnection()) {
            queryMap.value?.get("realm")?.let { realm ->
                queryMap.value?.get("guild")?.let { guild ->
                    val storedGuild = guildRepo.getGuild()
                    if (storedGuild != null && !isNewGuild(storedGuild.name)) {
                        emit(Resource.Success(characterRepo.getAllCharacters()))
                    } else {
                        guildRepo.deleteGuild()
                        guildRepo.addGuild(Guild(guild, realm))
                        guildRepo.getGuildRoster(realm, guild).collect { resource ->
                            when (resource) {
                                is Resource.Success -> {
                                    characterRepo.deleteAllCharacters()
                                    resource.data.forEach { guildCharacter ->
                                        characterRepo.getCharacter(realm, guildCharacter.name).collect {
                                            when (it) {
                                                is Resource.Error -> {
                                                    emit(Resource.Error(Throwable(), "Character not found: ${guildCharacter.name}"))
                                                }
                                            }
                                        }
                                    }
                                    emit(Resource.Success(characterRepo.getAllCharacters()))
                                }
                                is Resource.Error -> {
                                    emit(Resource.Error(Throwable(), resource.message))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * checks whether user is searching new guild
     */
    private fun isNewGuild(guildName: String): Boolean {
        return sharedPrefs.getSharedPrefsByKey("guild") != guildName.replace("-", " ")
    }

    /**
     * set search fields for api request
     */
    fun setGuildSearch(realmName: String, guildName: String) {
        queryMap.value = mapOf(
                "realm" to realmName,
                "guild" to guildName
        )
    }

    /**
     * checks for internet connection
     */
    private fun checkConnection(): Boolean {
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