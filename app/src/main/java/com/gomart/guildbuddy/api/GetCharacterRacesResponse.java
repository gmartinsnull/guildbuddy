package com.gomart.guildbuddy.api;

import com.gomart.guildbuddy.model.CharacterRace;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by glaubermartins on 2016-11-29.
 */

public class GetCharacterRacesResponse {

    @SerializedName("races")
    private ArrayList<CharacterRace> races;

    public ArrayList<CharacterRace> getRaces() {
        return races;
    }

    public void setRaces(ArrayList<CharacterRace> races) {
        this.races = races;
    }
}
