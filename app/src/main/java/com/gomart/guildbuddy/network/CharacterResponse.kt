package com.gomart.guildbuddy.network

import com.gomart.guildbuddy.vo.CharacterClass
import com.gomart.guildbuddy.vo.CharacterRace
import com.gomart.guildbuddy.vo.CharacterSpec

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
data class CharacterResponse(
        val name: String,
        val charClass: CharacterClass,
        val charRace: CharacterRace,
        val charSpec: CharacterSpec,
        val level: Int,
        val equipped_item_level: Int
)