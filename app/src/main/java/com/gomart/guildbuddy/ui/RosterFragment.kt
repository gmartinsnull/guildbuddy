package com.gomart.guildbuddy.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.databinding.FragmentRosterBinding
import com.gomart.guildbuddy.network.NetworkUtils
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
class RosterFragment : Fragment() {
    private val viewModel: GuildRosterViewModel by viewModels()

    @Inject
    lateinit var rosterRecyclerViewAdapter: RosterRecyclerViewAdapter

    @Inject
    lateinit var networkUtils: NetworkUtils

    private val params by navArgs<RosterFragmentArgs>()

    private lateinit var binding: FragmentRosterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_roster, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.roster.observe(viewLifecycleOwner, Observer { response ->
            refresh.isRefreshing = false
            when (response) {
                is Resource.Success -> {
                    progress.visibility = View.GONE
                    txtError.visibility = View.GONE

                    setupAdapter(response.data)
                }
                is Resource.Error -> {
                    progress.visibility = View.GONE
                    txtError.visibility = View.VISIBLE
                    txtError.text = response.message
                }
            }
        })
        refresh.setOnRefreshListener {
            viewModel.refreshRoster()
        }

        progress.visibility = View.VISIBLE

        if (networkUtils.checkConnection())
            viewModel.setGuildSearch(params.realm, params.guildName, params.region)

    }

    /**
     * set up recycler view adapter
     */
    private fun setupAdapter(data: List<Character>) {
        binding.recycler.apply {
            setHasFixedSize(true)

            val gridLayoutManager = GridLayoutManager(context, 3)
            gridLayoutManager.orientation = LinearLayoutManager.VERTICAL

            layoutManager = gridLayoutManager

            adapter = rosterRecyclerViewAdapter
            rosterRecyclerViewAdapter.setData(data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.roster_menu, menu)
        (menu.findItem(R.id.search).actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                rosterRecyclerViewAdapter.filter.filter(query)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> true
            R.id.changeGuild -> {
                viewModel.changeGuild()
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}