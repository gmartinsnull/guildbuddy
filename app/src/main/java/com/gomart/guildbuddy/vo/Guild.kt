package com.gomart.guildbuddy.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *   Created by gmartins on 2020-08-31
 *   Description:
 */
@Entity
data class Guild (
        @PrimaryKey
        val name: String,
        val realm: String
)