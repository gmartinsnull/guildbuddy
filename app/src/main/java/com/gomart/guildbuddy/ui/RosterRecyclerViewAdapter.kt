package com.gomart.guildbuddy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.gomart.guildbuddy.R
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

    /*@Inject
    lateinit var sharedPrefs: SharedPrefs*/

    private lateinit var data: Array<Character>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RosterViewHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_item, parent, false) as LinearLayout
        return RosterViewHolder(cardView)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RosterViewHolder, position: Int) {
        /*Glide.with(holder.view).load(data[position].imageUrl).into(holder.view.image)
        holder.view.findViewById<TextView>(R.id.text).text = data[position].text

        holder.view.setOnClickListener {
            sharedPrefs.setSharedPrefsByKey(position.toString(), true)
            val bundle = bundleOf("url" to data[position].linkUrl)
            holder.view.findNavController().navigate(R.id.action_main_to_webview, bundle)
        }*/
    }

    class RosterViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view)

    /**
     * set local data and notify data change
     */
    fun setData(items: Array<Character>) {
        data = items
        notifyDataSetChanged()
    }

}