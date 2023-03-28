package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponseDto extends BaseResponseDto {

    @SerializedName("hydra:member")
    private List<ImageDto> imageDtos;

    public List<ImageDto> getImageDtos() {
        return imageDtos;
    }

    public void setImageDtos(List<ImageDto> imageDtos) {
        this.imageDtos = imageDtos;
    }

}
