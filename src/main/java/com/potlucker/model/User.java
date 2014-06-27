package com.potlucker.model;

/**
 * Created by treznick on 6/25/14.
 */

import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {



    private String key;
    private String name;
    private ArrayList<String> pantry;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getPantry() {
        return pantry;
    }
     @JsonDeserialize(as = ArrayList.class, contentAs = String.class)
    public void setPantry(ArrayList<String> pantry) {
        this.pantry = pantry;
    }


}
