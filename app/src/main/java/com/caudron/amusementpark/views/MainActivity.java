package com.caudron.amusementpark.views;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Park;
import com.caudron.amusementpark.models.entities.general_preferences.GeneralConfig;
import com.caudron.amusementpark.utils.UtilsSharedPreferences;
import com.caudron.amusementpark.viewmodels.database_view_model.DatabaseViewModel;
import com.caudron.amusementpark.views.adapters.FragmentAdapter;
import com.caudron.amusementpark.views.fragments.ContentFragment;
import com.caudron.amusementpark.views.fragments.ParkListFragment;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.maps.android.SphericalUtil;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DatabaseViewModel mDatabaseViewModel;
    private GoogleMap mMap;
    private TabLayout tabs;
    private ViewPager2 viewPager;
    private ParkListFragment parkListFragment;
    private String countryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);

        mDatabaseViewModel = new ViewModelProvider(this).get(DatabaseViewModel.class);

        countryCode = getSelectedCountryCode();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Créer un adaptateur pour les fragments
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());

        // Ajouter des fragments pour chaque onglet
        parkListFragment = new ParkListFragment(new ArrayList<Park>(), mMap);
        adapter.addFragment(parkListFragment);
        adapter.addFragment(new ContentFragment("Onglet 2"));
        adapter.addFragment(new ContentFragment("Onglet 3"));
        adapter.addFragment(new ContentFragment("Onglet 4"));
        adapter.addFragment(new ContentFragment("Onglet 5"));
        adapter.addFragment(new ContentFragment("Onglet 6"));

        // Configurer le ViewPager2 avec l'adaptateur
        viewPager.setAdapter(adapter);

        // Configurer le TabLayout pour utiliser le ViewPager2
        new TabLayoutMediator(tabs, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Parcs");
                    } else {
                        tab.setText("Onglet " + (position + 1));
                    }
                }
        ).attach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        parkListFragment.setmMap(mMap);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                loadParks();
            }
        });

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void loadParks() {
        mDatabaseViewModel.getAllParks(getApplicationContext()).observe(this, new Observer<List<Park>>() {
            @Override
            public void onChanged(List<Park> parks) {
                if (mMap != null) {
                    mMap.clear();

                    for (Park park : parks) {
                        LatLng parkLocation = new LatLng(park.getLatitude(), park.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(parkLocation).title(park.getName()));
                    }

                    if (countryCode.equals("geoloc")) {
                        FusedLocationProviderClient locationClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                            locationClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                        LatLngBounds bounds = calculateBounds(currentLocation, 100000);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
                                    }
                                }
                            });
                        }
                    }
                    else if (countryCode.equals("world")){
                        LatLng worldCenter = new LatLng(0, 0);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(worldCenter, 0));
                    } else {
                        LatLng worldCenter = new LatLng(0, 0);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(worldCenter, 2));
                    }

                    if (parkListFragment != null) {
                        parkListFragment.updateParkList(parks);
                    }
                }
            }
        });
    }

    private LatLngBounds calculateBounds(LatLng center, double radius) {
        LatLng southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        LatLng northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        return new LatLngBounds(southwest, northeast);
    }

    private String getSelectedCountryCode() {
        SharedPreferences preferences = UtilsSharedPreferences.getSharedPreferencesFile(this, "GeneralConfig");
        GeneralConfig generalConfig = (GeneralConfig) UtilsSharedPreferences.getSharedPreferences(preferences, "GeneralConfig", GeneralConfig.class);
        return generalConfig.getMainPageCountryCode();
    }

    public GoogleMap getMap(){
        return mMap;
    }
}
