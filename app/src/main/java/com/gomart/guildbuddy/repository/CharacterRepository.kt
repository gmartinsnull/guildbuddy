package com.gomart.guildbuddy.repository

import com.gomart.guildbuddy.network.services.CharacterService
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class CharacterRepository @Inject constructor(
        private var service: CharacterService
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
}