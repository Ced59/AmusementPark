package com.caudron.amusementpark.views.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.dtos.BaseResponseDto;
import com.caudron.amusementpark.models.dtos.CoastersResponseDto;
import com.caudron.amusementpark.models.dtos.ImagesResponseDto;
import com.caudron.amusementpark.models.dtos.ParksResponseDto;
import com.caudron.amusementpark.models.dtos.StatusesResponseDto;
import com.caudron.amusementpark.viewmodels.api_view_model.ApiViewModel;
import com.caudron.amusementpark.viewmodels.api_view_model.ApiViewModelFactory;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;
import com.caudron.amusementpark.views.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.jvm.functions.Function2;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mLoaderIcon;
    private TextView mBackgroundTaskInfoType;
    private TextView mBackgroundTaskInfoProgress;

    private ApiViewModel mApiViewModel;
    private DatabaseViewModel mDatabaseViewModel;

    private final String mAuthToken = "a44c7304-5658-4434-b8ca-aa24d0c07845";

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
        mBackgroundTaskInfoType.setText(R.string.loading_coasters);
        mApiViewModel.getCoasters(mAuthToken, 1).observe(this, new Observer<CoastersResponseDto>() {
            @Override
            public void onChanged(CoastersResponseDto coasters) {
                if (coasters != null && coasters.getCoasterDtos() != null) {
                    int nbPages = 1;
                    if (coasters.getViewDto() != null) {
                        nbPages = extractPageNumber(coasters.getViewDto().getLastPage());
                    }
                    loadDataRecursive(1, nbPages, mApiViewModel::getCoasters, mAuthToken);
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadImageUrls() {
        mBackgroundTaskInfoType.setText(R.string.loading_images);
        mApiViewModel.getImageUrls(mAuthToken, 1).observe(this, new Observer<ImagesResponseDto>() {
            @Override
            public void onChanged(ImagesResponseDto images) {
                if (images != null && images.getImageDtos() != null) {
                    int nbPages = 1;
                    if (images.getViewDto() != null) {
                        nbPages = extractPageNumber(images.getViewDto().getLastPage());
                    }
                    loadDataRecursive(1, nbPages, mApiViewModel::getImageUrls, mAuthToken);
                } else {
                    showErrorToast();
                }
            }
        });
    }


    private void loadParks() {
        mBackgroundTaskInfoType.setText(R.string.loading_parks);
        mApiViewModel.getParks(mAuthToken, 1).observe(this, new Observer<ParksResponseDto>() {
            @Override
            public void onChanged(ParksResponseDto parks) {
                if (parks != null && parks.getParks() != null) {
                    int nbPages = 1;
                    if (parks.getViewDto() != null) {
                        nbPages = extractPageNumber(parks.getViewDto().getLastPage());
                    }
                    loadDataRecursive(1, nbPages, mApiViewModel::getParks, mAuthToken);
                } else {
                    showErrorToast();
                }
            }
        });
    }

    private void loadStatuses() {
        mBackgroundTaskInfoType.setText(R.string.loading_status);
        mApiViewModel.getStatuses(mAuthToken, 1).observe(this, new Observer<StatusesResponseDto>() {
            @Override
            public void onChanged(StatusesResponseDto statuses) {
                if (statuses != null && statuses.getStatuses() != null) {
                    int nbPages = 1;
                    if (statuses.getViewDto() != null){
                        nbPages = extractPageNumber(statuses.getViewDto().getLastPage());
                    }
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
                mBackgroundTaskInfoProgress.setText(getString(R.string.loading_progress, currentPage, nbPages));
            }
        });
        apiCall.invoke(authToken, currentPage).observe(this, new Observer<T>() {
            @Override
            public void onChanged(T data) {
                if (data != null) {
                    int nbPages = 1;
                    switch (data.getClass().getSimpleName()) {
                        case "CoastersResponseDto":
                            CoastersResponseDto coasters = (CoastersResponseDto) data;
                            nbPages = getNbPages(coasters);
                            // Insert coasters into database
                            ExecutorService executorServiceCoasters = Executors.newSingleThreadExecutor();
                            executorServiceCoasters.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDatabaseViewModel.insertCoasters(coasters, getApplicationContext());
                                }
                            });
                            executorServiceCoasters.shutdown();
                            break;
                        case "ImagesResponseDto":
                            ImagesResponseDto images = (ImagesResponseDto) data;
                            nbPages = getNbPages(images);
                            // Insert images into database
                            ExecutorService executorServiceImages = Executors.newSingleThreadExecutor();
                            executorServiceImages.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDatabaseViewModel.insertImages(images, getApplicationContext());
                                }
                            });
                            executorServiceImages.shutdown();

                            break;
                        case "ParksResponseDto":
                            ParksResponseDto parks = (ParksResponseDto) data;
                            nbPages = getNbPages(parks);
                            ExecutorService executorServiceParks = Executors.newSingleThreadExecutor();
                            executorServiceParks.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDatabaseViewModel.insertParks(parks, getApplicationContext());
                                }
                            });
                            executorServiceParks.shutdown();
                            break;
                        case "StatusesResponseDto":
                            StatusesResponseDto statuses = (StatusesResponseDto) data;
                            nbPages = getNbPages(statuses);
                            ExecutorService executorServiceStatuses = Executors.newSingleThreadExecutor();
                            executorServiceStatuses.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDatabaseViewModel.insertStatuses(statuses, getApplicationContext());
                                }
                            });
                            executorServiceStatuses.shutdown();
                            break;
                        default:
                            break;
                    }

                    if (currentPage < nbPages) {
                        // Load next page recursively
                        loadDataRecursive(currentPage + 1, nbPages, apiCall, authToken);
                    } else {
                        switch (data.getClass().getSimpleName()) {
                            case "CoastersResponseDto":
                                loadImageUrls();
                                break;
                            case "ImagesResponseDto":
                                loadParks();
                                break;
                            case "ParksResponseDto":
                                loadStatuses();
                                break;
                            default:
                                launchMainActivity();
                                break;
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

    private int getNbPages(BaseResponseDto dataResponseDto) {
        if (dataResponseDto.getViewDto() != null && dataResponseDto.getViewDto().getLastPage() != null) {
            return extractPageNumber(dataResponseDto.getViewDto().getLastPage());
        }
        else {
            return 1;
        }
    }
}