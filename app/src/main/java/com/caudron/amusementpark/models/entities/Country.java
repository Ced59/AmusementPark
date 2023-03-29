package com.caudron.amusementpark.models.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "countries")
public class Country {
    @PrimaryKey
    @NonNull
    private int id;
    private String name;
    private boolean isRateable;
    @Ignore
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