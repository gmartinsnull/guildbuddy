package com.gomart.guildbuddy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gomart.guildbuddy.util.mock
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.repository.GuildRepository
import com.gomart.guildbuddy.util.TestCoroutineRule
import com.gomart.guildbuddy.util.TestCoroutinesContextProvider
import com.gomart.guildbuddy.viewmodel.GuildRosterViewModel
import com.gomart.guildbuddy.vo.*
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
    @Rule
    @JvmField
    val testCoroutineRule = TestCoroutineRule()

    private val guildRepo = mock(GuildRepository::class.java)
    private val charRepo = mock(CharacterRepository::class.java)
    private lateinit var viewModel: GuildRosterViewModel

    @Before
    fun setup() {
        viewModel = GuildRosterViewModel(guildRepo, charRepo, TestCoroutinesContextProvider())
    }

    @Test
    fun basic() = testCoroutineRule.runBlockingTest {
        val charactersResponse = mock<List<Character>>()
        `when`(charRepo.getAllCharacters())
                .thenReturn(charactersResponse)

        `when`(guildRepo.isSameGuild("foo")).thenReturn(true)
        `when`(guildRepo.getGuild()).thenReturn(Resource.Success(Guild("foo", "bar")))

        val observer = mock<Observer<Resource<List<Character>>>>()
        viewModel.roster.observeForever(observer)
        viewModel.setGuildSearch("bar", "foo")

        verify(guildRepo).isSameGuild("foo")
        verify(guildRepo).getGuild()
        verify(charRepo).getAllCharacters()
    }

    @Test
    fun newGuild() = testCoroutineRule.runBlockingTest {
        val apiResponse = mock<List<GuildCharacter>>()
        `when`(guildRepo.getGuildRoster("bar", "foo"))
                .thenReturn(flowOf(Resource.Success(apiResponse)))

        `when`(guildRepo.isSameGuild("foo")).thenReturn(false)

        val observer = mock<Observer<Resource<List<Character>>>>()
        viewModel.roster.observeForever(observer)
        viewModel.setGuildSearch("bar", "foo")

        verify(guildRepo).isSameGuild("foo")
        verify(guildRepo).getGuildRoster("bar", "foo")
    }

    @Test
    fun empty() = testCoroutineRule.runBlockingTest {
        val result = mock<Observer<Resource<List<Character>>>>()
        viewModel.roster.observeForever(result)
        verifyNoMoreInteractions(guildRepo)
    }
}