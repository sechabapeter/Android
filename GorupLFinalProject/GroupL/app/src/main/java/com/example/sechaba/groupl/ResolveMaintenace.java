package com.example.sechaba.groupl;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.example.sechaba.groupl.Classes.ContactsApplication;
import com.example.sechaba.groupl.Classes.ReportClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResolveMaintenace extends AppCompatActivity {
    View toastView;
    TextView TOSTtEXT, username, status;
    Toast CustomToast;
    String objectI, statuses, email;
    ReportClass reportClass;
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_maintenace);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Resolve Maintenance Report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        objectI = getIntent().getStringExtra("objectId");
        statuses = getIntent().getStringExtra("status");
        email = getIntent().getStringExtra("Email");
        status = (TextView) findViewById(R.id.statusre);
        date = ContactsApplication.create(10, 10, 2030, 10, 10, 10);
        status.setText(statuses);
        username = (TextView) findViewById(R.id.username);
        username.setText(email);

    }

    public void changeresolve(View view) {
        reportClass = new ReportClass();
        reportClass.setStatus("Resolved");
        reportClass.setObjectId(objectI);
        Backendless.Persistence.save(reportClass, new AsyncCallback<ReportClass>() {
            @Override
            public void handleResponse(ReportClass response) {
                showCustomToast("Successfully Edited");
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        SetChannel();
        pushing();

    }

    public void SetChannel() {
        List<String> channels = new ArrayList<String>();
        channels.add("Master");

        Backendless.Messaging.registerDevice("914750615191", channels, date, new AsyncCallback<Void>() {

            @Override
            public void handleResponse(Void response) {
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                showCustomToast("Error " + fault.getMessage());

            }
        });

    }

    public void pushing() {
        String messages = "Report Resolved";

        PublishOptions options = new PublishOptions();
        options.putHeader("android-ticker-text", "Report!");
        options.putHeader("android-content-title", "Report Resolved");
        options.putHeader("android-content-text", "Report Created");

        Backendless.Messaging.publish("Master", messages, options, new AsyncCallback<MessageStatus>() {
            @Override
            public void handleResponse(MessageStatus response) {


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast("Error " + fault.getMessage());


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ResolveMaintenace.this.finish();
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
