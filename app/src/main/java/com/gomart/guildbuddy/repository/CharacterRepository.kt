package com.gomart.guildbuddy.repository

import com.gomart.guildbuddy.data.CharacterDao
import com.gomart.guildbuddy.network.services.CharacterService
import com.gomart.guildbuddy.vo.Character
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class CharacterRepository @Inject constructor(
        private var service: CharacterService,
        private val characterDao: CharacterDao
) {
    suspend fun getCharacter(
            realm: String,
            name: String,
            namespace: String,
            locale: String,
            token: String
    ) = service.getCharacter(realm, name, namespace, locale, token)

    suspend fun getAvatar(
            realm: String,
            name: String,
            namespace: String,
            locale: String,
            token: String
    ) = service.getAvatar(realm, name, namespace, locale, token)

    suspend fun saveCharacters(characters: List<Character>) = characterDao.insert(characters)
    suspend fun getAll() = characterDao.getAll()
    suspend fun deleteCharacters() = characterDao.deleteAll()
}