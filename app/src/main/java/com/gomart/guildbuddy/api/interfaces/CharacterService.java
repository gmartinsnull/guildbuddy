package com.gomart.guildbuddy.api.interfaces;

import com.gomart.guildbuddy.api.GetCharacterClassesResponse;
import com.gomart.guildbuddy.api.GetCharacterRacesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by glaubermartins on 2016-11-28.
 */

public interface CharacterService {

    @GET("wow/data/character/classes")
    Call<GetCharacterClassesResponse> getClassName(@Query("locale") String locale, @Query("apikey") String apiKey);

    @GET("wow/data/character/races")
    Call<GetCharacterRacesResponse> getRaceName(@Query("locale") String locale, @Query("apikey") String apiKey);
}
