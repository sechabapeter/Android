package com.example.sechaba.groupl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.sechaba.groupl.Adapter.Learner_adapter;
import com.example.sechaba.groupl.Classes.LearnerData;

import java.util.List;

public class Roles_Users extends AppCompatActivity {
    TextView tvMessage;
    Spinner s;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    String userobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roles__users);
        tvMessage = (TextView) findViewById(R.id.textView4);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("User Roles");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        s = (Spinner) findViewById(R.id.userroles);

        userobj = getIntent().getExtras().getString("objectID");
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

    public void Toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Roles_Users.this.finish();
    }

    public void onClicking(View view) {

        if (connectinAvailable()) {
            try {
                final BackendlessUser user = new BackendlessUser();
                user.setProperty("role", s.getSelectedItem().toString());
                user.setProperty("objectId", userobj);
                Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser backendlessUser) {
                        showCustomToast("profile updated successfully");


                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {

                        showCustomToast("Error : " + backendlessFault);


                    }
                });
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else {
            showCustomToast("Please check your internet connection first!");

        }


    }


}
