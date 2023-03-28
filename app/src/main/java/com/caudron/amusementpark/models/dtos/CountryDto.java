package com.caudron.amusementpark.models.dtos;

import java.util.List;

public class CountryDto {
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

    public List<ParkDto> getParks() {
        return parks;
    }

    public void setParks(List<ParkDto> parks) {
        this.parks = parks;
    }

    private int id;
    private String name;
    private boolean isRateable;
    private List<ParkDto> parks;
}
