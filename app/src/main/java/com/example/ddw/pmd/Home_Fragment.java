package com.example.ddw.pmd;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Home_Fragment extends Fragment {

    View view;
    private DBAdapter db;
    SharedPreferences pref;
    int currUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_layout, container, false);
        pref = getActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        currUser = pref.getInt("currUser", -1);
        getActivity().setTitle("Profile");
        db = new DBAdapter(this.getActivity());
        setUserName();
        return view;

    }

    public void setUserName(){
        TextView txtName = (TextView)view.findViewById(R.id.txtName);

        Cursor c;
        db.open();
        c = db.getUser(currUser);
        if(c.moveToFirst()) {
            txtName.setText(c.getString(3) + " " + c.getString(4));
        }

        db.close();
    }
}
