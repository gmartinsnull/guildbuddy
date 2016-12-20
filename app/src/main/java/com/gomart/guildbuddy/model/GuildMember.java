package com.gomart.guildbuddy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public class GuildMember {

    @SerializedName("character")
    private Character character;
    @SerializedName("rank")
    private int rank;

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }




}
