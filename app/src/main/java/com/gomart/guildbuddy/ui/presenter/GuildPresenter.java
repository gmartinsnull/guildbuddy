package com.gomart.guildbuddy.ui.presenter;

import android.content.Context;

import com.gomart.guildbuddy.network.GetGuildMembersResponse;
import com.gomart.guildbuddy.network.services.GuildService;
import com.gomart.guildbuddy.vo.Guild;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by glaubermartins on 2016-11-24.
 */

@Deprecated
public class GuildPresenter {

    private Context context;
    private GuildService guildService;
    private Guild guild;

    public GuildPresenter(Context context, Guild guild){

        this.context = context;
        this.guild = guild;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(BuildConfig.URL_START+BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        guildService = retrofit.create(GuildService.class);

    }

    public void getGuild(Callback<GetGuildMembersResponse> callback){
        /*Call<GetGuildMembersResponse> call = guildService.getGuildRoster(guild.getRealm(), guild.getName(), BuildConfig.LOCALE, BuildConfig.API_KEY);
        call.enqueue(callback);*/
    }

}
