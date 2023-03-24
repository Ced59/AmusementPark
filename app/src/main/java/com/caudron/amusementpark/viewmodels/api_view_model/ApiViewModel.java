package com.caudron.amusementpark.viewmodels.api_view_model;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.caudron.amusementpark.models.dtos.ApiResponse;
import com.caudron.amusementpark.viewmodels.api_view_model.callback.ApiCallback;

import java.util.ArrayList;

public class ApiViewModel extends AndroidViewModel {

    private MutableLiveData<ApiResponse> mResponseLiveData;

    public ApiViewModel(Application application) {
        super(application);
    }

    public LiveData<ApiResponse> getResponseLiveData() {
        if (mResponseLiveData == null) {
            mResponseLiveData = new MutableLiveData<>();
        }
        return mResponseLiveData;
    }

    public void makeApiCall(ApiCallback callback) {
        new Handler().postDelayed(() -> {
            ApiResponse response = new ApiResponse();
            response.setData(new ArrayList<>());
            callback.onSuccess(response);
        }, 3000);
    }
}
