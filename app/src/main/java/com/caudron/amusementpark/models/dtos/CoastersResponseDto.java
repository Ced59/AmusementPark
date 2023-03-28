package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CoastersResponseDto extends BaseResponseDto {

    @SerializedName("hydra:member")
    private List<CoasterDto> coasterDtos;

    public List<CoasterDto> getCoasterDtos() {
        return coasterDtos;
    }
    public void setCoasterDtos(List<CoasterDto> coasterDtos) {
        this.coasterDtos = coasterDtos;
    }

}
