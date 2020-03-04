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
import com.example.sechaba.groupl.Classes.ReportLearner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResolvedLearnerReport extends AppCompatActivity {
    View toastView;
    TextView TOSTtEXT, username, status;
    Toast CustomToast;
    Date date;
    String objectI, statuses, title;
    ReportLearner reportLearner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolved_learner_report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Resolve Learner Report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        objectI = getIntent().getStringExtra("objectId");
        statuses = getIntent().getStringExtra("statusL");
        title = getIntent().getStringExtra("title");
        status = (TextView) findViewById(R.id.statusLearn);
        date = ContactsApplication.create(10, 10, 2030, 10, 10, 10);
        status.setText(statuses);
        username = (TextView) findViewById(R.id.titleL);
        username.setText(title);
    }

    public void resolvedreport(View view) {
        reportLearner = new ReportLearner();
        reportLearner.setStatus("Resolved");
        reportLearner.setObjectId(objectI);
        Backendless.Persistence.save(reportLearner, new AsyncCallback<ReportLearner>() {
            @Override
            public void handleResponse(ReportLearner response) {
                showCustomToast("Successfuly Changed to Resolved");
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        notif();
        pushing();

    }

    public void notif() {
        List<String> channels = new ArrayList<String>();
        channels.add("Master");
        channels.add("Default");

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
        String messages = "Decision Made";

        PublishOptions options = new PublishOptions();
        options.putHeader("android-ticker-text", "Report!");
        options.putHeader("android-content-title", "Decision Made");
        options.putHeader("android-content-text", "Decision Taken");

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
        ResolvedLearnerReport.this.finish();
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
