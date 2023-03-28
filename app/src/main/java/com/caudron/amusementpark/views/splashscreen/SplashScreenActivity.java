package com.caudron.amusementpark.views.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.dtos.CoasterDto;
import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.dtos.ImageDto;
import com.caudron.amusementpark.models.dtos.ImagesResponseDto;
import com.caudron.amusementpark.models.dtos.ParkDto;
import com.caudron.amusementpark.models.dtos.ParksResponseDto;
import com.caudron.amusementpark.models.dtos.StatusDto;
import com.caudron.amusementpark.models.dtos.StatusesResponseDto;
import com.caudron.amusementpark.viewmodels.api_view_model.ApiViewModel;
import com.caudron.amusementpark.viewmodels.api_view_model.ApiViewModelFactory;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;
import com.caudron.amusementpark.views.MainActivity;

import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mLoaderIcon;
    private TextView mBackgroundTaskInfo;

    private ApiViewModel mApiViewModel;
    private DatabaseViewModel mDatabaseViewModel;

    private String mAuthToken = "a44c7304-5658-4434-b8ca-aa24d0c07845";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mLoaderIcon = findViewById(R.id.loaderIcon);
        mBackgroundTaskInfo = findViewById(R.id.backgroundTaskInfo);

        mApiViewModel = new ViewModelProvider(this, new ApiViewModelFactory(getApplication(), mAuthToken)).get(ApiViewModel.class);
        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        // Call API asynchronously
        loadCoasters();
    }

    private void loadCoasters() {
        mApiViewModel.getCoasters(mAuthToken, 1).observe(this, new Observer<CoastersResponseDto>() {
            @Override
            public void onChanged(CoastersResponseDto coasters) {
                if (coasters != null && coasters.getCoasterDtos() != null) {
                    // Insert coasters into database
                    //mDatabaseViewModel.insertCoasters(coasters);
                    loadImageUrls();
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadImageUrls() {
        mApiViewModel.getImageUrls(mAuthToken).observe(this, new Observer<ImagesResponseDto>() {
            @Override
            public void onChanged(ImagesResponseDto images) {
                if (images != null && images.getImages() != null) {
                    // Insert image URLs into database
                    //mDatabaseViewModel.insertImages(images);
                    loadParks();
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadParks() {
        mApiViewModel.getParks(mAuthToken).observe(this, new Observer<ParksResponseDto>() {
            @Override
            public void onChanged(ParksResponseDto parks) {
                if (parks != null && parks.getParks() != null) {
                    // Insert parks into database
                    //mDatabaseViewModel.insertParks(parks);
                    loadStatuses();
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadStatuses() {
        mApiViewModel.getStatuses(mAuthToken).observe(this, new Observer<StatusesResponseDto>() {
            @Override
            public void onChanged(StatusesResponseDto statuses) {
                if (statuses != null && statuses.getStatuses() != null) {
                    // Insert statuses into database
                    //mDatabaseViewModel.insertStatuses(statuses);
                    launchMainActivity();
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showErrorToast() {
        Toast.makeText(SplashScreenActivity.this, "Une erreur s'est produite lors du chargement des données.", Toast.LENGTH_LONG).show();
    }
}