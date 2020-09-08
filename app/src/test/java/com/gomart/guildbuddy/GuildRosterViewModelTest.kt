package com.gomart.guildbuddy

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gomart.guildbuddy.util.mock
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.viewmodel.GuildRosterViewModel
import com.gomart.guildbuddy.vo.Character
import com.gomart.guildbuddy.vo.GuildCharacter
import com.gomart.guildbuddy.vo.Resource
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

/**
 *   Created by gmartins on 2020-09-07
 *   Description:
 */
@RunWith(JUnit4::class)
class GuildRosterViewModelTest {
    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    private val repository = mock(GuildRepository::class.java)
    private val charRepository = mock(CharacterRepository::class.java)
    private val context = mock(Context::class.java)
    private val sharedPreferences = mock(SharedPreferences::class.java)
    private lateinit var viewModel: GuildRosterViewModel

    @Before
    fun init() {
        val sharedPrefs = SharedPrefs(sharedPreferences)
        viewModel = GuildRosterViewModel(context, repository, charRepository, sharedPrefs)
    }

    @Test
    fun empty() {
        val result = mock<Observer<Resource<List<Character>>>>()
        viewModel.roster.observeForever(result)
        verifyNoMoreInteractions(repository)
    }

    //todo TBI(to be implemented)
    /*@Test
    fun basic() {
        val roster = mock<List<GuildCharacter>>()
        `when`(repository.getGuildRoster("tichondrius", "chicken lords"))
                .thenReturn(flowOf(Resource.Success(roster)))

        val result = mock<Observer<Resource<List<Character>>>>()
        viewModel.roster.observeForever(result)
        viewModel.setGuildSearch("tichondrius", "chicken lords")
        verify(repository).getGuildRoster("tichondrius", "chicken lords")

        assert(true)
    }*/
}