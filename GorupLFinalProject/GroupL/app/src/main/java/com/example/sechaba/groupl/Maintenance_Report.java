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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Maintenance_Report extends AppCompatActivity {
    EditText description;
    AlertDialog progressDialog;
    Spinner classnames;
    ListView lvData;
    Date date;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    ArrayList<ReportClass> LData;
    Spinner problemsreport;
    ReportClass reportClass;
    String user = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance__report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Maintenance Reports");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        date = ContactsApplication.create(10, 10, 2030, 10, 10, 10);
        description = (EditText) findViewById(R.id.description);

        classnames = (Spinner) findViewById(R.id.sp_classes);
        problemsreport = (Spinner) findViewById(R.id.sp_maintenance);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.person:
                Intent intent = new Intent(Maintenance_Report.this, MaintenanceReportList.class);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Maintenance_Report.this.finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_reports, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void Variables() {
        String describe = description.getText().toString().trim();
        String problems = problemsreport.getSelectedItem().toString();
        String classreport = classnames.getSelectedItem().toString();
        user = Backendless.UserService.CurrentUser().getProperty("email").toString();
        reportClass = new ReportClass();
        reportClass.setDescription(describe);
        reportClass.setClases(classreport);
        reportClass.setProblems(problems);
        reportClass.setStatus("Unresolved");
        reportClass.setEmails(user);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        Backendless.Persistence.save(reportClass, new AsyncCallback<ReportClass>() {
            @Override
            public void handleResponse(ReportClass response) {

                showCustomToast("report successfully Created");
                progressDialog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();
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

    public void ReportMain(View view) {

        if (connectinAvailable()) {
            Variables();
            notif();
            pushing();


        } else {
            showCustomToast("No internet connection!");
        }
    }

    public void notif() {
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
        String messages = "Maintenance Report";

        PublishOptions options = new PublishOptions();
        options.putHeader("android-ticker-text", "Report!");
        options.putHeader("android-content-title", "Maintenance Report");
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
