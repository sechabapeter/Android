package com.example.sechaba.groupl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sechaba.groupl.Classes.ReportClass;
import com.example.sechaba.groupl.R;

import java.util.List;

/**
 * Created by DLMLAPTOP on 9/14/2017.
 */

public class AdapterforReport extends ArrayAdapter<ReportClass> {
    static Context context;
    List<ReportClass> data;
    private LayoutInflater inflater;
    TextView issues, whos;
    TextView namelear;
    TextView created, updated, report, title;
    int[] image;


    public AdapterforReport(Context context, List<ReportClass> objects, int[] image) {
        super(context, R.layout.row_report, objects);
        this.context = context;
        this.data = objects;
        this.image = image;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_report, parent, false);
        ImageView img = (ImageView) convertView.findViewById(R.id.resol);
        title = (TextView) convertView.findViewById(R.id.title);
        issues = (TextView) convertView.findViewById(R.id.res);
        whos = (TextView) convertView.findViewById(R.id.who);
        namelear = (TextView) convertView.findViewById(R.id.nameslearner);
        created = (TextView) convertView.findViewById(R.id.create);
        updated = (TextView) convertView.findViewById(R.id.updated);
        report = (TextView) convertView.findViewById(R.id.report);
        title.setText("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "Title :" + data.get(position).getProblems());
        namelear.setText(data.get(position).getClases());
        created.setText("Created: " + data.get(position).getCreated());
        whos.setText("This User : " + data.get(position).getEmails() + "   Sent Issue");
        updated.setText("Updated: " + data.get(position).getUpdated());
        report.setText("Report: " + "\n" + data.get(position).getDescription());

        if (data.get(position).getStatus().equals("Unresolved")) {
            issues.setText("\t" + "\t" + "\t" + "\t" + "\t" + "Issue resolved :" + data.get(position).getStatus());
            img.setImageResource(R.drawable.notresolved);
        } else if (data.get(position).getStatus().equals("Resolved")) {
            issues.setText("\t" + "\t" + "\t" + "\t" + "\t" + "Issue resolved :" + data.get(position).getStatus());
            img.setImageResource(R.drawable.resolved);
        }

        return convertView;

    }
}
