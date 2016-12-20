package com.gomart.guildbuddy.api.interfaces;

import com.gomart.guildbuddy.api.GetGuildMembersResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public interface GuildService {

    @GET("wow/guild/{realm}/{guildName}")
    Call<GetGuildMembersResponse> getGuildMembers(@Path("realm") String realm, @Path("guildName") String guildName, @Query("fields") ArrayList<String> fields, @Query("locale") String locale, @Query("apikey") String apiKey);

}
