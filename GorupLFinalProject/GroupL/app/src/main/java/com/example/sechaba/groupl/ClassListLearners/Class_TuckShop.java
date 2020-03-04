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
import com.example.sechaba.groupl.Adapter.TuckShopAdapter;
import com.example.sechaba.groupl.Calculator_View;
import com.example.sechaba.groupl.Classes.LearnerData;
import com.example.sechaba.groupl.R;
import com.example.sechaba.groupl.Show_learner_data;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Class_TuckShop extends AppCompatActivity {
    String classname;
    AlertDialog progressDialog;
    ArrayList<LearnerData> LData;
    TuckShopAdapter tuckShopAdapter;
    ListView lvData;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    int imag[] = {R.drawable.redcross,R.drawable.correct};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class__tuck_shop);
        classname = getIntent().getExtras().getString("ClassnameT");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Tuckshop");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        lvData = (ListView) findViewById(R.id.classshowTuck);
        validateConnection();
    }
//        @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.activity_tuck__shop, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mail:
//                Toast.makeText(this, "Mail", Toast.LENGTH_LONG).show();
//
//                break;
//            case R.id.delete:
//                Toast.makeText(this, "Delete", Toast.LENGTH_LONG).show();
//
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
    public void showCustomToast(CharSequence message) {
        toastView = getLayoutInflater().inflate(R.layout.customtoast, (ViewGroup) findViewById(R.id.custom_toast));
        TOSTtEXT = (TextView) toastView.findViewById(R.id.toasttext);
        TOSTtEXT.setText(message);
        CustomToast = new Toast(getApplicationContext());
        CustomToast.setDuration(Toast.LENGTH_LONG);
        CustomToast.setView(toastView);
        CustomToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        CustomToast.show();
    }//method
    public void validateConnection()
    {
        if(connectinAvailable())
        {
            listViewer();
        }
        else
        {
            showCustomToast("No connection for ClASSES");
        }

    }//method
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
    public void sendData()
    {
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), Calculator_View.class);
                intent.putExtra("name", LData.get(position).getName());
                intent.putExtra("surname", LData.get(position).getLastname());
                intent.putExtra("classname", LData.get(position).getClassname());
                intent.putExtra("objecId", LData.get(position).getObjectId());
                intent.putExtra("amountcurrent", LData.get(position).getLearner_tuchshop_balance());
                startActivity(intent);
            }
        });
    }

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
                tuckShopAdapter = new TuckShopAdapter(getApplicationContext(),LData,imag);
                progressDialog.dismiss();
                lvData.setAdapter(tuckShopAdapter);
                sendData();


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast("error:"+ fault.getMessage());
                progressDialog.dismiss();
            }
        });

    }

}
