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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.sechaba.groupl.Adapter.Learner_adapter;
import com.example.sechaba.groupl.Adapter.UserAdpter;
import com.example.sechaba.groupl.Classes.UserClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class UserDataList extends AppCompatActivity {
    ListView lvData;
    AlertDialog progressDialog;
    ArrayList<BackendlessUser> LData;
    View toastView;
    UserAdpter userAdpter;
    TextView TOSTtEXT;
    Toast CustomToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("List of Users Registered");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        lvData = (ListView) findViewById(R.id.user_data_list);

        if (connectinAvailable()) {
            GetDataInBackgroundMine();
        } else {
            showCustomToast("NO INTERNET C0NNECTION!");
        }
    }

    public void GetDataInBackgroundMine() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<>();

        Backendless.Data.of(BackendlessUser.class).find(new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {
                Iterator<BackendlessUser> userIterator = response.iterator();
                while (userIterator.hasNext()) {
                    BackendlessUser user = userIterator.next();
                    LData.add(user);
                    userAdpter = new UserAdpter(getApplicationContext(), LData);
                    lvData.setAdapter(userAdpter);
                    progressDialog.dismiss();
                    roles();

                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    public void roles() {
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Roles_Users.class);
                intent.putExtra("objectID", userAdpter.getItem(position).getObjectId());
                startActivity(intent);
                return false;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UserDataList.this.finish();
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
