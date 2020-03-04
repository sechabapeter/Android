package com.example.sechaba.groupl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.example.sechaba.groupl.Adapter.Learner_adapter;
import com.example.sechaba.groupl.Classes.EmailClass;
import com.example.sechaba.groupl.Classes.SMS_SEND;

public class Show_learner_data extends AppCompatActivity {
    PendingIntent sentPI, deliveryPI;
    BroadcastReceiver smsSentReciever, smsDeliveryReciever;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS DELIVERED";
    int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    EditText name, lastname, classname,
            race, birthdate, language,
            IDnumber, enrolment_date, address, mothername, fathername,
            emailadressmother,
            emailadressfather,
            cellmother, cellfather,
            familydoctor, doctorcell,
            medicalidname, medicalpla, medicalnumber,
            allergylearner, tuchshopbalance, gender, days;
    String name_learner, surname_learner,
            classname_learner, id_number_learner,
            gender_learner, race_learner,
            birthday_learner, language_learner, days_learner, enrolmentdate_learner, address_learner, mothername_learner, fathername_learner, motheraddress_learner, fatheraddress_learner,
            mothercell_learner, fathercell_learner, doctorname_learner,
            doctorcell_learner, medicalname_learner, medicalplan_learner,
            medicalnumber_learner, learnerallergy, learnertuckshop;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    String objectId;
    String role = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_learner_data);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Learner's Details");
        actionBar.setDisplayUseLogoEnabled(true);
        role = Backendless.UserService.CurrentUser().getProperty("role").toString();

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveryPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        check();
        setToFalse();

    }

    public void check() {
        if (connectinAvailable()) {
            getData();
        } else {
            showCustomToast("no internet");

        }
    }

    public void getData() {
        objectId = getIntent().getExtras().getString("objectID");
        name_learner = getIntent().getExtras().getString("name");
        surname_learner = getIntent().getExtras().getString("lastname");
        classname_learner = getIntent().getExtras().getString("classnam");
        id_number_learner = getIntent().getExtras().getString("idno");
        gender_learner = getIntent().getExtras().getString("gender");
        race_learner = getIntent().getExtras().getString("race");
        birthday_learner = getIntent().getExtras().getString("birthday");
        language_learner = getIntent().getExtras().getString("language");
        days_learner = getIntent().getExtras().getString("days");
        enrolmentdate_learner = getIntent().getExtras().getString("enrolmentdate");
        address_learner = getIntent().getExtras().getString("address");
        mothername_learner = getIntent().getExtras().getString("mothername");
        fathername_learner = getIntent().getExtras().getString("fathername");
        motheraddress_learner = getIntent().getExtras().getString("motheraddress");
        fatheraddress_learner = getIntent().getExtras().getString("fatheraddress");
        mothercell_learner = getIntent().getExtras().getString("mothercell");
        fathercell_learner = getIntent().getExtras().getString("fathercell");
        doctorname_learner = getIntent().getExtras().getString("doctorname");
        doctorcell_learner = getIntent().getExtras().getString("doctorcell");
        medicalname_learner = getIntent().getExtras().getString("medicalname");
        medicalplan_learner = getIntent().getExtras().getString("medicalplan");
        medicalnumber_learner = getIntent().getExtras().getString("medicalnumber");
        learnerallergy = getIntent().getExtras().getString("learnerallergy");
        learnertuckshop = getIntent().getExtras().getString("learnertuckshop");

        name = (EditText) findViewById(R.id.LfirstNameL);
        name.setText(name_learner);
        lastname = (EditText) findViewById(R.id.LlastNameLearL);
        lastname.setText(surname_learner);
        classname = (EditText) findViewById(R.id.LclassnamepleL);
        classname.setText(classname_learner);
        race = (EditText) findViewById(R.id.Lrace_learner);
        race.setText(race_learner);
        days = (EditText) findViewById(R.id.Llearner_daysL);
        days.setText(days_learner);
        gender = (EditText) findViewById(R.id.Lgender_learnerL);
        gender.setText(gender_learner);
        birthdate = (EditText) findViewById(R.id.LdatebirthL);
        birthdate.setText(birthday_learner);
        language = (EditText) findViewById(R.id.LlanguageLeL);
        language.setText(language_learner);
        IDnumber = (EditText) findViewById(R.id.LidNumberL);
        IDnumber.setText(id_number_learner);
        enrolment_date = (EditText) findViewById(R.id.LenrollDateL);
        enrolment_date.setText(enrolmentdate_learner);
        address = (EditText) findViewById(R.id.LaddressLeL);
        address.setText(address_learner);
        mothername = (EditText) findViewById(R.id.LmotherLeL);
        mothername.setText(mothername_learner);
        fathername = (EditText) findViewById(R.id.LfatherL);
        fathername.setText(fathername_learner);
        emailadressmother = (EditText) findViewById(R.id.LmomEmailL);
        emailadressmother.setText(motheraddress_learner);
        emailadressfather = (EditText) findViewById(R.id.LdadEmailL);
        emailadressfather.setText(fatheraddress_learner);
        cellmother = (EditText) findViewById(R.id.LmomPhoneL);
        cellmother.setText(mothercell_learner);
        cellfather = (EditText) findViewById(R.id.LdadPhoneL);
        cellfather.setText(fathercell_learner);
        familydoctor = (EditText) findViewById(R.id.LnamedoctorL);
        familydoctor.setText(doctorname_learner);
        doctorcell = (EditText) findViewById(R.id.LdoctorPhoneL);
        doctorcell.setText(doctorcell_learner);
        medicalidname = (EditText) findViewById(R.id.LmedAidnameL);
        medicalidname.setText(medicalname_learner);
        medicalpla = (EditText) findViewById(R.id.LmedAidPlanL);
        medicalpla.setText(medicalplan_learner);
        medicalnumber = (EditText) findViewById(R.id.LmedAidNumberL);
        medicalnumber.setText(medicalnumber_learner);
        allergylearner = (EditText) findViewById(R.id.LallergiesL);
        allergylearner.setText(learnerallergy);
        tuchshopbalance = (EditText) findViewById(R.id.LbalanceTuckL);
        tuchshopbalance.setText(learnertuckshop);
    }

    public void NotPickedUp() {
        String messages = "A Learner is not Picked Up";

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(mothercell_learner, null, messages, sentPI, deliveryPI);
        }
    }

    public void PickedUp() {
        String messages = "A Learner Has already been picked";

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(mothercell_learner, null, messages, sentPI, deliveryPI);
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        smsSentReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(Show_learner_data.this, "SMS sent!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(Show_learner_data.this, "Generic Failure!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(Show_learner_data.this, "No Service!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(Show_learner_data.this, "Null PDU!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(Show_learner_data.this, "Radio Off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveryReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(Show_learner_data.this, "SMS delivered!", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(Show_learner_data.this, "SMS Not Delivered!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        registerReceiver(smsSentReciever, new IntentFilter(SENT));
        registerReceiver(smsDeliveryReciever, new IntentFilter(DELIVERED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(smsDeliveryReciever);
        unregisterReceiver(smsSentReciever);
    }

    public void SendPickedUp() {
        final CharSequence[] options = {"Not Picked Up", "Picked Up"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Custom);
        builder.setTitle("Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Not Picked Up")) {
                    NotPickedUp();
                } else if (options[which].equals("Picked Up")) {
                    PickedUp();
                    new EmailClass(Show_learner_data.this).execute(new String[]{motheraddress_learner}, "AttachedEmail", "A Learner is not Picked Up", null);

                }

            }
        });
        builder.show();


    }

    public void setToFalse() {
        name.setEnabled(false);
        lastname.setEnabled(false);
        classname.setEnabled(false);
        days.setEnabled(false);
        race.setEnabled(false);
        gender.setEnabled(false);
        birthdate.setEnabled(false);
        language.setEnabled(false);
        IDnumber.setEnabled(false);
        enrolment_date.setEnabled(false);
        address.setEnabled(false);
        mothername.setEnabled(false);
        fathername.setEnabled(false);
        emailadressmother.setEnabled(false);
        emailadressfather.setEnabled(false);
        cellmother.setEnabled(false);
        cellfather.setEnabled(false);
        familydoctor.setEnabled(false);
        doctorcell.setEnabled(false);
        medicalidname.setEnabled(false);
        medicalpla.setEnabled(false);
        medicalnumber.setEnabled(false);
        allergylearner.setEnabled(false);
        tuchshopbalance.setEnabled(false);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.showlearner, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.call:

                Intent intent = new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
                break;
            case R.id.mail:
                    SendPickedUp();
                break;
        }
        return super.onOptionsItemSelected(item);
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
    public void onBackPressed() {
        super.onBackPressed();
        Show_learner_data.this.finish();
    }
}
