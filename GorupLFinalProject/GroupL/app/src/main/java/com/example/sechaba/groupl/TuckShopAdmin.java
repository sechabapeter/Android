package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.MessageStatus;
import com.backendless.messaging.PublishOptions;
import com.backendless.persistence.DataQueryBuilder;
import com.example.sechaba.groupl.Adapter.TuckShopAdapter;
import com.example.sechaba.groupl.Classes.ContactsApplication;
import com.example.sechaba.groupl.Classes.LearnerData;
import com.example.sechaba.groupl.Classes.SMS_SEND;
import com.example.sechaba.groupl.Classes.TuckShopLearner;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class TuckShopAdmin extends AppCompatActivity {
    int imag[] = {R.drawable.redcross};
    ListView lvData;
    AlertDialog progressDialog;
    ArrayList<LearnerData> LData;
    ArrayList<TuckShopLearner> money;
    View toastView;
    TextView alert;
    Date date;
    TuckShopAdapter tuckAdapAdmin;
    TextView TOSTtEXT;
    String role;
    EditText search;
    Toast CustomToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuck_shop_admin);
        search = (EditText) findViewById(R.id.textView3);
        lvData = (ListView) findViewById(R.id.tuck__shop);
        date = ContactsApplication.create(10, 10, 2030, 10, 10, 10);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Tuckshop");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        role = Backendless.UserService.CurrentUser().getProperty("role").toString();

        checkConnection();
        searchLearner();

    }

    public void checkConnection() {
        if (connectinAvailable()) {
            GetDataInBackgroundMine();
            GetData();
        } else {
            showCustomToast("No internet connection");
        }
    }
//
    public void GetDataInBackgroundMine() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<LearnerData>();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(5).setOffset(0);
        queryBuilder.setSortBy("classname");


        Backendless.Persistence.of(LearnerData.class).find(queryBuilder, new AsyncCallback<List<LearnerData>>() {
            @Override
            public void handleResponse(List<LearnerData> response) {
                LData.addAll(response);
                tuckAdapAdmin = new TuckShopAdapter(getApplicationContext(), LData, imag);
                lvData.setAdapter(tuckAdapAdmin);
                progressDialog.dismiss();
                callcalculater();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast("ERROR:" + fault.getMessage());
                progressDialog.dismiss();
            }
        });


    }


    public void GetData() {

        money = new ArrayList<TuckShopLearner>();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(5).setOffset(0);
        queryBuilder.setSortBy("learnerclass");


        Backendless.Persistence.of(TuckShopLearner.class).find(queryBuilder, new AsyncCallback<List<TuckShopLearner>>() {
            @Override
            public void handleResponse(List<TuckShopLearner> response) {
                money.addAll(response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });


    }


    public void searchLearner() {
        lvData.setTextFilterEnabled(true);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                int textlength = cs.length();
                ArrayList<LearnerData> temp = new ArrayList<LearnerData>();
                for (LearnerData c : LData) {
                    if (textlength <= c.getName().length()) {
                        if (c.getName().toLowerCase().contains(cs.toString().toLowerCase())) {
                            temp.add(c);
                        }
                    }
                }
                tuckAdapAdmin = new TuckShopAdapter(getApplicationContext(), temp, imag);
                lvData.setAdapter(tuckAdapAdmin);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void callcalculater() {
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), Calculator_View.class);
                intent.putExtra("name", LData.get(position).getName());
                intent.putExtra("surname", LData.get(position).getLastname());
                intent.putExtra("classname", LData.get(position).getClassname());
                intent.putExtra("objecId", LData.get(position).getObjectId());
                intent.putExtra("amountcurrent", LData.get(position).getLearner_tuchshop_balance());
                startActivity(intent);
            }
        });
        longpress();

    }

    public void notification() {
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

    public void pushingSend() {
        String messages = "New Learner Report" +
                "Report Created" +
                "#2. Master's Permission only";

        PublishOptions options = new PublishOptions();
        options.putHeader("android-ticker-text", "Report!");
        options.putHeader("android-content-title", "New Learner Report");
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

    public void longpress() {
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(TuckShopAdmin.this);
                dialog.setTitle("CREATING PDF FOR TUCKSHOP");
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.pdf);
                dialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createPDF();
                        notification();
                        pushingSend();
                        progressDialog.dismiss();

                    }
                });
                dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressDialog.dismiss();

                    }
                });
                dialog.show();


                return false;
            }
        });
    }

    public void createPDF() {


        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Document document = new Document();

        try {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/TuckShopReport.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file));
            //open
            document.open();

            Font f = new Font();
            f.setStyle(Font.BOLDITALIC);
            f.setSize(8);
            Paragraph p = new Paragraph("TuckShop Report for :" + date, f);
            p.setAlignment(Element.ALIGN_CENTER);

            Font f1 = new Font();
            f1.setStyle(Font.BOLDITALIC);
            f1.setSize(4);
            Paragraph p1 = new Paragraph("TuckShop", f);
            p1.setAlignment(Element.ALIGN_LEFT);
            //add doc

            PdfPTable table = new PdfPTable(new float[]{2f, 2f, 2f, 2f, 2f});
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Class");
            table.addCell("Date of Transaction");
            table.addCell("Balance Before");
            table.addCell("Transaction Amount");
            table.addCell("Balance After");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.YELLOW);
            }
            for (int index = 0; index < money.size(); index++) {
                document.add(new Paragraph(money.get(index).getLeranername()));
                table.addCell(money.get(index).getLearnerclass());
                table.addCell(date);
                table.addCell("R" + money.get(index).getBalancebefore());
                table.addCell("R" + money.get(index).getTransactionamount());
                table.addCell("R" + money.get(index).getBalanceafter());
                document.add(table);
                document.add(new Paragraph(""));
            }


            //close
            document.close();
            showCustomToast("PDF CREATED");


        } catch (Exception ex) {
            ex.printStackTrace();
            showCustomToast("PDF NOT CREATED!! /n" + ex.getMessage());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_tuck__shop, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mail:
                Intent intent = new Intent(TuckShopAdmin.this, SMS_SEND.class);
                startActivity(intent);

                break;
            case R.id.delete:
                if (role.equals("Master") || role.equals("Admin")) {
                    delealert();
                } else {
                    showCustomToast("Only The Master can Delete Transaction");
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TuckShopAdmin.this.finish();
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
                URL url = new URL("https://api.backendless.com/F6C15024-B723-6512-FF01-73A1E7B9AD00/BBBBAE79-FB50-7B3C-FF3D-76922F472300/data/bulk/TuckShopLearner");
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

    public void delealert() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.alert, null);
        alert = (TextView) view.findViewById(R.id.alerting);
        alert.setText("Are you sure you want to delete Learner Account ?" + "\n" + "This cannot be undone!");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account ?");
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

}
