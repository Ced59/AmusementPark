package com.caudron.amusementpark.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;


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
import com.google.android.gms.maps.model.Marker;
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
    private Toolbar toolbar;
    private List<Park> mParkList;
    private List<Park> mFilteredParkList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateParkListWithSearchQuery(newText);
                return true;
            }
        });
    }

    @SuppressLint("PotentialBehaviorOverride")
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTag() != null) {
                    String markTag = marker.getTag().toString();
                    int parkId = Integer.parseInt(markTag);
                    mFilteredParkList = findParkById(parkId, mParkList);
                    if (mFilteredParkList.size() > 0) {
                        parkListFragment.updateParkList(mFilteredParkList);
                    }
                }

                marker.showInfoWindow();
                return true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                boolean markerClicked = false;
                if (mFilteredParkList != null){
                    for (Park park : mFilteredParkList) {
                        if (park.getLatitude() == latLng.latitude && park.getLongitude() == latLng.longitude) {
                            markerClicked = true;
                            break;
                        }
                    }
                }
                if (!markerClicked) {
                    parkListFragment.updateParkList(mParkList);
                }
            }
        });
    }

    private void loadParks() {
        mDatabaseViewModel.getAllParks(getApplicationContext()).observe(this, new Observer<List<Park>>() {
            @Override
            public void onChanged(List<Park> parks) {

                mParkList = parks;

                if (mMap != null) {
                    mMap.clear();

                    for (Park park : parks) {
                        LatLng parkLocation = new LatLng(park.getLatitude(), park.getLongitude());
                        Marker marker = mMap.addMarker(new MarkerOptions().position(parkLocation).title(park.getName()));
                        marker.setTag(park.getId());
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

    private List<Park> findParkById(int parkId, List<Park> parks) {
        List<Park> filteredParkList = new ArrayList<>();
        for (Park park : parks) {
            if (park.getId() == parkId) {
                filteredParkList.add(park);
            }
        }
        return filteredParkList;
    }

    private void updateParkListWithSearchQuery(String query) {
        if (query.isEmpty()) {
            parkListFragment.updateParkList(mParkList);
        } else {
            List<Park> filteredParkList = new ArrayList<>();
            for (Park park : mParkList) {
                if (park.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredParkList.add(park);
                }
            }
            parkListFragment.updateParkList(filteredParkList);
        }
    }
}
