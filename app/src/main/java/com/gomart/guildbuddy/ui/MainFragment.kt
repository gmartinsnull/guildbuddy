package com.gomart.guildbuddy.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.viewmodel.GuildSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: GuildSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchToken()
        val searchData = viewModel.getSearchData()
        if (searchData.isNotEmpty() && searchData.size == 2) {
            edtRealm.setText(searchData[0])
            edtGuildName.setText(searchData[1])
        }

        btnSearch.setOnClickListener {
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            findNavController().navigate(
                    MainFragmentDirections.actionMainToRoster(
                            edtRealm.text.toString().replace(" ", "-"),
                            edtGuildName.text.toString().replace(" ", "-")
                    )
            )

            viewModel.storeSearchData(edtRealm.text.toString(), edtGuildName.text.toString())
        }
    }
}