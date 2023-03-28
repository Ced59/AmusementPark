package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParksResponseDto {
    @SerializedName("hydra:member")
    private List<ParkDto> parks;

    @SerializedName("hydra:totalItems")
    private int totalItems;

    @SerializedName("hydra:view")
    private ViewDto viewDto;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public ViewDto getViewDto() {
        return viewDto;
    }

    public void setViewDto(ViewDto viewDto) {
        this.viewDto = viewDto;
    }

    public List<ParkDto> getParks() {
        return parks;
    }

    public void setParks(List<ParkDto> parks) {
        this.parks = parks;
    }
}
