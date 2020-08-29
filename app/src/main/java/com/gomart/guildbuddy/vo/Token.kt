package com.gomart.guildbuddy.vo

import com.google.gson.annotations.SerializedName

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
data class Token (
        @SerializedName("access_token")
        val accessToken: String
)