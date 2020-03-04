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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.sechaba.groupl.Classes.LearnerData;

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Learner_data extends AppCompatActivity {
    EditText name, lastname, birthdate, language,
            IDnumber, enrolment_date, address, mothername, fathername,
            emailadressmother,
            emailadressfather,
            cellmother, cellfather,
            familydoctor, doctorcell,
            medicalidname, medicalpla, medicalnumber,
            allergylearner, tuchshopbalance;
    AlertDialog progressDialog;
    LearnerData learnerData;
    Spinner Race;
    Spinner classesnames, days, gender;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    String birthdatelear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Learner Data");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        name = (EditText) findViewById(R.id.firstNameL);
        lastname = (EditText) findViewById(R.id.lastNameLear);
        Race = (Spinner) findViewById(R.id.sp_Race);
        classesnames = (Spinner) findViewById(R.id.sp_classes);
        days = (Spinner) findViewById(R.id.sp_days);
        gender = (Spinner) findViewById(R.id.sp_gender);
        birthdate = (EditText) findViewById(R.id.datebirthL);
        language = (EditText) findViewById(R.id.languageLe);
        IDnumber = (EditText) findViewById(R.id.idNumberL);
        enrolment_date = (EditText) findViewById(R.id.enrollDate);
        address = (EditText) findViewById(R.id.addressLe);
        mothername = (EditText) findViewById(R.id.motherLe);
        fathername = (EditText) findViewById(R.id.fatherL);
        emailadressmother = (EditText) findViewById(R.id.momEmail);
        emailadressfather = (EditText) findViewById(R.id.dadEmail);
        cellmother = (EditText) findViewById(R.id.momPhone);
        cellfather = (EditText) findViewById(R.id.dadPhone);
        familydoctor = (EditText) findViewById(R.id.namedoctor);
        doctorcell = (EditText) findViewById(R.id.doctorPhone);
        medicalidname = (EditText) findViewById(R.id.medAidname);
        medicalpla = (EditText) findViewById(R.id.medAidPlan);
        medicalnumber = (EditText) findViewById(R.id.medAidNumber);
        allergylearner = (EditText) findViewById(R.id.allergies);
        tuchshopbalance = (EditText) findViewById(R.id.balanceTuck);

    }

    public void SaveLearner(View view) {
        if (name.getText().toString().trim().isEmpty() || lastname.getText().toString().trim().isEmpty()
                || birthdate.getText().toString().trim().isEmpty() || language.getText().toString().trim().isEmpty()
                || IDnumber.getText().toString().trim().isEmpty() || enrolment_date.getText().toString().trim().isEmpty() || address.getText().toString().trim().isEmpty() || mothername.getText().toString().trim().isEmpty()
                || fathername.getText().toString().trim().isEmpty() || emailadressmother.getText().toString().trim().isEmpty()
                || emailadressfather.getText().toString().trim().isEmpty() || cellmother.getText().toString().trim().isEmpty()
                || cellfather.getText().toString().trim().isEmpty() || familydoctor.getText().toString().trim().isEmpty()
                || doctorcell.getText().toString().trim().isEmpty() || medicalidname.getText().toString().trim().isEmpty()
                || medicalpla.getText().toString().trim().isEmpty() || medicalnumber.getText().toString().isEmpty()
                || allergylearner.getText().toString().trim().isEmpty() || tuchshopbalance.getText().toString().trim().isEmpty())

        {
            showCustomToast("Please Enter all fields!");


        } else {

            if (connectinAvailable()) {
                Variables();
            } else {
                showCustomToast("Please Connect to the Internet!");
            }


        }

    }


    public void Variables() {
        String learnername = name.getText().toString().trim();
        String lastnamel = lastname.getText().toString().trim();
        String race = Race.getSelectedItem().toString();
        String classesn = classesnames.getSelectedItem().toString();
        birthdatelear = birthdate.getText().toString().trim();
        String languagelearner = language.getText().toString().trim();
        String idnumberlearner = IDnumber.getText().toString().trim();
        String Days = days.getSelectedItem().toString();
        String enrolmentdated = enrolment_date.getText().toString().trim();
        String L_Address = address.getText().toString().trim();
        String L_gender = gender.getSelectedItem().toString();
        String mothernam = mothername.getText().toString().trim();
        String fathernam = fathername.getText().toString().trim();
        String emailmom = emailadressmother.getText().toString().trim();
        String emailfath = emailadressfather.getText().toString().trim();
        String cellmom = cellmother.getText().toString().trim();
        String fathercel = cellfather.getText().toString().trim();
        String docfam = familydoctor.getText().toString().trim();
        String doccel = doctorcell.getText().toString().trim();
        String medinam = medicalidname.getText().toString().trim();
        String mediplan = medicalpla.getText().toString().trim();
        String medinum = medicalnumber.getText().toString().trim();
        String allergy = allergylearner.getText().toString().trim();
        String tuchbalanc = tuchshopbalance.getText().toString().trim();


        learnerData = new LearnerData();
        learnerData.setName(learnername);
        learnerData.setLastname(lastnamel);
        learnerData.setClassname(classesn);
        learnerData.setGender(L_gender);
        learnerData.setRace(race);
        learnerData.setBirthdate(birthdatelear);
        learnerData.setLanguage(languagelearner);
        learnerData.setId_number(idnumberlearner);
        learnerData.setDays(Days);
        learnerData.setEnrolment_date(enrolmentdated);
        learnerData.setAddress(L_Address);
        learnerData.setFather_name(fathernam);
        learnerData.setMother_name(mothernam);
        learnerData.setFather_address(emailfath);
        learnerData.setMother_address(emailmom);
        learnerData.setFather_cell(fathercel);
        learnerData.setMother_cell(cellmom);
        learnerData.setDoctor_name(docfam);
        learnerData.setDoctor_cell(doccel);
        learnerData.setMedical_name(medinam);
        learnerData.setMedical_plan(mediplan);
        learnerData.setMedical_number(medinum);
        learnerData.setLearner_allergy(allergy);
        learnerData.setLearner_tuchshop_balance(tuchbalanc);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        Backendless.Persistence.save(learnerData, new AsyncCallback<LearnerData>() {
            @Override
            public void handleResponse(LearnerData response) {
                showCustomToast("Successfully Saved!");
                progressDialog.dismiss();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();

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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Learner_data.this, List_of_Learners.class));
        Learner_data.this.finish();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
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

