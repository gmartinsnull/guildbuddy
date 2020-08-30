package com.gomart.guildbuddy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.databinding.FragmentRosterBinding
import com.gomart.guildbuddy.viewmodel.GuildRosterViewModel
import com.gomart.guildbuddy.vo.Character
import com.gomart.guildbuddy.vo.Status
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

    private val params by navArgs<RosterFragmentArgs>()

    private lateinit var binding: FragmentRosterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_roster, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, Observer { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    progress.visibility = View.GONE

                    binding.recycler.apply {
                        setHasFixedSize(true)

                        val gridLayoutManager = GridLayoutManager(context, 3)
                        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL

                        layoutManager = gridLayoutManager

                        adapter = rosterRecyclerViewAdapter
                        if (response.data is List<*>)
                            rosterRecyclerViewAdapter.setData(response.data.filterIsInstance<Character>())
                    }
                }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                    txtError.visibility = View.VISIBLE
                    txtError.text = response.message
                }
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
            }
        })
        progress.visibility = View.VISIBLE

        viewModel.setGuildSearch(params.realm, params.guildName)
    }
}