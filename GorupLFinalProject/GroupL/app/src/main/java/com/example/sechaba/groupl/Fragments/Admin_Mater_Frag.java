package com.example.sechaba.groupl.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
*                 : 214110508 SEKATJA SS
*                 :214109984MADYWABE Z
*  Technical programming II Horizontal Prototype
* */
import com.example.sechaba.groupl.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Admin_Mater_Frag extends Fragment {


    public Admin_Mater_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_welcome, container, false);
    }

}
