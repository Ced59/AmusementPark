package com.caudron.amusementpark.models.dtos;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatusesResponseDto extends BaseResponseDto {
    @SerializedName("hydra:member")
    private List<StatusDto> statuses;

    public List<StatusDto> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<StatusDto> statuses) {
        this.statuses = statuses;
    }
}
