package com.example.sechaba.groupl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sechaba.groupl.Classes.ReportClass;
import com.example.sechaba.groupl.Classes.ReportLearner;
import com.example.sechaba.groupl.R;

import java.util.List;

/**
 * Created by DLMLAPTOP on 10/9/2017.
 */

public class AdapterLearnerRe extends ArrayAdapter<ReportLearner> {
    static Context context;
    List<ReportLearner> data;
    private LayoutInflater inflater;
    TextView justin;
    int[] image;

    TextView datare, title, status;

    public AdapterLearnerRe(Context context, List<ReportLearner> objects, int[] image) {
        super(context, R.layout.row_learnereport, objects);
        this.context = context;
        this.data = objects;
        this.image = image;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_learnereport, parent, false);
        title = (TextView) convertView.findViewById(R.id.vtitles);
        ImageView img = (ImageView) convertView.findViewById(R.id.showstat);
        justin = (TextView) convertView.findViewById(R.id.vjustinfor);
        status = (TextView) convertView.findViewById(R.id.statuslearner);
        datare = (TextView) convertView.findViewById(R.id.vdatareport);
        status.setText("Current Status : " + data.get(position).getStatus());
        title.setText("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "Title :" + data.get(position).getTitle());
        justin.setText("Recomendation : " + data.get(position).getJustinfor());

        datare.setText("Data for Report : " + data.get(position).getDatareport());
        if (data.get(position).getStatus().equals("Resolved")) {
            status.setText("\t" + "\t" + "\t" + "\t" + "\t" + "Issue resolved :" + data.get(position).getStatus());
            img.setImageResource(R.drawable.light);
        } else if (data.get(position).getStatus().equals("NotResolved")) {
            status.setText("\t" + "\t" + "\t" + "\t" + "\t" + "Issue resolved :" + data.get(position).getStatus());
            img.setImageResource(R.drawable.offlight);
        }
        return convertView;

    }
}