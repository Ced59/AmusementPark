package com.caudron.amusementpark.viewmodels.api_view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.caudron.amusementpark.models.apiclients.ApiService;
import com.caudron.amusementpark.models.apiclients.RetrofitClient;
import com.caudron.amusementpark.models.dtos.CoasterDto;
import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.dtos.ImageDto;
import com.caudron.amusementpark.models.dtos.ImagesResponseDto;
import com.caudron.amusementpark.models.dtos.ParkDto;
import com.caudron.amusementpark.models.dtos.ParksResponseDto;
import com.caudron.amusementpark.models.dtos.StatusDto;
import com.caudron.amusementpark.models.dtos.StatusesResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class ApiViewModel extends AndroidViewModel {

    private final ApiService mApiService;

    public ApiViewModel(Application application, String token) {
        super(application);
        mApiService = RetrofitClient.getInstance(token).getApiService();
    }

    public LiveData<CoastersResponseDto> getCoasters(String authToken, int page) {
        MutableLiveData<CoastersResponseDto> data = new MutableLiveData<>();
        mApiService.getCoasters(authToken, page).enqueue(new Callback<CoastersResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<CoastersResponseDto> call, @NonNull Response<CoastersResponseDto> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<CoastersResponseDto> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<ImagesResponseDto> getImageUrls(String authToken, int page) {
        MutableLiveData<ImagesResponseDto> data = new MutableLiveData<>();

        mApiService.getImages(authToken, page).enqueue(new Callback<ImagesResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<ImagesResponseDto> call, @NonNull Response<ImagesResponseDto> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<ImagesResponseDto> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<ParksResponseDto> getParks(String authToken, int page) {
        MutableLiveData<ParksResponseDto> data = new MutableLiveData<>();

        mApiService.getParks(authToken, page).enqueue(new Callback<ParksResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<ParksResponseDto> call, @NonNull Response<ParksResponseDto> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ParksResponseDto> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<StatusesResponseDto> getStatuses(String authToken, int page) {
        MutableLiveData<StatusesResponseDto> data = new MutableLiveData<>();

        mApiService.getStatuses(authToken, page).enqueue(new Callback<StatusesResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<StatusesResponseDto> call, @NonNull Response<StatusesResponseDto> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatusesResponseDto> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}