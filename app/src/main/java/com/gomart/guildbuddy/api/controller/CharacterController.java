package com.gomart.guildbuddy.api.controller;

import android.content.Context;

import com.gomart.guildbuddy.BuildConfig;
import com.gomart.guildbuddy.api.GetCharacterClassesResponse;
import com.gomart.guildbuddy.api.GetCharacterRacesResponse;
import com.gomart.guildbuddy.api.interfaces.CharacterService;
import com.gomart.guildbuddy.model.CharacterClass;
import com.gomart.guildbuddy.model.GuildMember;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by glaubermartins on 2016-11-28.
 */

public class CharacterController {

    private Context c;
    private CharacterService characterService;
    private GuildMember guildMember;

    private static final String URL_START = "https://us.";
    private static final String API_KEY = "33nxy4qemg2zmf9dyvk3qzbpmzsezjtg";
    private static final String LOCALE = "en_US";

    public CharacterController init(Context context){ //DI

        c = context;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor());

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_START+ BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        characterService = retrofit.create(CharacterService.class);


        return this;
    }

    public void getClassName(Callback<GetCharacterClassesResponse> callback){
        Call<GetCharacterClassesResponse> call = characterService.getClassName(LOCALE, API_KEY);
        call.enqueue(callback);
    }

    public void getRaceName(Callback<GetCharacterRacesResponse> callback){
        Call<GetCharacterRacesResponse> call = characterService.getRaceName(LOCALE, API_KEY);
        call.enqueue(callback);
    }

    public String getThumbnail(String url){
        String fullUrl = URL_START + BuildConfig.URL + url;
        return fullUrl;
    }
}
