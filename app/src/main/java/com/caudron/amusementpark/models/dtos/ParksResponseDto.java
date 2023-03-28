package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParksResponseDto {
    @SerializedName("hydra:member")
    private List<ParkDto> parks;

    public List<ParkDto> getParks() {
        return parks;
    }

    public void setParks(List<ParkDto> parks) {
        this.parks = parks;
    }
}
