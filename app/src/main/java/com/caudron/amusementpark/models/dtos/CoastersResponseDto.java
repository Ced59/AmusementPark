package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoastersResponseDto {

    @SerializedName("hydra:member")
    private List<CoasterDto> coasterDtos;

    @SerializedName("hydra:totalItems")
    private int totalItems;

    @SerializedName("hydra:view")
    private ViewDto viewDto;


    public List<CoasterDto> getCoasterDtos() {
        return coasterDtos;
    }

    public void setCoasterDtos(List<CoasterDto> coasterDtos) {
        this.coasterDtos = coasterDtos;
    }

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
}
