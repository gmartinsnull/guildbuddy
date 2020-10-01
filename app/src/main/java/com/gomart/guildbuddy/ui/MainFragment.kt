package com.gomart.guildbuddy.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.viewmodel.GuildSearchViewModel
import com.gomart.guildbuddy.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: GuildSearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()

        btnSearch.setOnClickListener {
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            findNavController().navigate(
                    MainFragmentDirections.actionMainToRoster(
                            edtRealm.text.toString().replace(" ", "-"),
                            edtGuildName.text.toString().replace(" ", "-"),
                            radioRegion.findViewById<RadioButton>(radioRegion.checkedRadioButtonId).text.toString().toLowerCase(Locale.getDefault())
                    )
            )
        }

        findNavController().addOnDestinationChangedListener { _, _, _ ->
            (requireActivity() as MainActivity).title = findNavController().currentDestination?.label
        }

        viewModel.getGuild().observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    findNavController().navigate(
                            MainFragmentDirections.actionMainToRoster(
                                    edtRealm.text.toString().replace(" ", "-"),
                                    edtGuildName.text.toString().replace(" ", "-"),
                                    radioRegion.findViewById<RadioButton>(radioRegion.checkedRadioButtonId).text.toString().toLowerCase(Locale.getDefault())
                            )
                    )
                }
            }
        })
    }

    /**
     * initial setup
     */
    private fun setup(){
        viewModel.fetchToken()

        val searchData = viewModel.getSearchData()
        if (searchData.isNotEmpty() && searchData.size == 3) {
            edtRealm.setText(searchData[0])
            edtGuildName.setText(searchData[1])
            if (searchData[2] == "eu") {
                radioRegion.findViewById<RadioButton>(R.id.eu).isChecked = true
            }else{
                radioRegion.findViewById<RadioButton>(R.id.us).isChecked = true
            }
        }
    }
}