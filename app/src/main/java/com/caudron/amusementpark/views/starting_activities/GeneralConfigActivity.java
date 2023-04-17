package com.caudron.amusementpark.views.starting_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import com.caudron.amusementpark.views.MainActivity;
import com.caudron.amusementpark.views.adapters.CountrySpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GeneralConfigActivity extends AppCompatActivity {

    private List<Country> listCountry;
    private DatabaseViewModel dbViewModel;
    private Spinner countrySpinner;
    private SwitchCompat saveOfflineSwitch;
    private boolean isActivityDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_config);

        dbViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);
        countrySpinner = findViewById(R.id.country_spinner);
        saveOfflineSwitch = findViewById(R.id.save_offline_switch);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGeneralConfig();
                Intent intent = new Intent(GeneralConfigActivity.this, MainActivity.class);
                startActivity(intent);
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
                countryCodes.add("geoloc");
                countryCodes.add("world");
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

    private void saveGeneralConfig() {
        int selectedPosition = countrySpinner.getSelectedItemPosition();
        String selectedCountryCode;
        String selectedCountryName;

        if (selectedPosition == 0) {
            selectedCountryCode = "geoloc";
            selectedCountryName = "geoloc";
        } else if (selectedPosition == 1) {
            selectedCountryCode = "world";
            selectedCountryName = "world";
        } else {
            selectedCountryCode = listCountry.get(selectedPosition).getCountryCode();
            selectedCountryName = listCountry.get(selectedPosition).getName();
        }

        boolean saveDatasOffline = saveOfflineSwitch.isChecked();

        GeneralConfig generalConfig = new GeneralConfig();
        generalConfig.setMainPageCountryCode(selectedCountryCode);
        generalConfig.setMainPageCountryName(selectedCountryName);
        generalConfig.setMakeDatasOffline(saveDatasOffline);

        SharedPreferences preferences = UtilsSharedPreferences.getSharedPreferencesFile(this, "GeneralConfig");
        UtilsSharedPreferences.saveSharedPreferences(preferences, "GeneralConfig", generalConfig);
    }


}