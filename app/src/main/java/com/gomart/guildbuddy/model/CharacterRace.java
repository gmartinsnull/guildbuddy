package com.gomart.guildbuddy.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glaubermartins on 2016-11-29.
 */

public class CharacterRace implements Serializable {

    @SerializedName("id")
    private int id;
    private String side;
    private String name;

    public CharacterRace(int id, String side, String name) {
        this.id = id;
        this.side = side;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
