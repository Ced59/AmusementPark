package com.caudron.amusementpark.views.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.dtos.ApiResponse;
import com.caudron.amusementpark.viewmodels.api_view_model.ApiViewModel;
import com.caudron.amusementpark.viewmodels.api_view_model.callback.ApiCallback;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;
import com.caudron.amusementpark.viewmodels.database_view_model.callback.DatabaseCallback;
import com.caudron.amusementpark.views.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private ProgressBar mLoaderIcon;
    private TextView mBackgroundTaskInfo;

    private ApiViewModel mApiViewModel;
    private DatabaseViewModel mDatabaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mLoaderIcon = findViewById(R.id.loaderIcon);
        mBackgroundTaskInfo = findViewById(R.id.backgroundTaskInfo);

        mApiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        // Call API asynchronously
        mApiViewModel.makeApiCall(new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse response) {
                // Update background task info text
                mBackgroundTaskInfo.setText("Enregistrement des nouvelles données");

                // Save data to database asynchronously
                mDatabaseViewModel.saveDataToDatabase(response.getData(), new DatabaseCallback() {
                    @Override
                    public void onSuccess() {
                        // Launch main activity when both API call and database save are successful
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        // Handle database save error
                        // Show error message to user
                        Toast.makeText(SplashScreenActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        // Hide loader icon
                        mLoaderIcon.setVisibility(View.GONE);
                        // Update background task info text
                        mBackgroundTaskInfo.setText("Failed to save data to database");
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle API call error
                // Show error message to user
                Toast.makeText(SplashScreenActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                // Hide loader icon
                mLoaderIcon.setVisibility(View.GONE);
                // Update background task info text
                mBackgroundTaskInfo.setText("Failed to load data from API");
            }
        });

        // Update background task info text
        mBackgroundTaskInfo.setText("Chargement des nouvelles données");
    }
}