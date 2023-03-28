package com.caudron.amusementpark.viewmodels.api_view_model;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ApiViewModelFactory implements ViewModelProvider.Factory {

    private final Application mApplication;
    private final String mToken;

    public ApiViewModelFactory(Application application, String token) {
        mApplication = application;
        mToken = token;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ApiViewModel.class)) {
            return (T) new ApiViewModel(mApplication, mToken);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}