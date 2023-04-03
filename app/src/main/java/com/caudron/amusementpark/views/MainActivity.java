package com.caudron.amusementpark.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;

public class MainActivity extends AppCompatActivity {

    private DatabaseViewModel mDatabaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                final LiveData<Integer> countCoasters = mDatabaseViewModel.getCountCoasters(getApplicationContext());
                final LiveData<Integer> countParks = mDatabaseViewModel.getCountParks(getApplicationContext());
                final LiveData<Integer> countImages = mDatabaseViewModel.getCountImages(getApplicationContext());
                final LiveData<Integer> countCountries = mDatabaseViewModel.getCountCountries(getApplicationContext());
                final LiveData<Integer> countMaterialType = mDatabaseViewModel.getCountMaterialType(getApplicationContext());
                final LiveData<Integer> countStatuses = mDatabaseViewModel.getCountStatuses(getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        countCoasters.observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer count) {
                                TextView textViewCountCoaster = findViewById(R.id.coaster_count_textview);
                                textViewCountCoaster.setText("Nombre de coasters dans la db: " + count);
                            }
                        });

                        countParks.observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer count) {
                                TextView textViewCountParks = findViewById(R.id.park_count_textview);
                                textViewCountParks.setText("Nombre de parcs dans la db: " + count);
                            }
                        });

                        countImages.observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer count) {
                                TextView textViewCountImages = findViewById(R.id.images_count_textview);
                                textViewCountImages.setText("Nombre d'images dans la db: " + count);
                            }
                        });


                        countCountries.observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer count) {
                                TextView textViewCountCountries = findViewById(R.id.countries_count_textview);
                                textViewCountCountries.setText("Nombre de pays dans la db : " + count);
                            }
                        });


                        countMaterialType.observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer count) {
                                TextView textViewCountMaterialType = findViewById(R.id.materialType_count_textview);
                                textViewCountMaterialType.setText("Nombre de MaterialType dans la db : " + count);
                            }
                        });

                        countStatuses.observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer count) {
                                TextView textViewCountStatuses = findViewById(R.id.statuses_count_textview);
                                textViewCountStatuses.setText("Nombre de Status dans la db : " + count);
                            }
                        });

                    }
                });
            }
        }).start();
    }
}
