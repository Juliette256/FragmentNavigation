package com.example.navigation.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.navigation.DatabaseAdapter;
import com.example.navigation.R;
import com.example.navigation.User;
import com.example.navigation.ui.gallery.GalleryFragment;

public class SlideshowFragment extends Fragment {
    SimpleCursorAdapter simpleCursorAdapter;
    DatabaseAdapter databaseAdapter;
  EditText et_name,et_location,et_designation;
  Button Update,Delete;
  long id;
ListView lv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        root.setBackgroundColor(Color.WHITE);
          lv=root.findViewById(R.id.user_list);
        et_name = root.findViewById(R.id.txtName);
        et_location = root.findViewById(R.id.txtLocation);
        et_designation = root.findViewById(R.id.txtDesignation);

        Update = root.findViewById(R.id.btnUpdate);
//        Delete = root.findViewById(R.id.btnDelete);

        Bundle bundle=getArguments();
        et_name.setText(String.valueOf(bundle.getString("name")));
        et_location.setText(String.valueOf(bundle.getString("location")));
        et_designation.setText(String.valueOf(bundle.getString("designation")));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updating();
    }


    public void updating(){
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String New_name = et_name.getText().toString().trim();
                String New_location = et_location.getText().toString().trim();
                String New_designation = et_designation.getText().toString().trim();

                databaseAdapter=new DatabaseAdapter(getContext());

                databaseAdapter.UpdateUserDetails((int) id, New_name,New_location,New_designation);
                Toast.makeText(getContext(), "Details have been Updated", Toast.LENGTH_SHORT).show();

                et_name.setText("");
                et_location.setText("");
                et_designation.setText("");

                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                GalleryFragment mFragment=new GalleryFragment();
                transaction.detach(mFragment).attach(mFragment);
                transaction.replace(R.id.content, mFragment);
                transaction.commit();

            }});

//        Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Delete Alert");
//                builder.setIcon(R.drawable.ex_mark);
//                builder.setMessage("Are you sure about deleting the member?");
//
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Cursor cursor = null;
//
//                        databaseAdapter=new DatabaseAdapter(getContext());
//                        databaseAdapter.delete((int) id);
//
//
//                        et_name.setText("");
//                        et_location.setText("");
//                        et_designation.setText("");
//                        Toast.makeText(getContext(), "Member had been deleted", Toast.LENGTH_LONG).show();
//
//                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                        GalleryFragment mFragment=new GalleryFragment();
//                        transaction.detach(mFragment).attach(mFragment);
//                        transaction.replace(R.id.content, mFragment);
//                        transaction.commit();
//
//
//                    }});
//
//                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                        FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                        GalleryFragment mFragment=new GalleryFragment();
//                        transaction.replace(R.id.content, mFragment);
//                        transaction.commit();
//
//                    }});
//
//                builder.show();
//
////                GalleryFragment galleryFragment=new GalleryFragment();
////                getActivity().getSupportFragmentManager().beginTransaction()
////                        .replace(R.id.nav_slideshow, galleryFragment, "findThisListFragment")
////                        .addToBackStack(null)
////                        .commit();
//
//            }});
//    }

}}


