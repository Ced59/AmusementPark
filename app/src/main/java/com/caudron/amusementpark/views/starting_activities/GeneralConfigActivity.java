package com.caudron.amusementpark.views.starting_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Country;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class GeneralConfigActivity extends AppCompatActivity {

    private List<Country> listCountry;
    private DatabaseViewModel dbViewModel;
    private Spinner countrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_config);

        dbViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        countrySpinner = findViewById(R.id.country_spinner);

        dbViewModel.getCountries(this).observe(GeneralConfigActivity.this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                listCountry = countries;

                List<String> countryNames = new ArrayList<>();
                countryNames.add("En fonction de votre géolocalisation");
                countryNames.add("Monde entier");
                for (Country country : listCountry) {
                    countryNames.add(country.getName());
                }

                ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(GeneralConfigActivity.this, android.R.layout.simple_spinner_dropdown_item, countryNames);
                countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                countrySpinner.setAdapter(countryAdapter);
            }
        });
    }
}