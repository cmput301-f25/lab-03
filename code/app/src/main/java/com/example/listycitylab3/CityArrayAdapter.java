package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// We extend ArrayAdapter Class so that we can override getView method and display a custom view
public class CityArrayAdapter extends ArrayAdapter<City> {
    //instantiate the ArrayAdapter class with context? and ArrayList of cities by calling super
    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }
    @NonNull
    @Override
    //instantiate getView with position of city in array, convertView? and parent?
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //make a view variable
        View view;

        //if there is no convert view then inflate content.xml to match the edges of the parent screen
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
        }
        //if convertView exists then just reuse it for efficiency
        else {
            view = convertView;
        }

        //get the city object at the current position
        City city = getItem(position);
        //get the TextView by id of the TextViews in content.xml
        TextView cityName = view.findViewById(R.id.city_text);
        TextView provinceName = view.findViewById(R.id.province_text);

        //set the names of the city and province in the TextViews in content.xml based on the city in the current position of the array
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());

        return view;
    }
}
