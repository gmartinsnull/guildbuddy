package com.gomart.guildbuddy.network.services

import com.gomart.guildbuddy.network.GuildMembers
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by glaubermartins on 2016-11-24.
 */
interface GuildService {
    @GET("data/wow/guild/{realm}/{guildName}/roster")
    suspend fun fetchGuildMembers(
            @Path("realm") realm: String,
            @Path("guildName") guildName: String,
            @Query("namespace") namespace: String,
            @Query("locale") locale: String,
            @Query("access_token") token: String): Response<GuildMembers>
}