package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.example.sechaba.groupl.Classes.ContactsApplication;
import com.example.sechaba.groupl.Classes.LearnerData;
import com.example.sechaba.groupl.Classes.ReportLearner;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class CreateReportLearner extends AppCompatActivity {
    RadioButton justinfor, inforparents, getprofessional;
    EditText title;
    EditText datareport;
    View toastView;
    TextView TOSTtEXT;
    EditText seelearner;
    Date date;
    Toast CustomToast;
    AlertDialog progressDialog;
    ReportLearner reportlearner;
    String name_learner, surname_learner, role, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educator);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Learner report");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        email = Backendless.UserService.CurrentUser().getProperty("email").toString();
        role = Backendless.UserService.CurrentUser().getProperty("role").toString();

        justinfor = (RadioButton) findViewById(R.id.rbtn_for_info);
        inforparents = (RadioButton) findViewById(R.id.rbnt_inform);
        getprofessional = (RadioButton) findViewById(R.id.rbtn_get);

        datareport = (EditText) findViewById(R.id.data);
        date = ContactsApplication.create(10, 10, 2030, 10, 10, 10);
        check();
        setToFalse();


    }

    public void geMyName() {
        surname_learner = getIntent().getExtras().getString("lastnames");
        name_learner = getIntent().getExtras().getString("names");
        seelearner = (EditText) findViewById(R.id.shownamelearn);
        seelearner.setText(name_learner + "," + surname_learner);
        title = (EditText) findViewById(R.id.et_title);
        title.setText(name_learner + "," + surname_learner);
    }

    public void setToFalse() {
        seelearner.setEnabled(false);
        title.setEnabled(false);
    }

    public void check() {
        if (connectinAvailable()) {
            geMyName();
        } else {
            showCustomToast("no internet");

        }
    }

    public void submitreport(View view) {
        if (connectinAvailable()) {
            Variables();
            notifybtn();
            push();

        } else {
            showCustomToast("No internet connection!");
        }
    }

    public void Variables() {

        reportlearner = new ReportLearner();

        String datareports = datareport.getText().toString().trim();
        String justinforchecked = "Just for information";
        String informparent = "Inform parents";
        String getprofessionals = "Get Professional help";
        if (justinfor.isClickable()) {

            reportlearner.setJustinfor(justinforchecked);

        } else if (inforparents.isChecked()) {
            reportlearner.setInforparents(informparent);
        } else if (getprofessional.isChecked()) {
            reportlearner.setGetprofhelp(getprofessionals);
        }
        reportlearner.setTitle(name_learner + "," + surname_learner);
        reportlearner.setDatareport(datareports);
        reportlearner.setStatus("NotResolved");
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        Backendless.Persistence.save(reportlearner, new AsyncCallback<ReportLearner>() {
            @Override
            public void handleResponse(ReportLearner response) {
                showCustomToast("Created Successfully");
                progressDialog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();
            }
        });

    }

    public void notifybtn() {
        List<String> channels = new ArrayList<String>();
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

    public void push() {
        String message = "Learner report" +
                "#3. Email :" + email;

        PublishOptions options = new PublishOptions();
        options.putHeader("android-ticker-text", "Report");
        options.putHeader("android-content-title", "Learner report");
        options.putHeader("android-content-text", "created");

        Backendless.Messaging.publish("Master", message, options, new AsyncCallback<MessageStatus>() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.viewlearnerreport, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.learnerinf:
                startActivity(new Intent(this, ReportLearnerView.class));
                break;
            case R.id.deleteL:
                if (role.equals("Master") || role.equals("Admin")) {
                    new DeletetBulkFromBackEnd().execute();
                } else {
                    showCustomToast("Only The User Has a Permission to Delete Reports");
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private class DeletetBulkFromBackEnd extends AsyncTask<Void, Void, Exception> {

        @Override
        protected Exception doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            Exception exception = null;


            try {
                URL url = new URL("https://api.backendless.com/F6C15024-B723-6512-FF01-73A1E7B9AD00/BBBBAE79-FB50-7B3C-FF3D-76922F472300/data/bulk/ReportLearner");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("application-id", "F6C15024-B723-6512-FF01-73A1E7B9AD00");
                urlConnection.setRequestProperty("secret-key", "BBBBAE79-FB50-7B3C-FF3D-76922F472300");
                urlConnection.setRequestProperty("application-type", "REST");
                urlConnection.setRequestMethod("DELETE");
                urlConnection.getInputStream();
                urlConnection.connect();
            } catch (MalformedURLException e) {
                exception = e;
            } catch (Exception e) {
                exception = e;
            } finally {
                urlConnection.disconnect();
            }

            return exception;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showCustomToast("Process started...please wait.....");
        }

        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);
            showCustomToast("Process completed!");

            if (e != null) {
                showCustomToast(e.getMessage());
            } else {
                showCustomToast("Successfully deleted!");
            }

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CreateReportLearner.this.finish();
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
