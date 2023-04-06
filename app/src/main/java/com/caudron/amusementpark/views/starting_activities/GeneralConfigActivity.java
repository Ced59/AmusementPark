package com.caudron.amusementpark.views.starting_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Country;
import com.caudron.amusementpark.models.entities.general_preferences.GeneralConfig;
import com.caudron.amusementpark.utils.UtilsSharedPreferences;
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

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelectedCountryCode();
            }
        });

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

    private void saveSelectedCountryCode() {
        int selectedPosition = countrySpinner.getSelectedItemPosition();
        String selectedCountryCode;

        if (selectedPosition == 0) {
            selectedCountryCode = "geoloc";
        } else if (selectedPosition == 1) {
            selectedCountryCode = "world";
        } else {
            selectedCountryCode = listCountry.get(selectedPosition - 2).getCountryCode();
        }

        GeneralConfig generalConfig = new GeneralConfig();
        generalConfig.setMainPageCountryCode(selectedCountryCode);

        SharedPreferences preferences = UtilsSharedPreferences.getSharedPreferencesFile(this, "GeneralConfig");
        UtilsSharedPreferences.saveSharedPreferences(preferences, "GeneralConfig", generalConfig);
    }


}