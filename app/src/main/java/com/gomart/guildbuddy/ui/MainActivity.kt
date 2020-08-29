package com.gomart.guildbuddy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.gomart.guildbuddy.R
import dagger.hilt.android.AndroidEntryPoint

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NavigationUI.setupActionBarWithNavController(
                this,
                Navigation.findNavController(findViewById(R.id.nav_host_fragment))
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return (Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigateUp()
                || super.onSupportNavigateUp())
    }
}