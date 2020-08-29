package com.gomart.guildbuddy.ui.presenter

import android.content.Context
import com.gomart.guildbuddy.BuildConfig
import com.gomart.guildbuddy.vo.Character
import com.gomart.guildbuddy.network.GetCharacterClassesResponse
import com.gomart.guildbuddy.network.GetCharacterRacesResponse
import com.gomart.guildbuddy.network.GetCharacterResponse
import com.gomart.guildbuddy.network.services.CharacterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by glaubermartins on 2017-06-14.
 */

class GuildMemberPresenter(context: Context?, private val realm: String) {
    private val service: CharacterService
    private var character: Character? = null
    fun setCharacterFields(character: Character?) {
        this.character = character
        val fields = ArrayList<String>()
        fields.add(CHAR_ITEMS)
        this.character!!.fields = fields
    }

    fun getCharacter(callback: Callback<GetCharacterResponse>?) {
        /*val call = service.getCharacter(realm, character!!.name, character!!.fields, BuildConfig.LOCALE, BuildConfig.API_KEY)
        call.enqueue(callback)*/
    }

    fun getClassName(callback: Callback<GetCharacterClassesResponse>?) {
        val call = service.getClassName(BuildConfig.LOCALE, BuildConfig.API_KEY)
        //call.enqueue(callback)
    }

    fun getRaceName(callback: Callback<GetCharacterRacesResponse>?) {
        val call = service.getRaceName(BuildConfig.LOCALE, BuildConfig.API_KEY)
        //call.enqueue(callback)
    }

    /*fun getThumbnail(url: String): String {
        return BuildConfig.URL_START + BuildConfig.URL + url
    }*/

    companion object {
        private const val CHAR_ITEMS = "items"
    }

    init {
        val builder = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        val client = builder.build()
        val retrofit = Retrofit.Builder()
                //.baseUrl(BuildConfig.URL_START + BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        service = retrofit.create(CharacterService::class.java)
    }
}