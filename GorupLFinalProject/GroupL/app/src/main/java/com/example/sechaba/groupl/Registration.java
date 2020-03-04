package com.example.sechaba.groupl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.example.sechaba.groupl.Classes.ContactsApplication;
import com.example.sechaba.groupl.Classes.UserClass;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Registration extends AppCompatActivity {
    ImageView iv_wordImage;
    Date date;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    UserClass userClass;
    private Uri imageFile;
    private static final int IMAGE_CAPTURE = 1;
    private static final int IMAGE_PICK = 2;
    private static final int TYPE_PICTURE = 3;
    private static final String DIRECTORY = "Photo";
    EditText etName, etSurname, etEmail, etPassword, etRepassword;
    Button reg, upload;
    ImageView UserPic;
    AlertDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("REGISTER USER");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        date = ContactsApplication.create(10, 10, 2030, 10, 10, 10);
        UserPic = (ImageView) findViewById(R.id.userpic);
        reg = (Button) findViewById(R.id.BTN_register);
        etName = (EditText) findViewById(R.id.et_name);
        etSurname = (EditText) findViewById(R.id.et_surname);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRepassword = (EditText) findViewById(R.id.re_password);
        iv_wordImage = (ImageView) findViewById(R.id.userpic);
        upload = (Button) findViewById(R.id.uplodpic);

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

    public void createUser() {
        final String role = "None";
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String re_password = etRepassword.getText().toString().trim();


        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty() || re_password.isEmpty() || UserPic.getDrawable() == null) {
            showCustomToast("Please Enter all fields!");
            etName.setText("");
            etSurname.setText("");
            etEmail.setText("");
            etPassword.setText("");
            etRepassword.setText("");
        } else if (!password.equals(re_password)) {
            showCustomToast("Please make sure your passwords match!");
        } else {
            final String imageFileName = sendPicture(imageFile);
            userClass = new UserClass();
            userClass.setName(name);
            userClass.setSurname(surname);
            userClass.setEmail(email);
            userClass.setPassword(password);
            userClass.setRole(role);
            userClass.setPhotoLocation(imageFileName);


            if (connectinAvailable()) {


                BackendlessUser user = new BackendlessUser();
                user.setProperty("email", userClass.getEmail());
                user.setProperty("surname", userClass.getSurname());
                user.setProperty("name", userClass.getName());
                user.setProperty("password", userClass.getPassword());
                user.setProperty("role", userClass.getRole());
                user.setProperty("photoLocation", userClass.getPhotoLocation());
                progressDialog = new SpotsDialog(this, R.style.Custom);
                progressDialog.show();
                Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        showCustomToast("User Registered successfully  and Role is :None");
                        progressDialog.dismiss();
                        Registration.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        showCustomToast("Error" + fault.getMessage());
                        progressDialog.dismiss();
                    }
                });


            } else {

                showCustomToast("No Internet Connection!Please connect to mobile data or WI-FI ");
            }

        }
    }

    private String sendPicture(Uri imageUri) {

        String imageFileName = null;
        try {
            if (imageUri != null) {
                final Bitmap pic = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                imageFileName = timeStamp + ".jpg";

                Backendless.Files.Android.upload(pic, Bitmap.CompressFormat.JPEG, 100, imageFileName, DIRECTORY, true, new AsyncCallback<BackendlessFile>() {
                    @Override
                    public void handleResponse(BackendlessFile backendlessFile) {
                        showCustomToast("ERROR : " + backendlessFile);
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        showCustomToast("Error : " + backendlessFault.getMessage());
                    }
                });
            } else {
                BackendlessUser user = Backendless.UserService.CurrentUser();
                imageFileName = user.getProperty("photoLocation").toString();
            }
        } catch (Exception e) {
            showCustomToast("ERROR : " + e.getMessage());
        }//end catch

        return imageFileName;

    }

    public void Register(View view) {
        String role = "";
        switch (view.getId()) {
            case R.id.BTN_register:

                createUser();
                SetChannel();
                SendNotification();

                break;
        }
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

    public void SendNotification() {
        String message = "New User Registration" +
                "Email Address:" + userClass.getEmail();

        PublishOptions options = new PublishOptions();
        options.putHeader("android-ticker-text", "Roles");
        options.putHeader("android-content-title", "New User Registration");
        options.putHeader("android-content-text", "Assign User Role");

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

    //select picture from mobile storage
    @Nullable
    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORY);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(DIRECTORY, "Failed to create the \"" + DIRECTORY + "\" directory");
                return null;
            }
        }
        //store file name...renews pic name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == TYPE_PICTURE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }
        return mediaFile;
    }//end method

    //
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }//end method

    private void takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageFile = getOutputMediaFileUri(TYPE_PICTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFile);
            startActivityForResult(intent, IMAGE_CAPTURE);
        } catch (Exception ex) {
            showCustomToast("Error : " + ex.getMessage());
        }
    }//end method

    //Called when an activity you launched exits, giving you the requestCode you started it with, the resultCode it returned, and any additional data from it. The resultCode will be RESULT_CANCELED if the activity explicitly returned that, didn't return any result, or crashed during its operation.
//You will receive this call immediately before onResume() when your activity is re-starting.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case IMAGE_CAPTURE: {
                if (resultCode == RESULT_OK) {
                    iv_wordImage.setImageURI(imageFile);
                } else if (resultCode == RESULT_CANCELED) {
                    showCustomToast("Image capture cancelled!");
                }
                break;
            }
            case IMAGE_PICK: {
                if (resultCode == RESULT_OK) {
                    imageFile = data.getData();
                    iv_wordImage.setImageURI(imageFile);
                } else {
                    showCustomToast("Image pick cancelled!");
                }
                break;
            }
            default: {
                showCustomToast("Image not captured!!");
                break;
            }
        }
    }//end method

    //Called to retrieve per-instance state from an activity before being killed so that the state can be restored in onCreate
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("image_uri", imageFile);
    }//end method

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageFile = savedInstanceState.getParcelable("image_uri");
    }//end method

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
        startActivity(new Intent(Registration.this, MainActivity.class));
        Registration.this.finish();
        overridePendingTransition(R.anim.right_out, R.anim.left_in);
    }

    public void clicks(View view) {

        final CharSequence[] options = {"Capture Image", "Choose", "QUIT"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Custom);
        builder.setTitle("Image Options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Capture Image")) {
                    takePhoto();
                } else if (options[which].equals("Choose")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK);
                } else if (options[which].equals("QUIT")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }
}

