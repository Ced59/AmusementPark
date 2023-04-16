package com.caudron.amusementpark.views.fragments;

import static android.app.PendingIntent.getActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Park;
import com.caudron.amusementpark.views.adapters.ParkListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ParkListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ParkListAdapter mAdapter;
    private List<Park> mParkList;
    private GoogleMap mMap;

    public ParkListFragment(List<Park> parkList, GoogleMap map) {
        mParkList = parkList;
        mMap = map;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_park_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_park_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ParkListAdapter(getActivity(), mParkList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ParkListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Park park) {
                LatLng parkLocation = new LatLng(park.getLatitude(), park.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(parkLocation, 15));
            }
        });


        return view;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void updateParkList(List<Park> parkList) {
        mParkList = parkList;
        mAdapter.updateParkList(mParkList);
    }

}

