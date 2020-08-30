package com.gomart.guildbuddy.vo

import com.google.gson.annotations.SerializedName

/**
 *   Created by gmartins on 2020-08-29
 *   Description:
 */
data class MediaResponse (
        @SerializedName("avatar_url")
        val avatar: String
)