package com.gomart.guildbuddy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gomart.guildbuddy.databinding.CardItemBinding
import com.gomart.guildbuddy.vo.Character
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class RosterRecyclerViewAdapter @Inject constructor(
        @ActivityContext private val context: Context
) : RecyclerView.Adapter<RosterRecyclerViewAdapter.RosterViewHolder>() {

    private lateinit var data: List<Character>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: CardItemBinding = CardItemBinding.inflate(layoutInflater, parent, false)
        return RosterViewHolder(itemBinding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class RosterViewHolder(private val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.character = character
            binding.executePendingBindings()
        }
    }

    /**
     * set local data and notify data change
     */
    fun setData(items: List<Character>) {
        data = items
        notifyDataSetChanged()
    }

}