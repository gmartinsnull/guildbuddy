package com.gomart.guildbuddy.network

import com.gomart.guildbuddy.vo.GuildMember

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
data class GuildRosterResponse(
        val members: List<GuildMember>
)