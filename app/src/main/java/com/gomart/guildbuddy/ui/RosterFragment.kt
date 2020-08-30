package com.gomart.guildbuddy.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.viewmodel.GuildRosterViewModel
import com.gomart.guildbuddy.vo.Character
import com.gomart.guildbuddy.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_roster.*
import javax.inject.Inject

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@AndroidEntryPoint
class RosterFragment : Fragment(R.layout.fragment_roster) {
    private val viewModel: GuildRosterViewModel by viewModels()

    @Inject
    lateinit var rosterRecyclerViewAdapter: RosterRecyclerViewAdapter

    private val params by navArgs<RosterFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, Observer { response ->
            Log.d("TEST", "data: ${response.data}")
            /*when (response) {
                is Resource.success -> {
                    progress.visibility = View.GONE

                    recycler.apply {
                        setHasFixedSize(true)

                        val gridLayoutManager = GridLayoutManager(context, 3)
                        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL

                        layoutManager = gridLayoutManager

                        //adapter = exploreRecyclerViewAdapter
                        //exploreRecyclerViewAdapter.setData((response.data as Character).posts.toTypedArray())
                    }
                }
                is Resource.error -> {
                    progress.visibility = View.GONE
                    txtError.visibility = View.VISIBLE
                }
            }*/
        })
        progress.visibility = View.VISIBLE

        viewModel.setGuildSearch(params.realm, params.guildName)
    }
}