package com.gomart.guildbuddy.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by glaubermartins on 2016-11-28.
 */

public class CharacterClass implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("powerType")
    private String powerType;
    @SerializedName("name")
    private String name;

    public CharacterClass(int id, String powerType, String name) {
        this.id = id;
        this.powerType = powerType;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
