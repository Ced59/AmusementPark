package com.caudron.amusementpark.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.panwrona.android.flags.FlagsImageView;

import com.caudron.amusementpark.R;
import com.caudron.amusementpark.models.entities.Country;

import java.util.List;

public class CountrySpinnerAdapter extends ArrayAdapter<Country> {

    private LayoutInflater inflater;
    private List<Country> countries;

    public CountrySpinnerAdapter(Context context, int resource, List<Country> countries) {
        super(context, resource, countries);
        this.inflater = LayoutInflater.from(context);
        this.countries = countries;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_item, parent, false);
        }
        Country country = countries.get(position);
        ImageView flagView = view.findViewById(R.id.spinner_icon);
        TextView nameView = view.findViewById(R.id.spinner_text);
        flagView.setImageResource(Flags.getFlag(country.getIsoCode()));
        nameView.setText(country.getName());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_dropdown_item, parent, false);
        }
        Country country = countries.get(position);
        ImageView flagView = view.findViewById(R.id.spinner_icon);
        TextView nameView = view.findViewById(R.id.spinner_text);
        flagView.setImageResource(Flags.getFlag(country.getIsoCode()));
        nameView.setText(country.getName());
        return view;
    }
}
