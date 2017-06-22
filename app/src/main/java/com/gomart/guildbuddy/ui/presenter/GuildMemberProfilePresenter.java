package com.gomart.guildbuddy.ui.presenter;

import android.content.Context;

import com.gomart.guildbuddy.BuildConfig;
import com.gomart.guildbuddy.model.Character;
import com.gomart.guildbuddy.network.GetCharacterResponse;
import com.gomart.guildbuddy.network.interfaces.CharacterService;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by glaubermartins on 2017-06-14.
 */

public class GuildMemberProfilePresenter {

    private static final String CHAR_ITEMS = "items";

    private Context context;
    private CharacterService service;
    private Character character;
    private String realm;

    public GuildMemberProfilePresenter(Context context, Character character, String realm){

        this.context = context;
        this.character = character;
        this.realm = realm;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.URL_START+BuildConfig.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(CharacterService.class);

        ArrayList<String> fields = new ArrayList<>();
        fields.add(CHAR_ITEMS);

        character.setFields(fields);

    }

    public void getCharacter(Callback<GetCharacterResponse> callback) {
        Call<GetCharacterResponse> call = service.getCharacter(realm, character.getName(), character.getFields(), BuildConfig.LOCALE, BuildConfig.API_KEY);
        call.enqueue(callback);
    }
}
