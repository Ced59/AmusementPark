package com.caudron.amusementpark.views.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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

import kotlin.jvm.functions.Function2;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mLoaderIcon;
    private TextView mBackgroundTaskInfoType;
    private TextView mBackgroundTaskInfoProgress;

    private int coasterNbPages = 0;
    private int imagesNbPages = 0;

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


    private void loadCoasters() {
        mBackgroundTaskInfoType.setText("Chargement des coasters...");
        mApiViewModel.getCoasters(mAuthToken, 1).observe(this, new Observer<CoastersResponseDto>() {
            @Override
            public void onChanged(CoastersResponseDto coasters) {
                if (coasters != null && coasters.getCoasterDtos() != null) {
                    int nbPages = extractPageNumber(coasters.getViewDto().getLastPage());
                    loadDataRecursive(1, nbPages, mApiViewModel::getCoasters, mAuthToken);
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadImageUrls() {
        mBackgroundTaskInfoType.setText("Chargement des images...");
        mApiViewModel.getImageUrls(mAuthToken, 1).observe(this, new Observer<ImagesResponseDto>() {
            @Override
            public void onChanged(ImagesResponseDto images) {
                if (images != null && images.getImageDtos() != null) {
                    int nbPages = extractPageNumber(images.getViewDto().getLastPage());
                    loadDataRecursive(1, nbPages, mApiViewModel::getImageUrls, mAuthToken);
                } else {
                    showErrorToast();
                }
            }
        });
    }


    private void loadParks() {
        mBackgroundTaskInfoType.setText("Chargement des parcs...");
        mApiViewModel.getParks(mAuthToken, 1).observe(this, new Observer<ParksResponseDto>() {
            @Override
            public void onChanged(ParksResponseDto parks) {
                if (parks != null && parks.getParks() != null) {
                    int nbPages = extractPageNumber(parks.getViewDto().getLastPage());
                    loadDataRecursive(1, nbPages, mApiViewModel::getParks, mAuthToken);
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadStatuses() {
        mBackgroundTaskInfoType.setText("Chargement des statuts...");
        mApiViewModel.getStatuses(mAuthToken, 1).observe(this, new Observer<StatusesResponseDto>() {
            @Override
            public void onChanged(StatusesResponseDto statuses) {
                if (statuses != null && statuses.getStatuses() != null) {
                    int nbPages = extractPageNumber(statuses.getViewDto().getLastPage());
                    loadDataRecursive(1, nbPages, mApiViewModel::getStatuses, mAuthToken);
                } else {
                    showErrorToast();
                }
            }
        });
    }


    private <T> void loadDataRecursive(final int currentPage, int nbPages, Function2<String, Integer, LiveData<T>> apiCall, String authToken) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBackgroundTaskInfoProgress.setText("Chargement de la page n° " + currentPage + " sur " + nbPages);
            }
        });
        apiCall.invoke(authToken, currentPage).observe(this, new Observer<T>() {
            @Override
            public void onChanged(T data) {
                if (data != null) {
                    int nbPages = 0;
                    if (data instanceof CoastersResponseDto) {
                        CoastersResponseDto coasters = (CoastersResponseDto) data;
                        nbPages = extractPageNumber(coasters.getViewDto().getLastPage());
                        // Insert coasters into database
                        //mDatabaseViewModel.insertCoasters(coasters);
                    } else if (data instanceof ImagesResponseDto) {
                        ImagesResponseDto images = (ImagesResponseDto) data;
                        nbPages = extractPageNumber(images.getViewDto().getLastPage());
                        // Insert images into database
                        //mDatabaseViewModel.insertImages(images);
                    } else if (data instanceof  ParksResponseDto){
                        ParksResponseDto parks = (ParksResponseDto) data;
                        nbPages = extractPageNumber(parks.getViewDto().getLastPage());
                    }

                    if (currentPage < nbPages) {
                        // Load next page recursively
                        loadDataRecursive(currentPage + 1, nbPages, apiCall, authToken);
                    } else {
                        if (data instanceof CoastersResponseDto){
                            loadImageUrls();
                        } else if (data instanceof ImagesResponseDto) {
                            loadParks();
                        } else if (data instanceof  ParksResponseDto){
                            loadStatuses();
                        } else {
                            launchMainActivity();
                        }
                    }
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

    private int extractPageNumber(String lastPage) {
        Pattern pattern = Pattern.compile("page=(\\d+)");
        Matcher matcher = pattern.matcher(lastPage);
        if (matcher.find()){
            String numberStr = matcher.group(1);
            return Integer.parseInt(numberStr);
        } else {
            return 0;
        }
    }
}