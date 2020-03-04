package com.example.sechaba.groupl;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.example.sechaba.groupl.Classes.CircleTransformation;
import com.squareup.picasso.Picasso;

import java.net.URL;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Homescreen extends AppCompatActivity {
    Button bclas, Tuckshop, Reports, Admin;
    ImageView imageView;
    String Master;
    TextView textView;
    String role, email;
    ViewPager pager;
    URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        try {
            email = Backendless.UserService.CurrentUser().getProperty("email").toString();
            role = Backendless.UserService.CurrentUser().getProperty("role").toString();
        } catch (Exception ex) {

        }
        ActionBar actionBar = getSupportActionBar();
        imageView = (ImageView) findViewById(R.id.imageView2);
        textView = (TextView) findViewById(R.id.emailuser);
        textView.setText(email);
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Welcome");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        bclas = (Button) findViewById(R.id.btn_class);
        Tuckshop = (Button) findViewById(R.id.btn_tuckshop);
        Reports = (Button) findViewById(R.id.btn_report);
        Admin = (Button) findViewById(R.id.btn_admin_master_menu);
        pictur();
        if (role.equals("Master") || role.equals("Admin")) {
            Tuckshop.setVisibility(View.GONE);
        } else if (role.equals("Default")) {
            Admin.setVisibility(View.GONE);
        }

    }

    public void pictur() {
        String pictures = Backendless.UserService.CurrentUser().getProperty("photoLocation").toString();
        String url = "https://api.backendless.com/F6C15024-B723-6512-FF01-73A1E7B9AD00/BBBBAE79-FB50-7B3C-FF3D-76922F472300/files/Photo/"
                + pictures;
        Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.mipmap.me3)
                .transform(new CircleTransformation())
                .into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_homescreen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void Button2(View view) {


        switch (view.getId()) {
            case R.id.btn_class:

                startActivity(new Intent(this, SelectClass.class));
                break;
            case R.id.btn_tuckshop:

                startActivity(new Intent(this, Tuck_Shop.class));
                break;
            case R.id.btn_report:
                startActivity(new Intent(this, Reports.class));
                break;
            case R.id.btn_admin_master_menu:

                startActivity(new Intent(this, Admin_Master_Menu.class));
                break;
        }//end switch


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Homescreen.this, MainActivity.class));
        Homescreen.this.finish();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }

}
