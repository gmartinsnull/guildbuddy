package com.gomart.guildbuddy.ui.roster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gomart.guildbuddy.CoroutinesContextProvider
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.testing.OpenForTesting
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@OpenForTesting
@HiltViewModel
class GuildRosterViewModel @Inject constructor(
    private val guildRepo: GuildRepository,
    private val characterRepo: CharacterRepository,
    private val contextProvider: CoroutinesContextProvider
) : ViewModel() {
    private val guildRequest: MutableLiveData<Guild> = MutableLiveData()
    private var isRefresh = false
    private var region = "us"

    val roster = guildRequest.switchMap { guild ->
        liveData(contextProvider.IO) {
            if (!isRefresh && guildRepo.isSameGuild(guild.name)) {
                when (guildRepo.getGuild()) {
                    is Resource.Error -> guildRepo.insertGuild(guild)
                    else -> {
                        // do nothing
                    }
                }
                emit(Resource.Success(characterRepo.getAllCharacters()))
            } else {
                guildRepo.getGuildRoster(guild.realm, guild.name, region).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            // already handled
                        }
                        is Resource.Success -> {
                            characterRepo.deleteAllCharacters()
                            resource.data.forEach { guildCharacter ->
                                characterRepo.getCharacter(guild.realm, guildCharacter.name)
                                    .collect {
                                        when (it) {
                                            is Resource.Error -> {
                                                emit(
                                                    Resource.Error(
                                                        Throwable(),
                                                        "Character not found: ${guildCharacter.name}"
                                                    )
                                                )
                                            }
                                            else -> {
                                                // do nothing
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
                isRefresh = false
            }
        }
    }

    /**
     * set search fields for api request
     */
    fun setGuildSearch(realmName: String, guildName: String, realmRegion: String) {
        region = realmRegion
        guildRequest.value = Guild(guildName, realmName)
    }

    /**
     * deletes guild from database
     */
    fun changeGuild() = viewModelScope.launch(Dispatchers.IO) { guildRepo.deleteGuild() }

    /**
     * sets isRefresh to true and sets guild livedata to trigger fetch guild roster
     */
    fun refreshRoster() {
        isRefresh = true
        setGuildSearch(
            guildRequest.value?.realm ?: "",
            guildRequest.value?.name ?: "",
            region
        )
    }
}