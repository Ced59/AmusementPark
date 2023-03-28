package com.caudron.amusementpark.views.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.dtos.ImagesResponseDto;
import com.caudron.amusementpark.models.dtos.ParksResponseDto;
import com.caudron.amusementpark.models.dtos.StatusesResponseDto;
import com.caudron.amusementpark.viewmodels.api_view_model.ApiViewModel;
import com.caudron.amusementpark.viewmodels.api_view_model.ApiViewModelFactory;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;
import com.caudron.amusementpark.views.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mLoaderIcon;
    private TextView mBackgroundTaskInfoType;
    private TextView mBackgroundTaskInfoProgress;

    private int coasterNbPages = 0;

    private ApiViewModel mApiViewModel;
    private DatabaseViewModel mDatabaseViewModel;

    private String mAuthToken = "a44c7304-5658-4434-b8ca-aa24d0c07845";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mLoaderIcon = findViewById(R.id.loaderIcon);
        mBackgroundTaskInfoType = findViewById(R.id.backgroundTaskInfoType);
        mBackgroundTaskInfoProgress = findViewById(R.id.backgroundTaskInfoProgress);


        mApiViewModel = new ViewModelProvider(this, new ApiViewModelFactory(getApplication(), mAuthToken)).get(ApiViewModel.class);
        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        // Call API asynchronously
        loadCoasters();
    }

    private void loadCoastersRecursive(final int currentPage, int nbPages) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBackgroundTaskInfoProgress.setText("Chargement de la page n° " + currentPage + " sur " + nbPages);
            }
        });
        mApiViewModel.getCoasters(mAuthToken, currentPage).observe(this, new Observer<CoastersResponseDto>() {
            @Override
            public void onChanged(CoastersResponseDto coasters) {
                if (coasters != null && coasters.getCoasterDtos() != null) {
                    // Insert coasters into database
                    //mDatabaseViewModel.insertCoasters(coasters);

                    Pattern pattern = Pattern.compile("page=(\\d+)");
                    Matcher matcher = pattern.matcher(coasters.getViewDto().getLastPage());
                    if (matcher.find()){
                        String numberStr = matcher.group(1);
                        coasterNbPages = Integer.parseInt(numberStr);
                    }
                    else {
                        showErrorToast();
                    }

                    if (currentPage < coasterNbPages) {
                        // Load next page recursively
                        loadCoastersRecursive(currentPage + 1, coasterNbPages);
                    } else {
                        // This is the last page, so load image URLs now
                        loadImageUrls();
                    }
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadCoasters() {
        mBackgroundTaskInfoType.setText("Chargement des coasters...");
        mApiViewModel.getCoasters(mAuthToken, 1).observe(this, new Observer<CoastersResponseDto>() {
            @Override
            public void onChanged(CoastersResponseDto coasters) {
                if (coasters != null && coasters.getCoasterDtos() != null) {


                    Pattern pattern = Pattern.compile("page=(\\d+)");
                    Matcher matcher = pattern.matcher(coasters.getViewDto().getLastPage());
                    if (matcher.find()){
                        String numberStr = matcher.group(1);
                        coasterNbPages = Integer.parseInt(numberStr);
                    }
                    else {
                        showErrorToast();
                    }


                    loadCoastersRecursive(1, coasterNbPages); // Start loading pages recursively
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