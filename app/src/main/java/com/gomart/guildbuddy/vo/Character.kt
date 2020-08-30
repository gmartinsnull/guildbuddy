package com.gomart.guildbuddy.vo

/**
 * Created by glaubermartins on 2016-11-24.
 */
data class Character(
        var name: String,
        var race: String,
        var level: Int,
        var thumbnail: String,
        var achievementPoints: Int,
        var itemLevel: Int,
        var totalHonorableKills: Int = 0,
        var charClass: String,
        var spec: String
)