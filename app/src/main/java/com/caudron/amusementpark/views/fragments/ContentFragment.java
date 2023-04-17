package com.caudron.amusementpark.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.caudron.amusementpark.R;

public class ContentFragment extends Fragment {
    private String mTitle;

    public ContentFragment(String title) {
        mTitle = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(mTitle);

        return view;
    }
}
