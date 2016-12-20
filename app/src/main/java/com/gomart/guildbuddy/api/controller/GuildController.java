package com.gomart.guildbuddy.api.controller;

import android.content.Context;

import com.gomart.guildbuddy.BuildConfig;
import com.gomart.guildbuddy.api.GetGuildMembersResponse;
import com.gomart.guildbuddy.api.interfaces.GuildService;
import com.gomart.guildbuddy.model.Guild;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public class GuildController {

    private Context c;
    private GuildService guildService;
    private Guild guild;

    private static final String URL_START = "https://us.";
    private static final String API_KEY = "33nxy4qemg2zmf9dyvk3qzbpmzsezjtg";
    private static final String LOCALE = "en_US";


    public GuildController init(Context context, Guild guild){ //DI

        c = context;
        this.guild = guild;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor());

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_START+BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        guildService = retrofit.create(GuildService.class);


        return this;
    }

    public void getGuild(Callback<GetGuildMembersResponse> callback){
        Call<GetGuildMembersResponse> call = guildService.getGuildMembers(guild.getRealm(), guild.getName(), guild.getFields(), LOCALE, API_KEY);
        call.enqueue(callback);
    }

}
