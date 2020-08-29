package com.gomart.guildbuddy.network.services

import com.gomart.guildbuddy.network.CharacterResponse
import com.gomart.guildbuddy.network.GetCharacterClassesResponse
import com.gomart.guildbuddy.network.GetCharacterRacesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by glaubermartins on 2016-11-28.
 */
interface CharacterService {
    @GET("wow/data/character/classes")
    fun getClassName(@Query("locale") locale: String?, @Query("apikey") apiKey: String?): Call<GetCharacterClassesResponse?>?

    @GET("wow/data/character/races")
    fun getRaceName(@Query("locale") locale: String?, @Query("apikey") apiKey: String?): Call<GetCharacterRacesResponse?>?

    @GET("profile/wow/character/{realm}/{name}")
    suspend fun getCharacter(
            @Path("realm") realm: String,
            @Path("name") name: String,
            @Path("namespace") namespace: String,
            @Path("locale") locale: String,
            @Query("token") token: String): CharacterResponse
}