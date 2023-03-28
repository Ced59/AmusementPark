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

    private ApiService mApiService;

    public ApiViewModel(Application application, String token) {
        super(application);
        mApiService = RetrofitClient.getInstance(token).getApiService();
    }

    public LiveData<CoastersResponseDto> getCoasters(String authToken, int page) {
        MutableLiveData<CoastersResponseDto> data = new MutableLiveData<>();
        final CoastersResponseDto[] responseApi = {new CoastersResponseDto()};
        // FAIRE UN SYSTEME POUR TOUT RECUPERER
        mApiService.getCoasters(authToken,  page).enqueue(new Callback<CoastersResponseDto>() {
            @Override
            public void onResponse(Call<CoastersResponseDto> call, Response<CoastersResponseDto> response) {
                if (response.isSuccessful()) {
                    responseApi[0] = response.body();
                } else {
                    data.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<CoastersResponseDto> call, Throwable t) {
                data.setValue(null);
            }
        });


        return data;
    }

    public LiveData<ImagesResponseDto> getImageUrls(String authToken) {
        MutableLiveData<ImagesResponseDto> data = new MutableLiveData<>();

        mApiService.getImages(authToken, Integer.MAX_VALUE).enqueue(new Callback<ImagesResponseDto>() {
            @Override
            public void onResponse(Call<ImagesResponseDto> call, Response<ImagesResponseDto> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ImagesResponseDto> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<ParksResponseDto> getParks(String authToken) {
        MutableLiveData<ParksResponseDto> data = new MutableLiveData<>();

        mApiService.getParks(authToken, Integer.MAX_VALUE).enqueue(new Callback<ParksResponseDto>() {
            @Override
            public void onResponse(Call<ParksResponseDto> call, Response<ParksResponseDto> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ParksResponseDto> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    public LiveData<StatusesResponseDto> getStatuses(String authToken) {
        MutableLiveData<StatusesResponseDto> data = new MutableLiveData<>();

        mApiService.getStatuses(authToken, Integer.MAX_VALUE).enqueue(new Callback<StatusesResponseDto>() {
            @Override
            public void onResponse(Call<StatusesResponseDto> call, Response<StatusesResponseDto> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<StatusesResponseDto> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}