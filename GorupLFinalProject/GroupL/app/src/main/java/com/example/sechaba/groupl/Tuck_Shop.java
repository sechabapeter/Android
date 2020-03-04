package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.sechaba.groupl.Adapter.TuckShopAdapter;
import com.example.sechaba.groupl.ClassListLearners.Class_List_Learn;
import com.example.sechaba.groupl.ClassListLearners.Class_TuckShop;
import com.example.sechaba.groupl.Classes.LearnerData;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Tuck_Shop extends AppCompatActivity {

    AlertDialog progressDialog;
    Spinner s;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuck__shop);
        s = (Spinner) findViewById(R.id.sp_classesTuck);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Tuck Shop");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Tuck_Shop.this.finish();
    }

    public void Classing(View view) {
        Intent intent = new Intent(getApplication(), Class_TuckShop.class);
        intent.putExtra("ClassnameT", (String) s.getSelectedItem());

        startActivity(intent);
    }
}
