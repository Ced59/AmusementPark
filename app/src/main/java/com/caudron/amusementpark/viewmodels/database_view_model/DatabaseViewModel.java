package com.caudron.amusementpark.viewmodels.database_view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.caudron.amusementpark.models.dtos.CoastersResponseDto;


public class DatabaseViewModel extends AndroidViewModel {

    public DatabaseViewModel(Application application) {
        super(application);
    }


    public void insertCoasters(CoastersResponseDto coasters) {
    }
}