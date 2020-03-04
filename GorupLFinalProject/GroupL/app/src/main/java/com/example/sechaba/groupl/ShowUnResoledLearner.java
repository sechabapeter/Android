package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.sechaba.groupl.Adapter.AdapterLearnerRe;
import com.example.sechaba.groupl.Classes.ReportLearner;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ShowUnResoledLearner extends AppCompatActivity {
    ListView listreports;
    AlertDialog progressDialog;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    ArrayList<ReportLearner> LData;
    AdapterLearnerRe adapterLearnerRe;
    int imag[] = {R.drawable.redcross, R.drawable.correct};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_un_resoled_learner);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("UnResolved Report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        listreports = (ListView) findViewById(R.id.unlearnerreportshow);
        reportdata();
    }

    public void reportdata() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<ReportLearner>();
        String whereClause = "status = 'NotResolved'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(5).setOffset(0);
        Backendless.Persistence.of(ReportLearner.class).find(queryBuilder, new AsyncCallback<List<ReportLearner>>() {
            @Override
            public void handleResponse(final List<ReportLearner> response) {
                LData.addAll(response);
                adapterLearnerRe = new AdapterLearnerRe(getApplicationContext(), LData, imag);
                listreports.setAdapter(adapterLearnerRe);
                progressDialog.dismiss();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ShowUnResoledLearner.this.finish();
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
}
