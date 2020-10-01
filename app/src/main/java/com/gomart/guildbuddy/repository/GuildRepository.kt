package com.gomart.guildbuddy.repository

import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.data.GuildDao
import com.gomart.guildbuddy.network.BaseRepository
import com.gomart.guildbuddy.network.services.GuildService
import com.gomart.guildbuddy.network.services.OAuthService
import com.gomart.guildbuddy.testing.OpenForTesting
import com.gomart.guildbuddy.vo.Guild
import com.gomart.guildbuddy.vo.GuildCharacter
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
        private val guildDao: GuildDao
) : BaseRepository() {

    suspend fun getGuildRoster(realm: String, guildName: String, region: String) = flow {
        sharedPrefs.getSharedPrefsByKey("guild")?.let {
            guildDao.deleteGuild()
            guildDao.insertGuild(Guild(guildName, realm))
            sharedPrefs.setSharedPrefsByKey("realm", realm)
            sharedPrefs.setSharedPrefsByKey("guild", guildName)
            sharedPrefs.setSharedPrefsByKey("region", region)
            emit(fetchGuildRoster(realm, guildName))
        } ?: emit(Resource.Error(Throwable(), "roster not found"))
    }

    private suspend fun fetchGuildRoster(
            realm: String,
            guildName: String
    ): Resource<List<GuildCharacter>> {
        apiInterceptor.setRegion(sharedPrefs.getSharedPrefsByKey("region") ?: "us")
        return guildService.fetchGuildMembers(realm, guildName, namespace, locale, token)
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
    }

    suspend fun fetchToken() = oAuthService.fetchToken(
            BuildConfig.CLIENT_ID,
            BuildConfig.CLIENT_SECRET,
            BuildConfig.GRANT_TYPE
    )

    /**
     * checks whether user is searching same guild
     */
    fun isSameGuild(guildName: String?) = sharedPrefs.getSharedPrefsByKey("guild") == guildName

    suspend fun getGuild() = guildDao.getGuild()?.let {
        Resource.Success(it)
    } ?: Resource.Error(Throwable(), "No guild found.")

    suspend fun insertGuild(guild: Guild) = guildDao.insertGuild(guild)
    suspend fun deleteGuild() = guildDao.deleteGuild()
}