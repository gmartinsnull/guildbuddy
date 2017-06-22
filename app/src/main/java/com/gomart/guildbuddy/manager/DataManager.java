package com.gomart.guildbuddy.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ImageView;

import com.gomart.guildbuddy.model.CharacterClass;
import com.gomart.guildbuddy.model.CharacterRace;
import com.gomart.guildbuddy.model.Guild;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by glaubermartins on 2016-11-29.
 */

public class DataManager {

    private ArrayList<CharacterClass> classes;
    private ArrayList<CharacterRace> races;
    private Guild guild;

    private SharedPreferences sharedPref;

    private Picasso picasso;

    public DataManager(SharedPreferences sharedPreferences) {
        this.sharedPref = sharedPreferences;
    }

    public ArrayList<CharacterClass> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<CharacterClass> classes) {
        this.classes = classes;
    }

    public ArrayList<CharacterRace> getRaces() {
        return races;
    }

    public void setRaces(ArrayList<CharacterRace> races) {
        this.races = races;
    }

    public String getCharacterClass(int id){
        for (int i=0; i<classes.size(); i++) {
            if (id == classes.get(i).getId())
                return classes.get(i).getName();
        }
        return "";
    }

    public String getCharacterRace(int id){
        for (int i=0; i<races.size(); i++) {
            if (id == races.get(i).getId())
                return races.get(i).getName();
        }
        return "";
    }

    public Guild getGuild() {
        return guild;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }


    /*SHARED PREFERENCES*/
    public void save(String key, String value){
        sharedPref.edit().putString(key, value).apply();
    }
    public void save(String key, int value){
        sharedPref.edit().putInt(key, value).apply();
    }
    public void save(String key, float value){
        sharedPref.edit().putFloat(key, value).apply();
    }
    public void save(String key, boolean value){
        sharedPref.edit().putBoolean(key, value).apply();
    }

    public String get(String key, String defaultValue){
        return sharedPref.getString(key, defaultValue);
    }
    public int get(String key, int defaultValue){
        return sharedPref.getInt(key, defaultValue);
    }
    public float get(String key, float defaultValue){
        return sharedPref.getFloat(key, defaultValue);
    }
    public boolean get(String key, boolean defaultValue){
        return sharedPref.getBoolean(key, defaultValue);
    }

    public void delete(String key){
        sharedPref.edit().remove(key).apply();
    }

    /*PICASSO*/
    public void loadPicture(Context c, String url, ImageView view){
        picasso.with(c).load(url).into(view);
    }

}
