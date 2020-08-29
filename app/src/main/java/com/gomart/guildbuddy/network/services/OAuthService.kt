package com.gomart.guildbuddy.network.services

import com.gomart.guildbuddy.vo.Token
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
interface OAuthService {
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun fetchToken(
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("grant_type") grandType: String
    ): Token
}