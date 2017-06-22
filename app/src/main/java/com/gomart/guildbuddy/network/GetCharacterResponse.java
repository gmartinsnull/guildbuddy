package com.gomart.guildbuddy.network;

import com.gomart.guildbuddy.model.Character;

/**
 * Created by glaubermartins on 2017-06-14.
 */

public class GetCharacterResponse {
    private int achievementPoints;
    private int totalHonorableKills;
    private String thumbnail;
    private Character.Items items;

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public int getTotalHonorableKills() {
        return totalHonorableKills;
    }

    public void setTotalHonorableKills(int totalHonorableKills) {
        this.totalHonorableKills = totalHonorableKills;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Character.Items getItems() {
        return items;
    }

    public void setItems(Character.Items items) {
        this.items = items;
    }
}
