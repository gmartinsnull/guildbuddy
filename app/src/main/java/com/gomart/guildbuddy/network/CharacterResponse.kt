package com.gomart.guildbuddy.network

import com.gomart.guildbuddy.vo.CharacterClass
import com.gomart.guildbuddy.vo.CharacterRace
import com.gomart.guildbuddy.vo.CharacterSpec
import com.google.gson.annotations.SerializedName

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
data class CharacterResponse(
        val id: Int,
        val name: String,
        @SerializedName("character_class")
        val charClass: CharacterClass,
        @SerializedName("race")
        val charRace: CharacterRace,
        @SerializedName("active_spec")
        val charSpec: CharacterSpec,
        val level: Int,
        @SerializedName("equipped_item_level")
        val itemLevel: Int,
        @SerializedName("achievement_points")
        val achievementPoints: Int
)