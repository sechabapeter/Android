package com.example.sechaba.groupl;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.sechaba.groupl.Fragments.FirstFragment;
import com.example.sechaba.groupl.Fragments.View_Pager_Fragment;


/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Admin_Master_Menu extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener {
    FragmentManager FM;
    FragmentTransaction FT;

    private SectionsPagerAdapter sectionsPagerAdapter;

    public class SectionsPagerAdapter {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__master__menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Admin and Master Menu");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();


        FT.replace(R.id.containerVIEW, new View_Pager_Fragment()).commit();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_homescreen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Admin_Master_Menu.this.finish();
    }

    @Override
    public void onButtonClickedListener(View view) {
        switch (view.getId()) {
            case R.id.button2:
                startActivity(new Intent(this, List_of_Learners.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, MastereUsers.class));
                break;
        }
    }
}
