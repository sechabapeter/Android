package com.example.sechaba.groupl;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.sechaba.groupl.ClassListLearners.Class_List_Learn;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Report_Learner extends AppCompatActivity {
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report__learner);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Learners Report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        s = (Spinner) findViewById(R.id.sp_classes);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Report_Learner.this.finish();
    }

    public void Classingreport(View view) {
        Intent intent = new Intent(getApplication(), Learner_Report_List.class);
        intent.putExtra("ClassnameR", (String) s.getSelectedItem());
        startActivity(intent);
    }
}
