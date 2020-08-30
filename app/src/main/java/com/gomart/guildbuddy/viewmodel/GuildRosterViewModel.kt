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
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.vo.Character
import com.gomart.guildbuddy.vo.Resource
import com.gomart.guildbuddy.vo.Token
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class GuildRosterViewModel @ViewModelInject constructor(
        @ApplicationContext private val context: Context,
        private val guildRepo: GuildRepository,
        private val characterRepo: CharacterRepository,
        private val sharedPrefs: SharedPrefs,
        @Assisted private val savedStateHandle: SavedStateHandle //needed by Hilt
) : ViewModel() {
    private val queryMap = MutableLiveData<Map<String, String>>()

    val data = liveData(Dispatchers.IO) {
        try {
            if (checkConnection()) {
                queryMap.value?.get("realm")?.let { realm ->
                    queryMap.value?.get("guild")?.let { guild ->
                        val resultRosterMeta = guildRepo.getGuildRoster(
                                realm,
                                guild,
                                BuildConfig.NAMESPACE,
                                BuildConfig.LOCALE,
                                "${sharedPrefs.getSharedPrefsByKey("token")}"
                        )

                        val result: ArrayList<Character> = arrayListOf()
                        resultRosterMeta.members.forEach { guildMember ->
                            val characterResponse = characterRepo.getCharacter(
                                    realm,
                                    guildMember.character.name.toLowerCase(Locale.ROOT),
                                    BuildConfig.NAMESPACE,
                                    BuildConfig.LOCALE,
                                    "${sharedPrefs.getSharedPrefsByKey("token")}"
                            )
                            result.add(Character(
                                    characterResponse.name,
                                    characterResponse.charRace.name,
                                    characterResponse.level,
                                    characterRepo.getAvatar(
                                            realm,
                                            characterResponse.name.toLowerCase(Locale.ROOT),
                                            BuildConfig.NAMESPACE,
                                            BuildConfig.LOCALE,
                                            "${sharedPrefs.getSharedPrefsByKey("token")}"
                                    ).avatar,
                                    characterResponse.achievementPoints,
                                    characterResponse.itemLevel,
                                    0,
                                    characterResponse.charSpec.name,
                                    characterResponse.charClass.name
                            ))
                        }
                        //todo save in db
                        emit(Resource.success(result))
                    }
                }
            }
        } catch (exception: NetworkErrorException) {
            emit(Resource.error<Throwable>(exception.message.toString(), null))
        }
    }

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