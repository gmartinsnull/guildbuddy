package com.gomart.guildbuddy.api;

import com.gomart.guildbuddy.model.GuildMember;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public class GetGuildMembersResponse {

    @SerializedName("level")
    private int level;
    @SerializedName("achievementPoints")
    private int achievementPoints;
    @SerializedName("members")
    private ArrayList<GuildMember> members;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public ArrayList<GuildMember> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<GuildMember> members) {
        this.members = members;
    }
}
