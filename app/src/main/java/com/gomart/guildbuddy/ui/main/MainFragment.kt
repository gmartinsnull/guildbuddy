package com.gomart.guildbuddy.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gomart.guildbuddy.R
import com.gomart.guildbuddy.databinding.FragmentMainBinding
import com.gomart.guildbuddy.vo.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel: GuildSearchViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        binding.btnSearch.setOnClickListener {
            val inputMethodManager =
                context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            findNavController().navigate(
                MainFragmentDirections.actionMainToRoster(
                    binding.edtRealm.text.toString().replace(" ", "-"),
                    binding.edtGuildName.text.toString().replace(" ", "-"),
                    binding.radioRegion.findViewById<RadioButton>(binding.radioRegion.checkedRadioButtonId).text.toString()
                        .toLowerCase(Locale.getDefault())
                )
            )
        }

        findNavController().addOnDestinationChangedListener { _, _, _ ->
            (requireActivity() as MainActivity).title =
                findNavController().currentDestination?.label
        }

        viewModel.getGuild().observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    findNavController().navigate(
                        MainFragmentDirections.actionMainToRoster(
                            binding.edtRealm.text.toString().replace(" ", "-"),
                            binding.edtGuildName.text.toString().replace(" ", "-"),
                            binding.radioRegion.findViewById<RadioButton>(binding.radioRegion.checkedRadioButtonId).text.toString()
                                .toLowerCase(Locale.getDefault())
                        )
                    )
                }
                else -> {
                    // todo: handle error and loading properly
                }
            }
        })
    }

    /**
     * initial setup
     */
    private fun setup() {
        viewModel.fetchToken()

        val searchData = viewModel.getSearchData()
        if (searchData.isNotEmpty() && searchData.size == 3) {
            binding.edtRealm.setText(searchData[0])
            binding.edtGuildName.setText(searchData[1])
            if (searchData[2] == "eu") {
                binding.radioRegion.findViewById<RadioButton>(R.id.eu).isChecked = true
            } else {
                binding.radioRegion.findViewById<RadioButton>(R.id.us).isChecked = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}