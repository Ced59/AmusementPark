package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatusesResponseDto {
    @SerializedName("hydra:member")
    private List<StatusDto> statuses;

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

    public List<StatusDto> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StatusDto> statuses) {
        this.statuses = statuses;
    }
}
