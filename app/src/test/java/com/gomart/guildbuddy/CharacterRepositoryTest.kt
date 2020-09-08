package com.gomart.guildbuddy

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gomart.guildbuddy.data.AppDatabase
import com.gomart.guildbuddy.data.CharacterDao
import com.gomart.guildbuddy.data.SharedPrefs
import com.gomart.guildbuddy.network.CharacterResponse
import com.gomart.guildbuddy.network.services.CharacterService
import com.gomart.guildbuddy.repository.CharacterRepository
import com.gomart.guildbuddy.vo.*
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import retrofit2.Response

/**
 *   Created by gmartins on 2020-09-07
 *   Description:
 */
@RunWith(JUnit4::class)
class CharacterRepositoryTest {
    private lateinit var repository: CharacterRepository
    private lateinit var sharedPrefs: SharedPrefs
    private val dao = mock(CharacterDao::class.java)
    private val service = mock(CharacterService::class.java)
    private val sharedPreferences = mock(SharedPreferences::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.characterDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        sharedPrefs = SharedPrefs(sharedPreferences)
        repository = CharacterRepository(service, dao, sharedPrefs)
    }

    @Test
    fun fetchCharacterFromNetwork() = runBlocking {
        val characterResponse = CharacterResponse(
                99,
                "Bukky",
                CharacterClass(99, "rogue"),
                CharacterRace(99, "nightborne"),
                CharacterSpec(99, "subtlety"),
                99,
                99,
                99
        )

        val responseData = Response.success(characterResponse)

        val token = "8hf89nq3ubg093nbbhjbzphajbahvcr230plav"
        `when`(service.getCharacter(
                "tichondrius",
                "bukky",
                BuildConfig.NAMESPACE,
                BuildConfig.LOCALE,
                token
        )).thenReturn(responseData)

        val character = Character(
                99,
                "Bukky",
                "nightborne",
                99,
                "",
                99,
                99,
                99,
                "rogue",
                "subtlety"
        )
        `when`(dao.getAll()).thenReturn(listOf(character))

        `when`(sharedPrefs.getSharedPrefsByKey("token")).thenReturn(token)
        `when`(sharedPrefs.getSharedPrefsByKey("guild")).thenReturn("lonesome guild")
        `when`(service.getAvatar(
                "tichondrius",
                "bukky",
                BuildConfig.NAMESPACE,
                BuildConfig.LOCALE,
                token
        )).thenReturn(MediaResponse("unknown avatar"))

        val response = repository.getCharacter(
                "tichondrius",
                "bukky"
        ).toList()

        verify(service).getCharacter(
                "tichondrius",
                "bukky",
                BuildConfig.NAMESPACE,
                BuildConfig.LOCALE,
                token
        )
        verify(dao).getAll()

        val result = response[0]
        assert(result is Resource.Success && result.data == listOf(character))
    }

    @Test
    fun fetchCharacterFromNetworkError() = runBlocking {
        val errorResponse = Response.error<Character>(
                400,
                "Login test failed!".toResponseBody("Mockito test".toMediaTypeOrNull())
        )
        `when`(sharedPrefs.getSharedPrefsByKey("guild")).thenReturn("random guild")
        `when`(sharedPrefs.getSharedPrefsByKey("token")).thenReturn(null)
        `when`(service.getCharacter(
                "tichondrius",
                "bukky",
                BuildConfig.NAMESPACE,
                BuildConfig.LOCALE,
                "ugibabgiunaiusjpnui9u293289h8"
        )).thenReturn(errorResponse as Response<CharacterResponse>)

        val result = repository.getCharacter(
                "tichondrius",
                "bukky"
        ).toList()

        val first = result[0]
        assert(first is Resource.Error)
    }
}