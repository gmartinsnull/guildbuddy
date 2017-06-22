package com.gomart.guildbuddy.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public class Character implements Parcelable{

    private String name;
    private int race;
    private int gender;
    private int level;
    private String thumbnail;
    private int achievementPoints;
    private Items items;
    private int totalHonorableKills;
    @SerializedName("class")
    private int charClass;
    private Spec spec;
    private ArrayList<String> fields;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Spec getSpec() {
        return spec;
    }

    public void setSpec(Spec spec) {
        this.spec = spec;
    }

    public int getCharClass() {
        return charClass;
    }

    public void setCharClass(int charClass) {
        this.charClass = charClass;
    }

    public int getAchievementPoints() {
        return achievementPoints;
    }

    public void setAchievementPoints(int achievementPoints) {
        this.achievementPoints = achievementPoints;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public int getTotalHonorableKills() {
        return totalHonorableKills;
    }

    public void setTotalHonorableKills(int totalHonorableKills) {
        this.totalHonorableKills = totalHonorableKills;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public static class Spec implements Parcelable{
        private String name;
        private String role;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(role);
        }

        protected Spec(Parcel in) {
            name = in.readString();
            role = in.readString();
        }

        public static final Parcelable.Creator<Spec> CREATOR = new Parcelable.Creator<Spec>() {
            @Override
            public Spec createFromParcel(Parcel in) {
                return new Spec(in);
            }

            @Override
            public Spec[] newArray(int size) {
                return new Spec[size];
            }
        };
    }

    public class Items{
        private int averageItemLevel;
        private int averageItemLevelEquipped;

        public int getAvarageItemLevel() {
            return averageItemLevel;
        }

        public void setAvarageItemLevel(int avarageItemLevel) {
            this.averageItemLevel = avarageItemLevel;
        }

        public int getAverageItemLevelEquipped() {
            return averageItemLevelEquipped;
        }

        public void setAverageItemLevelEquipped(int averageItemLevelEquipped) {
            this.averageItemLevelEquipped = averageItemLevelEquipped;
        }
    }

    //METHODS NEEDED IN ORDER TO PASS OBJ VIA INTENTS
    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(spec, flags);
        dest.writeString(name);
        dest.writeInt(race);
        dest.writeInt(gender);
        dest.writeInt(level);
        dest.writeString(thumbnail);
        dest.writeInt(charClass);
        dest.writeInt(achievementPoints);
        //dest.writeInt(totalHonorableKills);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected Character(Parcel in) {
        spec = in.readParcelable(Spec.class.getClassLoader());
        name = in.readString();
        race = in.readInt();
        gender = in.readInt();
        level = in.readInt();
        thumbnail = in.readString();
        charClass = in.readInt();
        achievementPoints = in.readInt();
        //totalHonorableKills = in.readInt();
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
}
