package com.gomart.guildbuddy.manager;

import android.content.Context;

import com.gomart.guildbuddy.model.CharacterClass;
import com.gomart.guildbuddy.model.CharacterRace;

import java.util.ArrayList;

import javax.inject.Singleton;

/**
 * Created by glaubermartins on 2016-11-29.
 */

public class DataManager {

    private static DataManager mInstance = new DataManager();
    private ArrayList<CharacterClass> classes;
    private ArrayList<CharacterRace> races;

    private Context context;

    private DataManager() {
    }

    public void init(Context c){
        context = c;
    }

    public static DataManager getInstance(){
        return mInstance;
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
}
