package com.gomart.guildbuddy.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by glaubermartins on 2016-11-24.
 */

public class Guild implements Serializable{

    private String name;
    private String realm;
    private ArrayList<String> fields;

    public Guild(String name, String realm) {
        this.name = name;
        this.realm = realm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }
}
