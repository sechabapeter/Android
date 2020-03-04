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
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.sechaba.groupl.Adapter.Learner_adapter;
import com.example.sechaba.groupl.ClassListLearners.Class_List_Learn;
import com.example.sechaba.groupl.Classes.LearnerData;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */

public class Learner_Report_List extends AppCompatActivity {
    AlertDialog progressDialog;
    ArrayList<LearnerData> LData;
    ListView lvData;
    Learner_adapter editAdapter;
    String classname;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner__report__list);
        role = Backendless.UserService.CurrentUser().getProperty("role").toString();
        ActionBar actionBar = getSupportActionBar();
        classname = getIntent().getExtras().getString("ClassnameR");
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Learner report");
        actionBar.setDisplayUseLogoEnabled(true);
        validateConnection();
        actionBar.setDisplayShowHomeEnabled(true);
        lvData = (ListView) findViewById(R.id.classsLeranersR);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Learner_Report_List.this.finish();
    }

    public void validateConnection() {
        if (connectinAvailable()) {
            listViewer();
        } else {
            showCustomToast("No connection for ClASSES");
        }

    }

    public void listViewer() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<LearnerData>();
        String whereClause = String.format("classname = '%s'", classname);//Interpolator of strings
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(5).setOffset(0);
        queryBuilder.setSortBy("name");

        Backendless.Persistence.of(LearnerData.class).find(queryBuilder, new AsyncCallback<List<LearnerData>>() {
            @Override
            public void handleResponse(List<LearnerData> response) {

                LData.addAll(response);
                editAdapter = new Learner_adapter(getApplicationContext(), LData);
                progressDialog.dismiss();
                lvData.setAdapter(editAdapter);
                sendData();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast("error:" + fault.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    public void showCustomToast(CharSequence message) {
        toastView = getLayoutInflater().inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast));
        TOSTtEXT = (TextView) toastView.findViewById(R.id.toasttext);
        TOSTtEXT.setText(message);
        CustomToast = new Toast(getApplicationContext());
        CustomToast.setDuration(Toast.LENGTH_LONG);
        CustomToast.setView(toastView);
        CustomToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        CustomToast.show();
    }

    public void sendData() {
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Learner_Report_List.this, CreateReportLearner.class);
                    intent.putExtra("names", editAdapter.getItem(position).getName());
                    intent.putExtra("lastnames", editAdapter.getItem(position).getLastname());
                    startActivity(intent);



            }
        });
    }

    private boolean connectinAvailable() {
        boolean connection = false;
        ConnectivityManager cm = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                connection = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connection = true;
            } else {
                connection = false;
            }
        }
        return connection;
    }
}
