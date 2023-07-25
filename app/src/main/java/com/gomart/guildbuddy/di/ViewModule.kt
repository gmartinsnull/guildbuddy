package com.gomart.guildbuddy.di

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.gomart.guildbuddy.ui.roster.RosterRecyclerViewAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

/**
 *   Created by gmartins on 2020-08-28
 *   Description:
 */
@Module
@InstallIn(FragmentComponent::class)
object ViewModule {
    @Provides
    fun provideRosterRecyclerViewAdapter(@ActivityContext context: Context): RecyclerView.Adapter<RosterRecyclerViewAdapter.RosterViewHolder> =
            RosterRecyclerViewAdapter(context)
}