package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.sechaba.groupl.Adapter.Learner_adapter;
import com.example.sechaba.groupl.Classes.LearnerData;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MastereUsers extends AppCompatActivity {
    ListView lvData;
    AlertDialog progressDialog;
    ArrayList<LearnerData> LData;
    View toastView;
    Learner_adapter editAdapter;
    TextView TOSTtEXT;
    Toast CustomToast;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mastere_users);

        search = (EditText) findViewById(R.id.searching);
        lvData = (ListView) findViewById(R.id.list_of_learners);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Master");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (connectinAvailable()) {
            GetDataInBackgroundMine();
        } else {
            showCustomToast("NO INTERNET C0NNECTION!");
        }
        searchLearner();
    }

    public void searchLearner() {
        lvData.setTextFilterEnabled(true);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                int textlength = cs.length();
                ArrayList<LearnerData> temp = new ArrayList<LearnerData>();
                for (LearnerData c : LData) {
                    if (textlength <= c.getName().length()) {
                        if (c.getName().toLowerCase().contains(cs.toString().toLowerCase())) {
                            temp.add(c);
                        }
                    }
                }
                editAdapter = new Learner_adapter(getApplicationContext(), temp);
                lvData.setAdapter(editAdapter);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.masteruser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.LearnerRep:
                startActivity(new Intent(this, Report_Learner.class));
                break;
            case R.id.roles:
                startActivity(new Intent(this, UserDataList.class));
                break;
            case R.id.LearnerRip:
                startActivity(new Intent(this, ReportLearnerView.class));
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MastereUsers.this.finish();
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
                Intent intent = new Intent(getApplication(), Edit_learner.class);
                intent.putExtra("name", editAdapter.getItem(position).getName());
                intent.putExtra("lastname", editAdapter.getItem(position).getLastname());
                intent.putExtra("classname", editAdapter.getItem(position).getClassname());
                intent.putExtra("gender", editAdapter.getItem(position).getGender());
                intent.putExtra("race", editAdapter.getItem(position).getRace());
                intent.putExtra("birthday", editAdapter.getItem(position).getBirthdate());
                intent.putExtra("language", editAdapter.getItem(position).getLanguage());
                intent.putExtra("idno", editAdapter.getItem(position).getId_number());
                intent.putExtra("days", editAdapter.getItem(position).getDays());
                intent.putExtra("enrolmentdate", editAdapter.getItem(position).getEnrolment_date());
                intent.putExtra("address", editAdapter.getItem(position).getAddress());
                intent.putExtra("mothername", editAdapter.getItem(position).getMother_name());
                intent.putExtra("fathername", editAdapter.getItem(position).getFather_name());
                intent.putExtra("motheraddress", editAdapter.getItem(position).getMother_address());
                intent.putExtra("fatheraddress", editAdapter.getItem(position).getFather_address());
                intent.putExtra("mothercell", editAdapter.getItem(position).getMother_cell());
                intent.putExtra("fathercell", editAdapter.getItem(position).getFather_cell());
                intent.putExtra("doctorname", editAdapter.getItem(position).getDoctor_name());
                intent.putExtra("doctorcell", editAdapter.getItem(position).getDoctor_cell());
                intent.putExtra("medicalname", editAdapter.getItem(position).getMedical_name());
                intent.putExtra("medicalplan", editAdapter.getItem(position).getMedical_plan());
                intent.putExtra("medicalnumber", editAdapter.getItem(position).getMedical_number());
                intent.putExtra("learnerallergy", editAdapter.getItem(position).getLearner_allergy());
                intent.putExtra("objectID", editAdapter.getItem(position).getObjectId());
                intent.putExtra("learnertuckshop", editAdapter.getItem(position).getLearner_tuchshop_balance());
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

    //retrieving data from backendless
    public void GetDataInBackgroundMine() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<LearnerData>();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(5).setOffset(0);
        queryBuilder.setSortBy("classname");
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
                showCustomToast("ERROR:" + fault.getMessage());
                progressDialog.dismiss();
            }
        });


    }

}
