package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

public class BaseResponseDto {
    @SerializedName("hydra:totalItems")
    private int totalItems;

    @SerializedName("hydra:view")
    private ViewDto viewDto;

    public ViewDto getViewDto() {
        return viewDto;
    }

    public void setViewDto(ViewDto viewDto) {
        this.viewDto = viewDto;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
