package com.example.navigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigation.DatabaseAdapter;
import com.example.navigation.R;
import com.example.navigation.ui.gallery.GalleryFragment;
import com.example.navigation.ui.slideshow.SlideshowFragment;

public class HomeFragment extends Fragment {
    EditText name, loc, desig;
    Button saveBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
            return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.txtName);
        loc = view.findViewById(R.id.txtLocation);
        desig = view.findViewById(R.id.txtDesignation);
        saveBtn = view. findViewById(R.id.btnSave);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString() + "\n";
                String location = loc.getText().toString();
                String designation = desig.getText().toString();

                DatabaseAdapter databaseAdapter = new DatabaseAdapter(getContext());
                databaseAdapter.insertUserDetails(username, location, designation);

                Toast.makeText(getContext(), "Details Inserted Successfully", Toast.LENGTH_LONG).show();
                name.setText("");
                loc.setText("");
                desig.setText("");

            }});

    }
}