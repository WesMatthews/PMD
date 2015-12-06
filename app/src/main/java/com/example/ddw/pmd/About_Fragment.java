package com.example.ddw.pmd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Poncho on 12/5/2015.
 */
public class About_Fragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.about_layout, container, false);
        getActivity().setTitle("About");
        TextView txtAbout = (TextView) view.findViewById(R.id.txtAbout);
        txtAbout.setText(R.string.about_text);
        return view;
    }
}
