package com.caudron.amusementpark.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caudron.amusementpark.R;

import java.util.List;

public class CountrySpinnerAdapter extends ArrayAdapter<String> {
    private final List<String> countryNames;
    private final List<String> countryCodes;
    private final Context context;

    public CountrySpinnerAdapter(Context context, List<String> countryNames, List<String> countryCodes) {
        super(context, R.layout.spinner_country_item, countryNames);
        this.context = context;
        this.countryNames = countryNames;
        this.countryCodes = countryCodes;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
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
