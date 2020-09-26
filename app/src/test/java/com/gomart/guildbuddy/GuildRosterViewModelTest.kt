package com.gomart.guildbuddy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gomart.guildbuddy.util.mock
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.viewmodel.GuildRosterViewModel
import com.gomart.guildbuddy.vo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
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

    private val guildRepo = mock(GuildRepository::class.java)
    private val charRepo = mock(CharacterRepository::class.java)
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun init() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun basic() = runBlocking {
        val charactersResponse = mock<List<Character>>()
        `when`(charRepo.getAllCharacters())
                .thenReturn(charactersResponse)

        `when`(guildRepo.isSameGuild("random guild")).thenReturn(true)

        val observer = mock<Observer<Resource<List<Character>>>>()
        val viewModel = GuildRosterViewModel(guildRepo, charRepo)
        viewModel.roster.observeForever(observer)
        viewModel.setGuildSearch("tichondrius", "random guild")

        verify(guildRepo).isSameGuild("random guild")
        verify(charRepo).getAllCharacters()

        assert(true)
    }

    @Test
    fun newGuild() = runBlocking {
        val apiResponse = mock<List<GuildCharacter>>()
        `when`(guildRepo.getGuildRoster("tichondrius", "new guild"))
                .thenReturn(flowOf(Resource.Success(apiResponse)))

        `when`(guildRepo.isSameGuild("new guild")).thenReturn(false)

        val observer = mock<Observer<Resource<List<Character>>>>()
        val viewModel = GuildRosterViewModel(guildRepo, charRepo)
        viewModel.roster.observeForever(observer)
        viewModel.setGuildSearch("tichondrius", "new guild")

        verify(guildRepo).isSameGuild("new guild")
        verify(guildRepo).getGuildRoster("tichondrius", "new guild")

        assert(true)
    }

    @Test
    fun empty() = runBlocking {
        val result = mock<Observer<Resource<List<Character>>>>()
        val viewModel = GuildRosterViewModel(guildRepo, charRepo)
        viewModel.roster.observeForever(result)
        verifyNoMoreInteractions(guildRepo)
    }
}