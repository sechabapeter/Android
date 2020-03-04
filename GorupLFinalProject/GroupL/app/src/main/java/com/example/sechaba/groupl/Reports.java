package com.example.sechaba.groupl;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.example.sechaba.groupl.Classes.CircleTransformation;
import com.squareup.picasso.Picasso;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Reports extends AppCompatActivity {
    Button RMP, RL;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        imageView = (ImageView) findViewById(R.id.imageView4);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Reports");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        RMP = (Button) findViewById(R.id.rmp);
        RL = (Button) findViewById(R.id.rl);
        pictur();
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

    public void ButtonRL(View view) {


        switch (view.getId()) {
            case R.id.rmp:
                startActivity(new Intent(this, Maintenance_Report.class));
                break;
            case R.id.rl:
                startActivity(new Intent(this, Report_Learner.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Reports.this.finish();
    }
}