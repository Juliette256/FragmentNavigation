package com.example.navigation.ui.gallery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.navigation.DatabaseAdapter;
import com.example.navigation.R;
import com.example.navigation.User;
import com.example.navigation.UserAdapter;
import com.example.navigation.ui.slideshow.SlideshowFragment;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    ArrayAdapter<User>arrayAdapter;
    DatabaseAdapter databaseAdapter;
    ListView listView;
    SimpleCursorAdapter simpleCursorAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        root.setBackgroundColor(Color.WHITE);
        listView=root.findViewById(R.id.user_list) ;
       final ArrayList<User>list = loadData();
       listView.setAdapter(new UserAdapter(getContext(),list));
        return root;
    }

      public ArrayList<User> loadData(){
        databaseAdapter = new DatabaseAdapter(getActivity());
//        arrayAdapter= new ArrayAdapter<User>(getActivity(), R.layout.rowlayout, R.id.name, databaseAdapter.LoadUsers());
//        arrayAdapter.notifyDataSetChanged();
//        listView.setAdapter(arrayAdapter);
          ArrayList<User>dataList = new ArrayList<User>();
          dataList=databaseAdapter.LoadUsers();
          return  dataList;
      }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                 builder.setTitle("EDIT MEMBER");
                 builder.setIcon(R.drawable.ex_mark);

                 builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         databaseAdapter=new DatabaseAdapter(getContext());
                         databaseAdapter.delete((int) id);
                         Toast.makeText(getContext(), "Member had been deleted", Toast.LENGTH_LONG).show();
                     }
                     });

                 builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {


                         Cursor cursor= (Cursor) simpleCursorAdapter.getItem(position);
                         String name=cursor.getString(1);
                         String location=cursor.getString(2);
                         String designation=cursor.getString(3);


                         FragmentTransaction transaction=getFragmentManager().beginTransaction();
                         SlideshowFragment mFragment=new SlideshowFragment();

                         Bundle bundle=new Bundle();
                         bundle.putString("name",name);
                         bundle.putString("location",location);
                         bundle.putString("designation",designation);
                         mFragment.setArguments(bundle);
                         transaction.detach(mFragment).attach(mFragment);
                         transaction.replace(R.id.content, mFragment);
                         transaction.commit();

                     }
                 });

                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         dialog.cancel();
//                         FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                         GalleryFragment mFragment=new GalleryFragment();
//                         transaction.replace(R.id.content, mFragment);
//                         transaction.commit();

                     }});

                 builder.show();


                 return false;
             }

         });




    }
     }






