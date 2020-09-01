package com.gomart.guildbuddy.network.services

import com.gomart.guildbuddy.network.CharacterResponse
import com.gomart.guildbuddy.vo.MediaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by glaubermartins on 2016-11-28.
 */
interface CharacterService {
    @GET("profile/wow/character/{realm}/{name}")
    suspend fun getCharacter(
            @Path("realm") realm: String,
            @Path("name") name: String,
            @Query("namespace") namespace: String,
            @Query("locale") locale: String,
            @Query("access_token") token: String): Response<CharacterResponse>

    @GET("profile/wow/character/{realm}/{name}/character-media")
    suspend fun getAvatar(
            @Path("realm") realm: String,
            @Path("name") name: String,
            @Query("namespace") namespace: String,
            @Query("locale") locale: String,
            @Query("access_token") token: String): MediaResponse
}