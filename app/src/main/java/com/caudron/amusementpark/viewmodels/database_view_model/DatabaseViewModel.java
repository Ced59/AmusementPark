package com.caudron.amusementpark.viewmodels.database_view_model;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;

import com.caudron.amusementpark.viewmodels.database_view_model.callback.DatabaseCallback;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {

    public DatabaseViewModel(Application application) {
        super(application);
    }

    public void saveDataToDatabase(List<String> dataList, DatabaseCallback callback) {
        // Simulate database save
        new Handler().postDelayed(() -> {
            // Simulate database save success
            callback.onSuccess();
        }, 3000);
    }


}