package com.gomart.guildbuddy.repository

import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.network.services.GuildService
import com.gomart.guildbuddy.network.services.OAuthService
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class GuildRepository @Inject constructor(
        private var guildService: GuildService,
        private var oAuthService: OAuthService
) {
    suspend fun getGuildRoster(
            realm: String,
            guildName: String,
            namespace: String,
            locale: String,
            token: String
    ) = guildService.getGuildRoster(realm, guildName, namespace, locale, token)

    suspend fun fetchToken() = oAuthService.fetchToken(
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET,
            BuildConfig.GRANT_TYPE
    )
}