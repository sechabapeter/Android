package com.example.sechaba.groupl.Classes;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sechaba.groupl.R;

public class SMS_SEND extends AppCompatActivity {
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS DELIVERED";
    PendingIntent sentPI, deliveryPI;
    BroadcastReceiver smsSentReciever, smsDeliveryReciever;
    int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    EditText etMessage, etNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms__send);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Send Learner Sms");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        etMessage = (EditText) findViewById(R.id.message);
        etNumber = (EditText) findViewById(R.id.etNumber);

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        deliveryPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

    }

    public void btn_SendSMS_OnClick(View v) {
        String message = etMessage.getText().toString();
        String number = etNumber.getText().toString();


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number, null, message, sentPI, deliveryPI);
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
                        Toast.makeText(SMS_SEND.this, "SMS sent!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(SMS_SEND.this, "Generic Failure!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(SMS_SEND.this, "No Service!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(SMS_SEND.this, "Null PDU!", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(SMS_SEND.this, "Radio Off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        smsDeliveryReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(SMS_SEND.this, "SMS delivered!", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(SMS_SEND.this, "SMS Not Delivered!", Toast.LENGTH_SHORT).show();
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
