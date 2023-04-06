package com.caudron.amusementpark.views.starting_activities;

import static com.caudron.amusementpark.keys.ApiKeys.API_CAPTAIN_COASTER_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.dtos.BaseResponseDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.jvm.functions.Function2;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mLoaderIcon;
    private TextView mBackgroundTaskInfoType;
    private TextView mBackgroundTaskInfoProgress;

    private ApiViewModel mApiViewModel;
    private DatabaseViewModel mDatabaseViewModel;

    private final String mAuthToken = API_CAPTAIN_COASTER_KEY;

    private final ExecutorService mTaskExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mLoaderIcon = findViewById(R.id.loaderIcon);
        mBackgroundTaskInfoType = findViewById(R.id.backgroundTaskInfoType);
        mBackgroundTaskInfoProgress = findViewById(R.id.backgroundTaskInfoProgress);

        mApiViewModel = new ViewModelProvider(this, new ApiViewModelFactory(getApplication(), mAuthToken)).get(ApiViewModel.class);
        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        loadParks();
    }

    private void loadParks() {
        mBackgroundTaskInfoType.setText(R.string.loading_parks);

        getApiTotalItems(mApiViewModel::getParks, totalItems -> {
            mDatabaseViewModel.getCountParks(getApplicationContext()).observe(this, count -> {
                if (count != null && count.equals(totalItems)){
                    loadCoasters();
                } else{
                    loadDataFromApi(mApiViewModel::getParks, data -> {
                        mTaskExecutor.execute(() -> {
                            mDatabaseViewModel.insertParks((ParksResponseDto) data, getApplicationContext());
                            runOnUiThread(this::loadCoasters);
                        });
                    }, ParksResponseDto.class);
                }
            });
        });
    }

    private void loadCoasters() {
        mBackgroundTaskInfoType.setText(R.string.loading_coasters);
        getApiTotalItems(mApiViewModel::getCoasters, totalItems -> {
            mDatabaseViewModel.getCountCoasters(getApplicationContext()).observe(this, count -> {
                if (count != null && count.equals(totalItems)){
                    loadImageUrls();
                } else{
                    loadDataFromApi(mApiViewModel::getCoasters, data -> {
                        mTaskExecutor.execute(() -> {
                            mDatabaseViewModel.insertCoasters((CoastersResponseDto) data, getApplicationContext());
                            runOnUiThread(this::loadImageUrls);
                        });
                    }, CoastersResponseDto.class);
                }
            });
        });
    }

    private void loadImageUrls() {
        mBackgroundTaskInfoType.setText(R.string.loading_images);
        getApiTotalItems(mApiViewModel::getImageUrls, totalItems -> {
            mDatabaseViewModel.getCountImages(getApplicationContext()).observe(this, count -> {
                if (count != null && count.equals(totalItems)){
                    loadStatuses();
                }else{
                    loadDataFromApi(mApiViewModel::getImageUrls, data -> {
                        mTaskExecutor.execute(() -> {
                            mDatabaseViewModel.insertImages((ImagesResponseDto) data, getApplicationContext());
                            runOnUiThread(this::loadStatuses);
                        });
                    }, ImagesResponseDto.class);
                }
            });
        });
    }

    private void loadStatuses() {
        mBackgroundTaskInfoType.setText(R.string.loading_status);
        getApiTotalItems(mApiViewModel::getStatuses, totalItems -> {
            mDatabaseViewModel.getCountStatuses(getApplicationContext()).observe(this, count -> {
                if (count != null && count.equals(totalItems)){
                    determineAndLaunchNextActivity();
                } else {
                    loadDataFromApi(mApiViewModel::getStatuses, data -> {
                        mTaskExecutor.execute(() -> {
                            mDatabaseViewModel.insertStatuses((StatusesResponseDto) data, getApplicationContext());
                            runOnUiThread(this::determineAndLaunchNextActivity);
                        });
                    }, StatusesResponseDto.class);
                }
            });
        });
    }


    private <T extends BaseResponseDto> void loadDataFromApi(Function2<String, Integer, LiveData<T>> apiCall, Consumer<T> onComplete, Class<T> clazz) {
        List<T> dataList = new ArrayList<>();
        loadPage(apiCall, 1, dataList, onComplete, clazz);
    }

    private <T extends BaseResponseDto> void loadPage(Function2<String, Integer, LiveData<T>> apiCall, int page, List<T> dataList, Consumer<T> onComplete, Class<T> clazz) {
        apiCall.invoke(mAuthToken, page).observe(this, new Observer<T>() {
            @Override
            public void onChanged(T data) {
                if (data != null) {
                    dataList.add(data);
                    int totalPages = getNbPages((BaseResponseDto) data);
                    if (page < totalPages) {
                        loadPage(apiCall, page + 1, dataList, onComplete, clazz);
                    } else {
                        T combinedData = mergeData(dataList, clazz);
                        onComplete.accept(combinedData);
                    }
                } else {
                    showErrorToast();
                }
            }
        });
    }


    private void determineAndLaunchNextActivity() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, GeneralConfigActivity.class);
            startActivity(intent);
            finish();
        }, 100); // 10000 ms = 10s
    }

    private void showErrorToast() {
        Toast.makeText(SplashScreenActivity.this, R.string.error_loading, Toast.LENGTH_LONG).show();
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

    private <T extends BaseResponseDto> T mergeData(List<T> dataList, Class<T> clazz) {
        if (dataList == null || dataList.isEmpty()) {
            return null;
        }

        T mergedData;
        try {
            mergedData = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        List<Object> mergedList = new ArrayList<>();

        for (T data : dataList) {
            if (data instanceof CoastersResponseDto) {
                mergedList.addAll(((CoastersResponseDto) data).getCoasterDtos());
            } else if (data instanceof ImagesResponseDto) {
                mergedList.addAll(((ImagesResponseDto) data).getImageDtos());
            } else if (data instanceof ParksResponseDto) {
                mergedList.addAll(((ParksResponseDto) data).getParks());
            } else if (data instanceof StatusesResponseDto) {
                mergedList.addAll(((StatusesResponseDto) data).getStatuses());
            }
        }

        if (mergedData instanceof CoastersResponseDto) {
            ((CoastersResponseDto) mergedData).setCoasterDtos((List<CoasterDto>) (List<?>) mergedList);
        } else if (mergedData instanceof ImagesResponseDto) {
            ((ImagesResponseDto) mergedData).setImageDtos((List<ImageDto>) (List<?>) mergedList);
        } else if (mergedData instanceof ParksResponseDto) {
            ((ParksResponseDto) mergedData).setParks((List<ParkDto>) (List<?>) mergedList);
        } else if (mergedData instanceof StatusesResponseDto) {
            ((StatusesResponseDto) mergedData).setStatuses((List<StatusDto>) (List<?>) mergedList);
        }

        return mergedData;
    }

    private <T extends BaseResponseDto> void getApiTotalItems(Function2<String, Integer, LiveData<T>> apiCall, Consumer<Integer> onComplete) {
        apiCall.invoke(mAuthToken, 1).observe(this, new Observer<T>() {
            @Override
            public void onChanged(T data) {
                if (data != null) {
                    onComplete.accept(data.getTotalItems());
                } else {
                    showErrorToast();
                }
            }
        });
    }

}

