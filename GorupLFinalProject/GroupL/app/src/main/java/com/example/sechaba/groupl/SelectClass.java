package com.example.sechaba.groupl;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.sechaba.groupl.ClassListLearners.Class_List_Learn;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class SelectClass extends AppCompatActivity {
    Button Viewclass;
    Spinner s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("CLASSES FOR LEARNERS");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        s = (Spinner) findViewById(R.id.sp_classes);
        Viewclass = (Button) findViewById(R.id.classA);

    }

    public void Classing(View view) {
        Intent intent = new Intent(getApplication(), Class_List_Learn.class);
        intent.putExtra("Classname", (String) s.getSelectedItem());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SelectClass.this.finish();
    }


}
