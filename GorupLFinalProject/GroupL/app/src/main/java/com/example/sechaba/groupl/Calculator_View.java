package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.sechaba.groupl.Classes.LearnerData;
import com.example.sechaba.groupl.Classes.TuckShopLearner;

import dmax.dialog.SpotsDialog;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class Calculator_View extends AppCompatActivity {
    String name_learner, surname_learner, tuckbalance, classname, obj;
    TextView setAmount, currentBalance;
    TextView currentTransactin, afterTransaction;
    double total, currentTr;
    View toastView;
    TextView TOSTtEXT;
    EditText coins;
    double d, totalafter;
    LearnerData learnerData;
    String myObJECT;
    Toast CustomToast;
    String currentT, afterT;
    TuckShopLearner tuckShopLearner;
    AlertDialog progressDialog;
    Button btn1, btn2, btn3, btn4, btn5, btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator__view);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        getData();
        actionBar.setTitle("Buy for " + name_learner + " " + surname_learner);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        setAmount = (TextView) findViewById(R.id.et_calc_val);
        currentBalance = (TextView) findViewById(R.id.et_current_bal);
        currentTransactin = (TextView) findViewById(R.id.et_cur_transaction);
        afterTransaction = (TextView) findViewById(R.id.et_after);

        btn1 = (Button) findViewById(R.id.btn_10c);
        btn2 = (Button) findViewById(R.id.btn_20c);
        btn3 = (Button) findViewById(R.id.btn_50c);
        btn4 = (Button) findViewById(R.id.btn_r1);
        btn5 = (Button) findViewById(R.id.btn_r2);
        btn6 = (Button) findViewById(R.id.btn_r5);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Calculator_View.this.finish();
    }

    public void getData() {
        name_learner = getIntent().getExtras().getString("name");
        surname_learner = getIntent().getExtras().getString("surname");
        tuckbalance = getIntent().getExtras().getString("amountcurrent");
        classname = getIntent().getExtras().getString("classname");
        myObJECT = getIntent().getExtras().getString("objecId");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.calculate, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.coin:

                // updateBalance();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    public void bn10c(View view) {
        switch (view.getId()) {
            case R.id.btn_10c:
                AddAmount(0.10);
                break;
            case R.id.btn_20c:
                AddAmount(0.20);
                break;
            case R.id.btn_50c:
                AddAmount(0.50);
                break;
            case R.id.btn_r1:
                AddAmount(1.00);
                break;
            case R.id.btn_r2:
                AddAmount(2.00);
                break;
            case R.id.btn_r5:
                AddAmount(5.00);
                break;
        }
    }

    public void AddAmount(double amount) {

        total += amount;
        currentTr += amount;
        d = Double.parseDouble(tuckbalance);
        totalafter = d -= currentTr;
        String totAmount = String.valueOf(Math.round(total * 100.00) / 100.00);
        currentT = String.valueOf(Math.round(currentTr * 100.00) / 100.00);
        afterT = String.valueOf(Math.round(totalafter * 100.00) / 100.00);
        setAmount.setText("R" + totAmount);
        currentBalance.setText("R" + tuckbalance);
        currentTransactin.setText("R" + currentT);
        afterTransaction.setText("R" + afterT);

    }

    private void updateBalance() {
        learnerData = new LearnerData();
        learnerData.setObjectId(myObJECT);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.coins, null);
        coins = (EditText) view.findViewById(R.id.coi);
        String balanceEnter = coins.getText().toString().trim();
//        final double a = Double.parseDouble(balanceEnter);
//        final double b = Double.parseDouble(afterT);
//        double newBal = b + a;
//        String newBalance = String.valueOf(Math.round(newBal*100.00)/100.00);
        learnerData.setLearner_tuchshop_balance(balanceEnter);
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this);
        Dialog.setIcon(R.mipmap.me3);
        Dialog.setTitle("Please  Top Up Learner");
        Dialog.setView(view);
        Dialog.setPositiveButton("AGREE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Backendless.Persistence.save(learnerData, new AsyncCallback<LearnerData>() {
                    @Override
                    public void handleResponse(LearnerData response) {
                        showCustomToast("you have Toped up!");

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        showCustomToast(fault.getMessage());

                    }
                });

            }
        });
        Dialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog.show();


    }

    public void resolvedreport() {
        learnerData = new LearnerData();
        learnerData.setObjectId(myObJECT);
        learnerData.setLearner_tuchshop_balance(afterT);
        Backendless.Persistence.save(learnerData, new AsyncCallback<LearnerData>() {
            @Override
            public void handleResponse(LearnerData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }


    public void Variables() {

        tuckShopLearner = new TuckShopLearner();
        tuckShopLearner.setLeranername(name_learner + " " + surname_learner);
        tuckShopLearner.setLearnerclass(classname);
        tuckShopLearner.setBalancebefore(tuckbalance);
        tuckShopLearner.setBalanceafter(afterT);
        tuckShopLearner.setTransactionamount(currentT);

        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        Backendless.Persistence.save(tuckShopLearner, new AsyncCallback<TuckShopLearner>() {
            @Override
            public void handleResponse(TuckShopLearner response) {
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

    public void resetTransaction(View view) {
        String cleartext = "R0.00";
        setAmount.setText(cleartext);
        currentTransactin.setText("");
        afterTransaction.setText("");
    }


    public void submtTransaction(View view) {
        if (connectinAvailable()) {
            Variables();
            resolvedreport();
            setAmount.setText("");
            currentBalance.setText("");
            currentTransactin.setText("");
            afterTransaction.setText("");

        } else {
            showCustomToast("No internet connection");
        }
    }
}
