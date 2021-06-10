package com.example.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;

import com.example.navigation.ui.gallery.GalleryFragment;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    private final List<User> activity;
    List<User> userList;
    User user;
    int id;
     Context Context;
    ArrayList<User> listView;


    public UserAdapter(android.content.Context activity, ArrayList<User> list) {
     this.activity = (List<User>)activity;
     listView=list;
    }

    public void deleteUser() {
        User p=userList.get(id);
        int id=p.getId();

        DatabaseAdapter db=new DatabaseAdapter(Context);
        if(db.delete(id)) {
            userList.remove(id);
        }else { }
        this.notifyDataSetChanged();
    }

    public void addAll(ArrayList<User>userList){
        this.userList=userList;
        notifyDataSetChanged();
    }
    public void clear(){
        this.userList.clear();
        notifyDataSetChanged();
    }

    public void UpdateListData(ArrayList<User>userList) {
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    public String getSelectedName() {
        return user.getName();
    }

    public String getSelectedLocation() {
        return user.getLocation();
    }

    public String getSelectedAddress() {
        return user.getAddress();
    }
}

