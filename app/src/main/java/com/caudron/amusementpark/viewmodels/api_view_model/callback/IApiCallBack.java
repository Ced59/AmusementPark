package com.caudron.amusementpark.viewmodels.api_view_model.callback;

import com.caudron.amusementpark.models.dtos.ApiResponse;

public interface IApiCallBack {
    void onSuccess(ApiResponse response);
    void onFailure(String errorMessage);
}
