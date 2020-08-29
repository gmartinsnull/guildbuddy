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
            name: String,
            realm: String,
            namespace: String,
            locale: String,
            token: String
    ) = service.getCharacter(name, realm, namespace, locale, token)
}