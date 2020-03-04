package com.example.sechaba.groupl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import com.example.sechaba.groupl.Adapter.AdapterforReport;
import com.example.sechaba.groupl.Classes.EmailClass;
import com.example.sechaba.groupl.Classes.ReportClass;
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

import static com.example.sechaba.groupl.Edit_learner.motheraddress_learner;

/*
* Group L Members : 214107930 Tomodi SP
*                 : 212046551 HLAPHO TI
* */
public class MaintenanceReportList extends AppCompatActivity {
    AlertDialog progressDialog;
    ListView viewreport;
    View toastView;
    TextView TOSTtEXT, alert;
    Toast CustomToast;
    ArrayList<ReportClass> LData;
    ReportClass reportClass;
    String role;
    AdapterforReport adapterforReport;
    int imag[] = {R.drawable.redcross, R.drawable.correct};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenancereport_list);
        role = Backendless.UserService.CurrentUser().getProperty("role").toString();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Maintenance Reports");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        viewreport = (ListView) findViewById(R.id.listMain);

        if (connectinAvailable()) {
            reportdata();

        } else {
            showCustomToast("no internet connection");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.maintenace, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.resolving:
                startActivity(new Intent(this, ShowrResolved.class));
                break;
            case R.id.unresolved:
                startActivity(new Intent(this, ShowUnresolvedProblems.class));
                break;
            case R.id.deletemaintenance:
                if (role.equals("Master") || role.equals("Admin")) {
                    delealert();
                } else {
                    showCustomToast("Only The Master User Has a Permission to Delete Reports");
                }
                break;
            case R.id.pdf:
                longpress();
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

    private class DeletetBulkFromBackEnd extends AsyncTask<Void, Void, Exception> {

        @Override
        protected Exception doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            Exception exception = null;


            try {
                URL url = new URL("https://api.backendless.com/F6C15024-B723-6512-FF01-73A1E7B9AD00/BBBBAE79-FB50-7B3C-FF3D-76922F472300/data/bulk/ReportClass");
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


    public void reportdata() {
        progressDialog = new SpotsDialog(this, R.style.Custom);
        progressDialog.show();
        LData = new ArrayList<ReportClass>();
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setPageSize(5).setOffset(0);
        queryBuilder.setSortBy("clases");
        Backendless.Persistence.of(ReportClass.class).find(queryBuilder, new AsyncCallback<List<ReportClass>>() {
            @Override
            public void handleResponse(final List<ReportClass> response) {
                LData.addAll(response);
                AdapterforReport adapterforReport = new AdapterforReport(getApplicationContext(), LData, imag);
                viewreport.setAdapter(adapterforReport);
                progressDialog.dismiss();
                sendData();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showCustomToast(fault.getMessage());
                progressDialog.dismiss();


            }
        });
    }

    public void sendData() {

        viewreport.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ResolveMaintenace.class);
                intent.putExtra("Email", LData.get(position).getEmails());
                intent.putExtra("status", LData.get(position).getStatus());
                intent.putExtra("objectId", LData.get(position).getObjectId());
                startActivity(intent);
                return false;
            }
        });


    }

    public void showPDF() {

        try {


            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MaintenanceReport.pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);


        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "PDF NOT Found!! /n" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    public void longpress() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MaintenanceReportList.this);
        dialog.setTitle("CREATING PDF FOR Miantenance");
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

    public void createPDF()
    {

        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Document document = new Document();

        try {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/MaintenanceReport.pdf");
            PdfWriter.getInstance(document, new FileOutputStream(file, true));
            //open
            document.open();
            document.add(new Chunk(""));

            Font f = new Font();
            f.setStyle(Font.BOLD);
            f.setSize(8);
            Paragraph p = new Paragraph("Maintenance report :"+ date, f);
            p.setAlignment(Element.ALIGN_CENTER);

            Font f1 = new Font();
            f1.setStyle(Font.BOLD);
            f1.setSize(4);
            Paragraph p1 = new Paragraph("", f);
            p1.setAlignment(Element.ALIGN_LEFT);
            //add doc


            PdfPTable table = new PdfPTable(new float[] { 2f, 2f, 2f ,2f, 2f});
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell("Class");
            table.addCell("Date");
            table.addCell("Description");
            table.addCell("Problem");
            table.addCell("Status");
            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j=0;j<cells.length;j++){
                cells[j].setBackgroundColor(BaseColor.GREEN);
            }
            for(int index = 0; index< LData.size(); index++)
            {
                document.add( new Paragraph(LData.get(index).getClases()));
                table.addCell(LData.get(index).getEmails());
                table.addCell(date);
                table.addCell(LData.get(index).getDescription());
                table.addCell(LData.get(index).getProblems());
                table.addCell(LData.get(index).getStatus());
                document.add(table);
                document.add(new Paragraph(""));
            }



            //close
            document.close();
            showCustomToast( "PDF CREATED");


        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            showCustomToast( "PDF NOT CREATED!! /n" + ex.getMessage());

        }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MaintenanceReportList.this.finish();
    }
}