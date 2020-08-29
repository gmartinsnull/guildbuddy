package com.gomart.guildbuddy.network;

import com.gomart.guildbuddy.vo.CharacterClass;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by glaubermartins on 2016-11-28.
 */

public class GetCharacterClassesResponse {

    @SerializedName("classes")
    private ArrayList<CharacterClass> classes;

    public ArrayList<CharacterClass> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<CharacterClass> classes) {
        this.classes = classes;
    }
}
