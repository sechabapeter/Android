package com.example.sechaba.groupl;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sechaba.groupl.Adapter.MyAdapter;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class View_Activity_Frag extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view___frag);
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpsger);
        adapterViewPager = new MyAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Welcome");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View_Activity_Frag.this.finish();
    }
}
