package com.gomart.guildbuddy.network.interfaces;

import com.gomart.guildbuddy.network.GetCharacterClassesResponse;
import com.gomart.guildbuddy.network.GetCharacterRacesResponse;
import com.gomart.guildbuddy.network.GetCharacterResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by glaubermartins on 2016-11-28.
 */

public interface CharacterService {

    @GET("wow/data/character/classes")
    Call<GetCharacterClassesResponse> getClassName(@Query("locale") String locale, @Query("apikey") String apiKey);

    @GET("wow/data/character/races")
    Call<GetCharacterRacesResponse> getRaceName(@Query("locale") String locale, @Query("apikey") String apiKey);

    @GET("wow/character/{realm}/{name}")
    Call<GetCharacterResponse> getCharacter(@Path("realm") String realm, @Path("name") String name, @Query("fields") ArrayList<String> fields, @Query("locale") String locale, @Query("apikey") String apiKey);
}
