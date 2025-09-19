package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;



// A DialogFragment that lets the user edit an existing City object
public class EditFragment extends DialogFragment {

    // Callback interface so the host Activity can receive the edited City
    public interface EditCityDialogListener {
        void onCityEdited(int position, City updatedCity);
    }

    private EditCityDialogListener listener; // reference to the host Activity

    // Factory method to create a new instance of the EditFragment
    // Takes a City object and its position in the list, puts them in a Bundle
    public static EditFragment newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);  // City must implement Serializable
        args.putInt("pos", position);        // keep track of which item is being edited
        EditFragment f = new EditFragment();
        f.setArguments(args);                // attach arguments to the fragment
        return f;
    }

    // Called when the fragment is attached to its host Activity
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Ensure the host Activity implements the callback interface
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    // Create and return the actual dialog UI
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Retrieve the City object and its position from arguments
        City city = (City) requireArguments().getSerializable("city");
        int position = requireArguments().getInt("pos");

        // Inflate the custom layout for editing a city
        View view = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_edit_city, null);

        // Get references to the EditText fields
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill the text fields with the current city data (if provided)
        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        // Build and return the AlertDialog
        return new AlertDialog.Builder(requireContext())
                .setTitle("Edit City")      // dialog title
                .setView(view)              // set the custom layout
                .setPositiveButton("Confirm", (d, w) -> {
                    // When user clicks Confirm, grab the new values
                    String newName = editCityName.getText().toString().trim();
                    String newProv = editProvinceName.getText().toString().trim();

                    // Create a new City object with updated values
                    // (or you could directly mutate the existing City via setters)
                    City updated = new City(newName, newProv);

                    // Notify the Activity through the callback
                    listener.onCityEdited(position, updated);
                })
                .setNegativeButton("Cancel", null) // Dismiss if Cancel pressed
                .create();
    }
}

