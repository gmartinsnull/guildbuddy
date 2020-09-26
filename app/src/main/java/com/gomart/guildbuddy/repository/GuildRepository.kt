package com.gomart.guildbuddy.repository

import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.data.GuildDao
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.network.services.GuildService
import com.gomart.guildbuddy.network.services.OAuthService
import com.gomart.guildbuddy.testing.OpenForTesting
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.vo.Resource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@OpenForTesting
class GuildRepository @Inject constructor(
        private val guildService: GuildService,
        private val oAuthService: OAuthService,
        private val guildDao: GuildDao,
        private val sharedPrefs: SharedPrefs
) {
    suspend fun getGuildRoster(realm: String, guildName: String) = flow {
        sharedPrefs.getSharedPrefsByKey("guild")?.let {
            guildDao.deleteGuild()
            guildDao.insertGuild(Guild(guildName, realm))
            sharedPrefs.setSharedPrefsByKey("realm", realm)
            sharedPrefs.setSharedPrefsByKey("guild", guildName)
            emit(fetchGuildRoster(
                    realm,
                    guildName,
                    BuildConfig.NAMESPACE,
                    BuildConfig.LOCALE,
                    "${sharedPrefs.getSharedPrefsByKey("token")}")
            )
        } ?: emit(Resource.Error(Throwable(), "roster not found"))
    }

    private suspend fun fetchGuildRoster(
            realm: String,
            guildName: String,
            namespace: String,
            locale: String,
            token: String
    ) = guildService.fetchGuildMembers(realm, guildName, namespace, locale, token)
            .run {
                val response = body()
                if (isSuccessful && response != null) {
                    guildDao.deleteAll()
                    guildDao.insertRoster(response.members.map { it.character })
                    Resource.Success(guildDao.getRoster())
                } else {
                    //todo implement proper error handling
                    Resource.Error(Throwable(), "Guild not found")
                }
            }

    suspend fun fetchToken() = oAuthService.fetchToken(
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET,
            BuildConfig.GRANT_TYPE
    )

    /**
     * checks whether user is searching same guild
     */
    fun isSameGuild(guildName: String?): Boolean {
        return sharedPrefs.getSharedPrefsByKey("guild") == guildName
    }

}