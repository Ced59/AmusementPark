package com.caudron.amusementpark.views.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.caudron.amusementpark.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountrySpinnerAdapter extends ArrayAdapter<String> {
    private final List<String> countryNames;
    private final List<String> countryCodes;
    private final Context context;


    public CountrySpinnerAdapter(Context context, List<String> countryNames, List<String> countryCodes) {
        super(context, R.layout.spinner_country_item, countryNames);
        this.context = context;

        List<Pair<String, String>> countryPairs = new ArrayList<>();
        for (int i = 2; i < countryNames.size(); i++) {
            countryPairs.add(new Pair<>(countryCodes.get(i - 2), countryNames.get(i)));
        }

        countryPairs.sort(Comparator.comparing(pair -> pair.second));

        this.countryNames = new ArrayList<>();
        this.countryCodes = new ArrayList<>();
        this.countryNames.add(countryNames.get(0));
        this.countryNames.add(countryNames.get(1));
        for (Pair<String, String> pair : countryPairs) {
            this.countryCodes.add(pair.first);
            this.countryNames.add(pair.second);
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.spinner_country_item, parent, false);

        ImageView flagImageView = view.findViewById(R.id.flag_image);
        TextView countryNameTextView = view.findViewById(R.id.country_name);

        if (position == 0) {
            flagImageView.setImageResource(R.drawable.geoloc);
            flagImageView.setVisibility(View.VISIBLE);
        }else if (position == 1) {
            flagImageView.setImageResource(R.drawable.world);
            flagImageView.setVisibility(View.VISIBLE);
        } else {
            String countryCode = countryCodes.get(position - 2);
            int flagId = context.getResources().getIdentifier("flag_" + countryCode.toLowerCase(), "drawable", context.getPackageName());
            flagImageView.setImageResource(flagId);
            flagImageView.setVisibility(View.VISIBLE);
        }

        countryNameTextView.setText(countryNames.get(position));

        return view;
    }
}
