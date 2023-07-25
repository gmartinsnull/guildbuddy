package com.gomart.guildbuddy.ui.roster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gomart.guildbuddy.CoroutinesContextProvider
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.testing.OpenForTesting
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
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
    private var isRefresh = false
    private var region = "us"

    val rosterState: MutableLiveData<RosterState> = MutableLiveData()
    var currentGuildSearch: Guild? = null

    private fun handleGuildRoster(guild: Guild) {
        viewModelScope.launch(contextProvider.IO) {
            rosterState.postValue(RosterState.Loading)
            if (!isRefresh && guildRepo.isSameGuild(guild.name)) {
                when (guildRepo.getGuild()) {
                    is Resource.Error -> guildRepo.insertGuild(guild)
                    else -> {
                        // do nothing
                    }
                }
                rosterState.postValue(RosterState.Loaded(characterRepo.getAllCharacters()))
            } else {
                guildRepo.getGuildRoster(
                    guild.realm,
                    guild.name,
                    region
                ).flatMapConcat { resource ->
                    flowOf(
                        if (resource is Resource.Success)
                            Resource.Success(resource.data)
                        else
                            Resource.Error(Throwable(), "guild roster error")
                    )
                }.flatMapConcat { guildRosterResource ->
                    flowOf(
                        if (guildRosterResource is Resource.Success) {
                            characterRepo.deleteAllCharacters()
                            guildRosterResource.data.forEach { guildCharacter ->
                                characterRepo.getCharacter(
                                    guild.realm,
                                    guildCharacter.name
                                ).collect {
                                    when (it) {
                                        is Resource.Error -> {
                                            Resource.Error(
                                                Throwable(),
                                                "Character not found: ${guildCharacter.name}"
                                            )
                                        }

                                        else -> {
                                            // do nothing
                                        }
                                    }
                                }
                            }
                            Resource.Success(characterRepo.getAllCharacters())
                        } else {
                            guildRosterResource
                        }
                    )
                }.collect { result ->
                    rosterState.postValue(
                        when (result) {
                            is Resource.Success -> {
                                RosterState.Loaded(result.data)
                            }

                            is Resource.Error -> {
                                RosterState.Error(result.message)
                            }
                        }
                    )
                    isRefresh = false
                }
            }
        }
    }

    /**
     * set search fields for api request
     */
    fun setGuildSearch(realmName: String, guildName: String, realmRegion: String) {
        region = realmRegion
        val guild = Guild(guildName, realmName)
        handleGuildRoster(guild)
        currentGuildSearch = guild
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
            currentGuildSearch?.realm ?: "",
            currentGuildSearch?.name ?: "",
            region
        )
    }
}