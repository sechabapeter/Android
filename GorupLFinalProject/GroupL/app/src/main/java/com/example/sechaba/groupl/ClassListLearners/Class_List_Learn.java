package com.example.sechaba.groupl.ClassListLearners;

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
import com.example.sechaba.groupl.Classes.LearnerData;
import com.example.sechaba.groupl.R;
import com.example.sechaba.groupl.Show_learner_data;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Class_List_Learn extends AppCompatActivity {
    AlertDialog progressDialog;
    ArrayList<LearnerData> LData;
    ListView lvData;
    Learner_adapter editAdapter;
    String classname;
    View toastView;
    TextView  TOSTtEXT;
    Toast CustomToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Learner Information");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        classname = getIntent().getExtras().getString("Classname");

        validateConnection();
        lvData = (ListView) findViewById(R.id.classshowLeraners);

    }


  public void validateConnection()
  {
      if(connectinAvailable())
      {
          listViewer();
      }//end if
      else
      {
          showCustomToast("No connection for ClASS A");
      }//end else

  }//end method
    public  void listViewer()
    {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<LearnerData>();
        String whereClause =String.format("classname = '%s'",classname);//Interpolator of strings
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(5).setOffset(0);
        queryBuilder.setSortBy("name");

        Backendless.Persistence.of(LearnerData.class).find(queryBuilder, new AsyncCallback<List<LearnerData>>() {
            @Override
            public void handleResponse(List<LearnerData> response) {

                LData.addAll(response);
                editAdapter = new Learner_adapter(getApplicationContext(),LData);
                progressDialog.dismiss();
                lvData.setAdapter(editAdapter);
                sendData();

            }//end backendless

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast("error:"+ fault.getMessage());
                progressDialog.dismiss();
            }
        });

    }//end method
    public void showCustomToast(CharSequence message) {
        toastView = getLayoutInflater().inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast));
        TOSTtEXT = (TextView) toastView.findViewById(R.id.toasttext);
        TOSTtEXT.setText(message);
        CustomToast = new Toast(getApplicationContext());
        CustomToast.setDuration(Toast.LENGTH_LONG);
        CustomToast.setView(toastView);
        CustomToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        CustomToast.show();
    }//end method


    public void sendData()
    {
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Class_List_Learn.this, Show_learner_data.class);
                intent.putExtra("name",editAdapter.getItem(position).getName());
                intent.putExtra("lastname",editAdapter.getItem(position).getLastname());
                intent.putExtra("classnam",editAdapter.getItem(position).getClassname());
                intent.putExtra("gender",editAdapter.getItem(position).getGender());
                intent.putExtra("race",editAdapter.getItem(position).getRace());
                intent.putExtra("birthday",editAdapter.getItem(position).getBirthdate());
                intent.putExtra("language",editAdapter.getItem(position).getLanguage());
                intent.putExtra("idno",editAdapter.getItem(position).getId_number());
                intent.putExtra("days",editAdapter.getItem(position).getDays());
                intent.putExtra("enrolmentdate",editAdapter.getItem(position).getEnrolment_date());
                intent.putExtra("address",editAdapter.getItem(position).getAddress());
                intent.putExtra("mothername",editAdapter.getItem(position).getMother_name());
                intent.putExtra("fathername",editAdapter.getItem(position).getFather_name());
                intent.putExtra("motheraddress",editAdapter.getItem(position).getMother_address());
                intent.putExtra("fatheraddress",editAdapter.getItem(position).getFather_address());
                intent.putExtra("mothercell",editAdapter.getItem(position).getMother_cell());
                intent.putExtra("fathercell",editAdapter.getItem(position).getFather_cell());
                intent.putExtra("doctorname",editAdapter.getItem(position).getDoctor_name());
                intent.putExtra("doctorcell",editAdapter.getItem(position).getDoctor_cell());
                intent.putExtra("medicalname",editAdapter.getItem(position).getMedical_name());
                intent.putExtra("medicalplan",editAdapter.getItem(position).getMedical_plan());
                intent.putExtra("medicalnumber",editAdapter.getItem(position).getMedical_number());
                intent.putExtra("learnerallergy",editAdapter.getItem(position).getLearner_allergy());
                intent.putExtra("objectID",editAdapter.getItem(position).getObjectId());
                intent.putExtra("learnertuckshop",editAdapter.getItem(position).getLearner_tuchshop_balance());
                startActivity(intent);
            }
        });
    }//end method
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
    }//end method
}//end of class
