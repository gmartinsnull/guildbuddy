package com.gomart.guildbuddy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gomart.guildbuddy.network.services.CharacterService
import com.gomart.guildbuddy.network.services.OAuthService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *   Created by gmartins on 2020-08-30
 *   Description:
 */
@RunWith(JUnit4::class)
class CharacterServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: CharacterService
    private lateinit var oathService: OAuthService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterService::class.java)
        oathService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.URL_TOKEN))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OAuthService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun search() = runBlocking {
        enqueueResponse("characters.json", emptyMap())
        val response = service.getCharacter(
            "tichondrius",
            "refakthug",
            BuildConfig.NAMESPACE,
            BuildConfig.LOCALE,
            oathService.fetchToken(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                BuildConfig.GRANT_TYPE
            ).accessToken
        )

        assert(response.body()?.charClass?.name == "Rogue")
        assert(response.body()?.name == "Refakthug")
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        source?.readString(Charsets.UTF_8)?.let {
            mockResponse.setBody(it)
        }?.let {
            mockWebServer.enqueue(it)
        }
    }
}