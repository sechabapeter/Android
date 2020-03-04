package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.sechaba.groupl.Adapter.Learner_adapter;
import com.example.sechaba.groupl.Classes.LearnerData;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;

import dmax.dialog.SpotsDialog;

public class Edit_learner extends AppCompatActivity {
    EditText name, lastname, classname,
            race, birthdate, language,
            IDnumber, enrolment_date, address, mothername, fathername,
            emailadressmother,
            emailadressfather,
            cellmother, cellfather,
            familydoctor, doctorcell,
            medicalidname, medicalpla, medicalnumber,
            allergylearner, tuchshopbalance, gender, days;
    ArrayList<LearnerData> LData;
    ListView lvData;
    LearnerData learnerData;
    AlertDialog progressDialog;
    TextView alert;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    Learner_adapter learner_adapter;
    Button sub;
    public static String name_learner, surname_learner, objectId,
            classname_learner, id_number_learner,
            gender_learner, race_learner,
            birthday_learner, language_learner, days_learner, enrolmentdate_learner, address_learner, mothername_learner, fathername_learner, motheraddress_learner, fatheraddress_learner,
            mothercell_learner, fathercell_learner, doctorname_learner,
            doctorcell_learner, medicalname_learner, medicalplan_learner,
            medicalnumber_learner, learnerallergy, fathercell, learnertuckshop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_learner);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Details");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getData();
        sub = (Button) findViewById(R.id.submitButtonLearner);
        setToFalse();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_edit, menu);
        return super.onCreateOptionsMenu(menu);
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


    private class DeletetBulkFromBackEnd extends AsyncTask<Void, Void, Exception> {

        @Override
        protected Exception doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            Exception exception = null;


            try {
                URL url = new URL("https://api.backendless.com/F6C15024-B723-6512-FF01-73A1E7B9AD00/BBBBAE79-FB50-7B3C-FF3D-76922F472300/data/LearnerData/" + objectId);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("application-id", "85314224-5E31-DE56-FF9B-2A9DCE392900");
                urlConnection.setRequestProperty("secret-key", "2740F390-BA10-E22E-FF79-6AEDD60F2600");
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

    public void delealert() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.alert, null);
        alert = (TextView) view.findViewById(R.id.alerting);
        alert.setText("Are you sure you want to delete the list of Learner ?" + "\n" + "This cannot be undone!");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Learner ?");
        builder.setView(view);
        builder.setIcon(R.mipmap.my_school);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (connectinAvailable()) {
                    new DeletetBulkFromBackEnd().execute();
                } else {
                    showCustomToast("No internet Connection");
                }


            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showCustomToast("LEARNER NOT DELETED");

            }
        });
        builder.show();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                delealert();
                break;
            case R.id.editlearner:
                setEDIT();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData() {
        objectId = getIntent().getExtras().getString("objectID");
        name_learner = getIntent().getExtras().getString("name");
        surname_learner = getIntent().getExtras().getString("lastname");
        classname_learner = getIntent().getExtras().getString("classname");
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

        name = (EditText) findViewById(R.id.firstNameL);
        name.setText(name_learner);
        lastname = (EditText) findViewById(R.id.lastNameLear);
        lastname.setText(surname_learner);
        classname = (EditText) findViewById(R.id.classnameple);
        classname.setText(classname_learner);
        race = (EditText) findViewById(R.id.race_learner);
        race.setText(race_learner);
        days = (EditText) findViewById(R.id.learner_days);
        days.setText(days_learner);
        gender = (EditText) findViewById(R.id.gender_learner);
        gender.setText(gender_learner);
        birthdate = (EditText) findViewById(R.id.datebirthL);
        birthdate.setText(birthday_learner);
        language = (EditText) findViewById(R.id.languageLe);
        language.setText(language_learner);
        IDnumber = (EditText) findViewById(R.id.idNumberL);
        IDnumber.setText(id_number_learner);
        enrolment_date = (EditText) findViewById(R.id.enrollDate);
        enrolment_date.setText(enrolmentdate_learner);
        address = (EditText) findViewById(R.id.addressLe);
        address.setText(address_learner);
        mothername = (EditText) findViewById(R.id.motherLe);
        mothername.setText(mothername_learner);
        fathername = (EditText) findViewById(R.id.fatherL);
        fathername.setText(fathername_learner);
        emailadressmother = (EditText) findViewById(R.id.momEmail);
        emailadressmother.setText(motheraddress_learner);
        emailadressfather = (EditText) findViewById(R.id.dadEmail);
        emailadressfather.setText(fatheraddress_learner);
        cellmother = (EditText) findViewById(R.id.momPhone);
        cellmother.setText(mothercell_learner);
        cellfather = (EditText) findViewById(R.id.dadPhone);
        cellfather.setText(fathercell_learner);
        familydoctor = (EditText) findViewById(R.id.namedoctor);
        familydoctor.setText(doctorname_learner);
        doctorcell = (EditText) findViewById(R.id.doctorPhone);
        doctorcell.setText(doctorcell_learner);
        medicalidname = (EditText) findViewById(R.id.medAidname);
        medicalidname.setText(medicalname_learner);
        medicalpla = (EditText) findViewById(R.id.medAidPlan);
        medicalpla.setText(medicalplan_learner);
        medicalnumber = (EditText) findViewById(R.id.medAidNumber);
        medicalnumber.setText(medicalnumber_learner);
        allergylearner = (EditText) findViewById(R.id.allergies);
        allergylearner.setText(learnerallergy);
        tuchshopbalance = (EditText) findViewById(R.id.balanceTuck);
        tuchshopbalance.setText(learnertuckshop);

    }

    public void setToFalse() {
        cellmother.setEnabled(false);
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
        cellfather.setEnabled(false);
        familydoctor.setEnabled(false);
        doctorcell.setEnabled(false);
        medicalidname.setEnabled(false);
        medicalpla.setEnabled(false);
        medicalnumber.setEnabled(false);
        allergylearner.setEnabled(false);
        tuchshopbalance.setEnabled(false);
        sub.setVisibility(View.GONE);
    }

    public void setEDIT() {

        name.setEnabled(true);
        lastname.setEnabled(true);
        classname.setEnabled(true);
        ;
        race.setEnabled(true);
        days.setEnabled(true);
        gender.setEnabled(true);
        birthdate.setEnabled(true);
        language.setEnabled(true);
        ;
        IDnumber.setEnabled(true);
        enrolment_date.setEnabled(true);
        address.setEnabled(true);
        mothername.setEnabled(true);
        ;
        fathername.setEnabled(true);
        emailadressmother.setEnabled(true);
        emailadressfather.setEnabled(true);
        cellmother.setEnabled(true);
        ;
        cellfather.setEnabled(true);
        familydoctor.setEnabled(true);
        doctorcell.setEnabled(true);
        medicalidname.setEnabled(true);
        ;
        medicalpla.setEnabled(true);
        medicalnumber.setEnabled(true);
        allergylearner.setEnabled(true);
        tuchshopbalance.setEnabled(true);
        sub.setVisibility(View.VISIBLE);
    }

    public void editlear(View view) {
        String namelearner = name.getText().toString().trim();
        String lastnamel = lastname.getText().toString().trim();
        String classnaming = classname.getText().toString().trim();
        String L_race = race.getText().toString().trim();
        String birthdatelear = birthdate.getText().toString().trim();
        String languagelearner = language.getText().toString().trim();
        String idnumberlearner = IDnumber.getText().toString().trim();
        String L_days = days.getText().toString().trim();
        String enrolmentdated = enrolment_date.getText().toString().trim();
        String L_Address = address.getText().toString().trim();
        String L_gender = gender.getText().toString().trim();
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
        if (namelearner.isEmpty() || lastnamel.isEmpty() ||
                classnaming.isEmpty() || L_race.isEmpty() ||
                birthdatelear.isEmpty() || languagelearner.isEmpty() ||
                idnumberlearner.isEmpty() || L_days.isEmpty() ||
                enrolmentdated.isEmpty() || L_Address.isEmpty() ||
                L_gender.isEmpty() || mothernam.isEmpty() ||
                fathernam.isEmpty() || emailmom.isEmpty() ||
                emailfath.isEmpty() || cellmom.isEmpty() ||
                fathercel.isEmpty() || docfam.isEmpty() ||
                doccel.isEmpty() || medinam.isEmpty() ||
                mediplan.isEmpty() || medinum.isEmpty() ||
                allergy.isEmpty() || tuchbalanc.isEmpty())

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Edit_learner.this.finish();
    }

    public void Variables() {
        String learnername = name.getText().toString().trim();
        String lastnamel = lastname.getText().toString().trim();
        String classnaming = classname.getText().toString().trim();
        String L_race = race.getText().toString().trim();
        String birthdatelear = birthdate.getText().toString().trim();
        String languagelearner = language.getText().toString().trim();
        String idnumberlearner = IDnumber.getText().toString().trim();
        String L_days = days.getText().toString().trim();
        String enrolmentdated = enrolment_date.getText().toString().trim();
        String L_Address = address.getText().toString().trim();
        String L_gender = gender.getText().toString().trim();
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
        learnerData.setClassname(classnaming);
        learnerData.setGender(L_gender);
        learnerData.setRace(L_race);
        learnerData.setBirthdate(birthdatelear);
        learnerData.setLanguage(languagelearner);
        learnerData.setId_number(idnumberlearner);
        learnerData.setDays(L_days);
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
        learnerData.setObjectId(objectId);
        learnerData.setMedical_name(medinam);
        learnerData.setMedical_plan(mediplan);
        learnerData.setMedical_number(medinum);
        learnerData.setLearner_allergy(allergy);
        learnerData.setLearner_allergy(allergy);
        learnerData.setLearner_tuchshop_balance(tuchbalanc);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        Backendless.Persistence.save(learnerData, new AsyncCallback<LearnerData>() {
            @Override
            public void handleResponse(LearnerData response) {

                showCustomToast("Successfully Edited!");
                progressDialog.dismiss();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();

            }
        });

    }


}
