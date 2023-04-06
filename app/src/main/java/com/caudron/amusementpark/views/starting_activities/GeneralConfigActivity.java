package com.caudron.amusementpark.views.starting_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Country;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;
import com.caudron.amusementpark.views.adapters.CountrySpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GeneralConfigActivity extends AppCompatActivity {

    private List<Country> listCountry;
    private DatabaseViewModel dbViewModel;
    private Spinner countrySpinner;
    private boolean isActivityDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_config);

        dbViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        countrySpinner = findViewById(R.id.country_spinner);

        dbViewModel.getCountries(this).observe(GeneralConfigActivity.this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                if (isActivityDestroyed) {
                    return;
                }

                listCountry = countries;

                List<String> countryCodes = new ArrayList<>();
                for (Country country : listCountry) {
                    countryCodes.add(country.getCountryCode());
                }
                List<String> countryNames = new ArrayList<>();
                countryNames.add(getString(R.string.geolocation_fonction));
                countryNames.add(getString(R.string.all_world));

                for (String code : countryCodes) {
                    String nameResource = "country_" + code.toLowerCase();

                    try {
                        int resourceId = getResources().getIdentifier(nameResource, "string", getPackageName());
                        String resourceValue = getString(resourceId);
                        countryNames.add(resourceValue);
                    } catch (Resources.NotFoundException ignored) {

                    }
                }

                ArrayAdapter<String> countryAdapter = new CountrySpinnerAdapter(GeneralConfigActivity.this, countryNames, countryCodes);
                countrySpinner.setAdapter(countryAdapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityDestroyed = true;
    }

}