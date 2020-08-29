package com.gomart.guildbuddy.vo

import android.widget.GridLayout
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by glaubermartins on 2016-11-24.
 */
data class Character(
        var name: String,
        var race: Int,
        var gender: Int,
        var level: Int,
        var thumbnail: String,
        var achievementPoints: Int,
        @SerializedName("averageItemLevelEquipped")
        var itemLevel: Int,
        var totalHonorableKills: Int = 0,
        val role: String,
        @SerializedName("class")
        var charClass: Int,
        var spec: GridLayout.Spec,
        var fields: ArrayList<String>
)