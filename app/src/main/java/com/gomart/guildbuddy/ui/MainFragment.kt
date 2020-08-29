package com.gomart.guildbuddy.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.viewmodel.GuildRosterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: GuildRosterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchToken()

        btnSearch.setOnClickListener {
            viewModel.data.observe(viewLifecycleOwner, Observer {
                Log.d("test", "${it.status} | ${it.data}")
            })
            /*findNavController().navigate(
                    MainFragmentDirections.actionMainToRoster(
                            edtRealm.text.toString(),
                            edtGuildName.text.toString()
                    )
            )*/
        }
    }
}