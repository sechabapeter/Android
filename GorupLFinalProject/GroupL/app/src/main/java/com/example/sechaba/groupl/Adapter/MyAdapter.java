package com.example.sechaba.groupl.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sechaba.groupl.Fragments.FirstFragment;
import com.example.sechaba.groupl.Fragments.SecondFragment;

import static com.example.sechaba.groupl.Fragments.View_Pager_Fragment.int_items;

/**
 * Created by sechaba on 8/8/2017.
 */
/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
*
* */
public class MyAdapter extends FragmentPagerAdapter {

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FirstFragment();
            case 1:
                return new SecondFragment();

        }
        return null;
    }

    @Override
    public int getCount() {


        return int_items;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ADMIN USERS";
            case 1:
                return "MASTER USERS";

        }
        return null;
    }


}
