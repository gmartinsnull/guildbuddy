package com.gomart.guildbuddy.vo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide

/**
 * Created by glaubermartins on 2016-11-24.
 */
@Entity
data class Character(
        @PrimaryKey
        var id: Int,
        var name: String,
        var race: String? = "",
        var level: Int? = 0,
        var thumbnail: String? = "",
        var achievementPoints: Int? = 0,
        var itemLevel: Int? = 0,
        var totalHonorableKills: Int = 0,
        var charClass: String? = "",
        var spec: String? = ""
) {
    companion object {
        /**
         * data binding image url into imageview
         */
        @JvmStatic
        @BindingAdapter("avatar")
        fun loadImage(imageView: ImageView, imageURL: String?) {
            imageURL?.let {
                Glide.with(imageView.context)
                    .load(it)
                    .into(imageView)
            }
        }
    }
}