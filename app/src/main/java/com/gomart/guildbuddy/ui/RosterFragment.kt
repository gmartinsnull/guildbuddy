package com.gomart.guildbuddy.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.viewmodel.GuildRosterViewModel
import dagger.hilt.android.AndroidEntryPoint
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
        /*viewModel.data.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE

                    recyclerview.apply {
                        setHasFixedSize(true)

                        val gridLayoutManager = GridLayoutManager(context, 3)
                        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL

                        layoutManager = gridLayoutManager

                        adapter = exploreRecyclerViewAdapter
                        exploreRecyclerViewAdapter.setData((response.data as Posts).posts.toTypedArray())
                    }
                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    textError.visibility = View.VISIBLE
                }
            }
        })
        progressBar.visibility = View.VISIBLE*/
    }
}