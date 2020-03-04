package com.example.sechaba.groupl;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlertDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class MainActivity extends AppCompatActivity {
    Button bLogin, Register, Reset;
    EditText etUsername, etPassword, etMail;
    AlertDialog progressDialog;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("PRE SCHOOL DESPICABLE");
        actionBar.setDisplayUseLogoEnabled(true);

        bLogin = (Button) findViewById(R.id.btn_login);
        Register = (Button) findViewById(R.id.btn_register);
        Reset = (Button) findViewById(R.id.btn_reset);
        etUsername = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

    }


    public void EmergencyAmbulance() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10177"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);

    }

    public void EmergencyPolice() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10111"));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);

 }
    public void ShowEmergency() {
    final CharSequence [] options = {"Police","Ambulance","QUIT"};
    AlertDialog.Builder builder = new  AlertDialog.Builder(this,R.style.Custom);
    builder.setTitle("EMERGENCY AREA");
    builder.setItems(options, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(options[which].equals("Police")){
                EmergencyPolice();
            }
            else if(options[which].equals("Ambulance")){
                EmergencyAmbulance();
            }
            else if(options[which].equals("QUIT")){
                dialog.dismiss();
            }
        }
    });
    builder.show();
    }

    public void Button(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                LoginButton();
                break;
            case R.id.btn_register:

                Intent i = new Intent(this, Registration.class);
                startActivity(i);

                break;

        }//end switch
    }


    public void LoginButton() {
        final String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showCustomToast("Please enter all fields");
        } else {
            progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
            progressDialog.show();
            if (connectinAvailable()) {
                Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {

                        String role = "";
                        String email = "";
                        try {
                            email = Backendless.UserService.CurrentUser().getProperty("email").toString();
                            role = Backendless.UserService.CurrentUser().getProperty("role").toString();
                        } catch (Exception ex) {

                        }
                        if (role.equals("Master") == response.getEmail().equals(email) || role.equals("Admin") == response.getEmail().equals(email)) {
                            showCustomToast(response.getEmail() + " Master/Admin");
                            Intent intent = new Intent(MainActivity.this, Homescreen.class);
                            intent.putExtra("user", response.getEmail());
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);

                        } else if (role.equals("Default") == response.getEmail().equals(email)) {
                            showCustomToast(response.getEmail() + " Default Access Only");
                            Intent intent = new Intent(MainActivity.this, Homescreen.class);
                            intent.putExtra("user", response.getEmail());
                            intent.putExtra("role", role);
                            startActivity(intent);
                            overridePendingTransition(R.anim.left_in, R.anim.right_out);

                        } else {
                            final Dialog dialog = new Dialog(MainActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.notallowed);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.show();

                        }


                        progressDialog.dismiss();

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        progressDialog.dismiss();
                        showCustomToast("Error:" + fault.getMessage());


                    }
                }, true);
            } else {
                showCustomToast("No Internet Connection!!..please connect to Internet or Data");
                progressDialog.dismiss();


            }

        }

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

    public void clicking(View view) {
        ObjectAnimator scaleAnim = ObjectAnimator.ofFloat(Reset, "scaleX", 0.5f, 1.0f);
        scaleAnim.setDuration(3000);
        scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnim.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnim.start();
        if (connectinAvailable()) {
            LayoutInflater inflater = getLayoutInflater();
            final View v = inflater.inflate(R.layout.password_reset, null);
            etMail = (EditText) v.findViewById(R.id.reset_password1);

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Reset Password");
            dialog.setView(v);
            dialog.setIcon(R.drawable.reset);
            dialog.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.progressDialog = new SpotsDialog(MainActivity.this, R.style.Custom);
                    MainActivity.this.progressDialog.setTitle("Busy sending reset instructions....please..wait");
                    progressDialog.show();

                    Backendless.UserService.restorePassword(etMail.getText().toString().trim(), new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            showCustomToast("Reset instructions has been sent to" +
                                    etMail.getText().toString().trim());
                            progressDialog.dismiss();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showCustomToast(fault.getMessage());
                            progressDialog.dismiss();
                        }
                    });

                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialog.show();
        } else {
            showCustomToast("Please connect to the internet");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.whaetherandgoogle, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wheather:
                startActivity(new Intent(this, Weather.class));

                break;
            case R.id.googleMap:
                startActivity(new Intent(this, GoogleMaps.class));
                break;
            case R.id.emergency:
                ShowEmergency();
                break;
            case R.id.webview:
                startActivity(new Intent(this, WebView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.this.finish();
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
