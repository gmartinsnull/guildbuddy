package com.gomart.guildbuddy.repository

import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.data.CharacterDao
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.network.CharacterResponse
import com.gomart.guildbuddy.network.services.CharacterService
import com.gomart.guildbuddy.testing.OpenForTesting
import com.gomart.guildbuddy.vo.Character
import com.gomart.guildbuddy.vo.Resource
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@OpenForTesting
class CharacterRepository @Inject constructor(
        private var service: CharacterService,
        private val characterDao: CharacterDao,
        private val sharedPrefs: SharedPrefs
) {
    fun getCharacter(realm: String, name: String) = flow {
        sharedPrefs.getSharedPrefsByKey("guild")?.let {
            emit(fetchCharacter(realm, name))
        } ?: Resource.Error(Throwable(), "guild not found")
    }

    private suspend fun fetchCharacter(realm: String, name: String) = service.getCharacter(
            realm,
            name.toLowerCase(Locale.ROOT),
            BuildConfig.NAMESPACE,
            BuildConfig.LOCALE,
            "${sharedPrefs.getSharedPrefsByKey("token")}"
    ).run {
        try {
            val response = body()
            if (isSuccessful && response != null) {
                characterDao.insertCharacter(convertToCharacter(response, realm))
                Resource.Success(characterDao.getAll())
            } else {
                Resource.Error(Throwable(errorBody().toString()))
            }
        } catch (exception: Exception) {
            Resource.Error(Throwable(), "potential null token")
        }
    }

    private suspend fun getAvatar(
            realm: String,
            name: String,
            namespace: String,
            locale: String,
            token: String
    ) = service.getAvatar(realm, name, namespace, locale, token)

    suspend fun getAllCharacters() = characterDao.getAll()
    suspend fun deleteAllCharacters() = characterDao.deleteAll()

    /**
     * converts character object from api call response to vo
     */
    private suspend fun convertToCharacter(characterResponse: CharacterResponse, realmName: String): Character {
        return Character(
                characterResponse.id,
                characterResponse.name,
                characterResponse.charRace.name,
                characterResponse.level,
                getAvatar(
                        realmName,
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
        )
    }
}