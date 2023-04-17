package com.caudron.amusementpark.views.adapters;

import static android.provider.Settings.System.getString;

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
import com.caudron.amusementpark.models.entities.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountrySpinnerAdapter extends ArrayAdapter<Country> {
    private final List<Country> countries;
    private final Context context;

    public CountrySpinnerAdapter(Context context, List<Country> countries) {
        super(context, R.layout.spinner_country_item, countries);
        this.context = context;

        this.countries = new ArrayList<>();

        Country countryGeoloc = new Country();
        countryGeoloc.setCountryCode("geoloc");
        countryGeoloc.setName(context.getString(R.string.geolocation_fonction));
        Country countryWholeWorld = new Country();
        countryWholeWorld.setCountryCode("world");
        countryWholeWorld.setName(context.getString(R.string.all_world));
        this.countries.add(countryGeoloc);
        this.countries.add(countryWholeWorld);

        for (Country country : countries) {
            this.countries.add(country);
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

        Country country = countries.get(position);
        String countryCode = country.getCountryCode();

        if (position == 0) {
            flagImageView.setImageResource(R.drawable.geoloc);
        } else if (position == 1) {
            flagImageView.setImageResource(R.drawable.world);
        } else {
            int flagId = context.getResources().getIdentifier("flag_" + countryCode.toLowerCase(), "drawable", context.getPackageName());
            flagImageView.setImageResource(flagId);
        }

        countryNameTextView.setText(country.getName());

        return view;
    }
}

