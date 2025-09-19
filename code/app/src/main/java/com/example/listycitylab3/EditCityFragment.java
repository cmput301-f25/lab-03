package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    public static EditCityFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        args.putInt("position", position);
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = requireArguments();
        City city = (City) args.getSerializable("city");
        int position = args.getInt("position", -1);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City updated = new City(cityName, provinceName);
                    Bundle result = new Bundle();
                    result.putSerializable("city", updated);
                    result.putInt("position", position);
                    getParentFragmentManager().setFragmentResult("edit_city", result);
                })
                .create();
    }
}

