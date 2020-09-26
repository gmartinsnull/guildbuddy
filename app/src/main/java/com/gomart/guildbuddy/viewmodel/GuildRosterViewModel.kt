package com.gomart.guildbuddy.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.testing.OpenForTesting
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@OpenForTesting
class GuildRosterViewModel @ViewModelInject constructor(
        private val guildRepo: GuildRepository,
        private val characterRepo: CharacterRepository
) : ViewModel() {
    private val guildRequest: MutableLiveData<Guild> = MutableLiveData()

    val roster = guildRequest.switchMap { guild ->
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            if (guildRepo.isSameGuild(guild.name)) {
                emit(Resource.Success(characterRepo.getAllCharacters()))
            } else {
                guildRepo.getGuildRoster(guild.realm, guild.name).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            characterRepo.deleteAllCharacters()
                            resource.data.forEach { guildCharacter ->
                                characterRepo.getCharacter(guild.realm, guildCharacter.name).collect {
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

    /**
     * set search fields for api request
     */
    fun setGuildSearch(realmName: String, guildName: String) {
        guildRequest.value = Guild(guildName, realmName)
    }
}