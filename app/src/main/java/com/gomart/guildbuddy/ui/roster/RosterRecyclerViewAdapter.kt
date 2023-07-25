package com.gomart.guildbuddy.ui.roster

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.gomart.guildbuddy.databinding.CardItemBinding
import com.gomart.guildbuddy.vo.Character
import dagger.hilt.android.qualifiers.ActivityContext
import java.util.*
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
class RosterRecyclerViewAdapter @Inject constructor(
        @ActivityContext private val context: Context
) : RecyclerView.Adapter<RosterRecyclerViewAdapter.RosterViewHolder>(), Filterable {

    private lateinit var data: List<Character>
    private var filteredData: List<Character> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: CardItemBinding = CardItemBinding.inflate(layoutInflater, parent, false)
        return RosterViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return if (filteredData.isNotEmpty())
            filteredData.size
        else
            data.size
    }

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        holder.bind(
                if (filteredData.isNotEmpty())
                    filteredData[position]
                else
                    data[position]
        )
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(query: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val result = data.filter { character ->
                    query != null &&
                            character.name.toLowerCase(Locale.getDefault()).contains(query) &&
                            query.length >= 3
                }
                filterResults.values = result
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, filterResults: FilterResults) {
                filteredData = filterResults.values as List<Character>
                notifyDataSetChanged()
            }

        }
    }

}