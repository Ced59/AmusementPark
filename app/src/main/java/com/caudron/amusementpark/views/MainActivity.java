package com.caudron.amusementpark.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

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
import com.caudron.amusementpark.utils.UtilsKeyboard;
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
    private static final int AUTO_COMPLETE_POPUP_MAX_HEIGHT = 700;

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
    private ArrayAdapter<String> autoCompleteAdapter;
    private PopupWindow autoCompletePopup;
    private List<String> parkNameList;
    private ListView autoCompleteListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.focus_dummy).requestFocus();

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

        parkNameList = new ArrayList<>();
        autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, parkNameList);
        autoCompletePopup = new PopupWindow(this);
        autoCompleteListView = new ListView(this);
        autoCompleteListView.setAdapter(autoCompleteAdapter);

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Park> filteredParks = findParksByName(query, mParkList);
                if (filteredParks.size() == 1) {
                    centerMapOnPark(filteredParks.get(0));
                }
                UtilsKeyboard.hideVirtualKeyboard(MainActivity.this);
                autoCompletePopup.dismiss();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateParkListWithSearchQuery(newText);
                if (!newText.isEmpty()) {
                    showAutoCompletePopup(newText);
                } else {
                    autoCompletePopup.dismiss();
                }
                return true;
            }
        });

        autoCompleteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setQuery(parkNameList.get(position), true);
                autoCompletePopup.dismiss();
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    autoCompletePopup.dismiss();
                }
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

    private void showAutoCompletePopup(String query) {
        List<String> filteredParkNames = new ArrayList<>();
        for (Park park : mParkList) {
            if (park.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredParkNames.add(park.getName());
            }
        }
        parkNameList.clear();
        parkNameList.addAll(filteredParkNames);
        autoCompleteAdapter.notifyDataSetChanged();

        if (!autoCompletePopup.isShowing() && !filteredParkNames.isEmpty()) {
            autoCompletePopup.setWidth(searchView.getWidth());

            int totalItemsHeight = 0;
            for (int i = 0; i < autoCompleteAdapter.getCount(); i++) {
                View listItem = autoCompleteAdapter.getView(i, null, autoCompleteListView);
                listItem.measure(0, 0);
                totalItemsHeight += listItem.getMeasuredHeight();
            }

            // Limiter la hauteur de la PopupWindow
            int popupHeight = Math.min(totalItemsHeight, AUTO_COMPLETE_POPUP_MAX_HEIGHT);
            autoCompletePopup.setHeight(popupHeight);

            autoCompletePopup.setContentView(autoCompleteListView);
            autoCompletePopup.showAsDropDown(searchView);
        } else if (filteredParkNames.isEmpty()) {
            autoCompletePopup.dismiss();
        }
    }

    private void centerMapOnPark(Park park) {
        if (park != null && mMap != null) {
            LatLng parkLocation = new LatLng(park.getLatitude(), park.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(parkLocation, 16));
        }
    }

    private List<Park> findParksByName(String parkName, List<Park> parks) {
        List<Park> filteredParks = new ArrayList<>();
        for (Park park : parks) {
            if (park.getName().equalsIgnoreCase(parkName)) {
                filteredParks.add(park);
            }
        }
        return filteredParks;
    }


}
