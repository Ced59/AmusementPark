package com.caudron.amusementpark.views;

import androidx.appcompat.app.AppCompatActivity;
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
                final int countCoasters = mDatabaseViewModel.getCountCoasters(getApplicationContext());
                final int countParks = mDatabaseViewModel.getCountParks(getApplicationContext());
                final int countImages = mDatabaseViewModel.getCountImages(getApplicationContext());
                final int countCountries = mDatabaseViewModel.getCountCountries(getApplicationContext());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textViewCountCoaster = findViewById(R.id.coaster_count_textview);
                        textViewCountCoaster.setText("Nombre de coasters dans la db: " + countCoasters);

                        TextView textViewCountParks = findViewById(R.id.park_count_textview);
                        textViewCountParks.setText("Nombre de parcs dans la db: " + countParks);

                        TextView textViewCountImages = findViewById(R.id.images_count_textview);
                        textViewCountImages.setText("Nombre d'images dans la db: " + countImages);

                        TextView textViewCountCountries = findViewById(R.id.countries_count_textview);
                        textViewCountCountries.setText("Nombre de pays dans la db : " + countCountries);
                    }
                });
            }
        }).start();
    }
}
