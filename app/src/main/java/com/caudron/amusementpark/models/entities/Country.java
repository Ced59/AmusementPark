package com.caudron.amusementpark.models.entities;

import java.util.List;

public class Country {
    private int id;
    private String name;
    private boolean isRateable;
    private List<Park> parks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRateable() {
        return isRateable;
    }

    public void setRateable(boolean rateable) {
        isRateable = rateable;
    }

    public List<Park> getParks() {
        return parks;
    }

    public void setParks(List<Park> parks) {
        this.parks = parks;
    }
}
