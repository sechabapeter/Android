package com.example.sechaba.groupl.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sechaba.groupl.Adapter.MyAdapter;
import com.example.sechaba.groupl.R;

/**
 * A simple {@link Fragment} subclass.
 */

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
*                 : 214110508 SEKATJA SS
*                 :214109984MADYWABE Z
*  Technical programming II Horizontal Prototype
* */
public class View_Pager_Fragment extends Fragment {
    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;
    public  static  int int_items=2;
    Button bn;

    public View_Pager_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view__pager_,null);
        tabLayout = (TabLayout)v.findViewById(R.id.tabs);
        viewPager=(ViewPager)v.findViewById(R.id.viewpsger);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return v;
    }
}
