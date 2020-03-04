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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.sechaba.groupl.Adapter.AdapterLearnerRe;
import com.example.sechaba.groupl.Adapter.AdapterforReport;
import com.example.sechaba.groupl.Classes.ReportClass;
import com.example.sechaba.groupl.Classes.ReportLearner;
import com.itextpdf.text.BaseColor;
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

public class ReportLearnerView extends AppCompatActivity {
    AlertDialog progressDialog;
    ListView lvData;
    View toastView;
    String role;
    TextView TOSTtEXT;
    Toast CustomToast;
    ArrayList<ReportLearner> LData;
    TextView alert;
    int imag[] = {R.drawable.redcross, R.drawable.correct};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_learner_view);
        try {

            role = Backendless.UserService.CurrentUser().getProperty("role").toString();
        } catch (Exception ex) {

        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Learner Report Created");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        lvData = (ListView) findViewById(R.id.reportlearnerList);

        if (connectinAvailable()) {
            reportdata();
        } else {
            showCustomToast("no internet connection");
        }

    }

    public void setToResolve() {
        lvData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (role.equals("Master") || role.equals("Admin")) {

                    Intent intent = new Intent(getApplicationContext(), ResolvedLearnerReport.class);
                    intent.putExtra("statusL", LData.get(position).getStatus());
                    intent.putExtra("title", LData.get(position).getTitle());
                    intent.putExtra("objectId", LData.get(position).getObjectId());
                    startActivity(intent);
                } else {
                    showCustomToast("Only The Master/Admin User can do changes!!!");
                }

                return false;
            }
        });
    }


    public void longpress() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReportLearnerView.this);
        dialog.setTitle("CREATING PDF FOR Reported Learner");
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.pdf);
        dialog.setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createPDF();
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
    }

    public void createPDF() {

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Document document = new Document();

        try {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/ReportLearnerClass.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file, true));
            //open
            document.open();

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);
            Paragraph p = new Paragraph("Reported Learner :" + date, f);
            p.setAlignment(Element.ALIGN_CENTER);

            Font f1 = new Font();
            f1.setStyle(Font.BOLD);
            f1.setSize(4);
            Paragraph p1 = new Paragraph("ReportLearnerClass", f);
            p1.setAlignment(Element.ALIGN_LEFT);
            //add doc


            PdfPTable table = new PdfPTable(new float[]{2f, 2f, 2f, 2f, 2f});
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Title");
            table.addCell("Date");
            table.addCell("Just Information");
            table.addCell("Data Report");
            table.addCell("Status");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.YELLOW);
            }
            for (int index = 0; index < LData.size(); index++) {
                document.add(new Paragraph(LData.get(index).getTitle()));
                table.addCell(LData.get(index).getTitle());
                table.addCell(date);
                table.addCell(LData.get(index).getJustinfor());
                table.addCell(LData.get(index).getDatareport());
                table.addCell(LData.get(index).getStatus());
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

    public void reportdata() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<ReportLearner>();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(5).setOffset(0);
        queryBuilder.setSortBy("title");

        Backendless.Persistence.of(ReportLearner.class).find(queryBuilder, new AsyncCallback<List<ReportLearner>>() {
            @Override
            public void handleResponse(List<ReportLearner> response) {
                LData.addAll(response);
                AdapterLearnerRe adapterforReport = new AdapterLearnerRe(getApplicationContext(), LData, imag);
                lvData.setAdapter(adapterforReport);
                progressDialog.dismiss();
                setToResolve();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();
            }
        });
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
        ReportLearnerView.this.finish();
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

        getMenuInflater().inflate(R.menu.pdflearner, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pdflearner:
                longpress();
                break;
            case R.id.showreolvedL:
                startActivity(new Intent(this, ShowResoledLearner.class));
                break;
            case R.id.showunreolvedL:
                startActivity(new Intent(this, ShowUnResoledLearner.class));
                break;
            case R.id.deleteall:
                if (role.equals("Master") || role.equals("Admin")) {
                    delealert();
                } else {
                    showCustomToast("Only The User Has a Permission to Delete Reports");
                }
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void delealert() {
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.alert, null);
        alert = (TextView) view.findViewById(R.id.alerting);
        alert.setText("Are you sure you want to delete the list of Reports ? ?" + "\n" + "This cannot be undone!");

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


}