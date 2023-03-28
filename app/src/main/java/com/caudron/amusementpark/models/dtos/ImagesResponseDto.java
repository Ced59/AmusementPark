package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponseDto {


    @SerializedName("hydra:member")
    private List<ImageDto> imageDtos;

    @SerializedName("hydra:totalItems")
    private int totalItems;

    @SerializedName("hydra:view")
    private ViewDto viewDto;

    public List<ImageDto> getImageDtos() {
        return imageDtos;
    }

    public void setImageDtos(List<ImageDto> imageDtos) {
        this.imageDtos = imageDtos;
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
