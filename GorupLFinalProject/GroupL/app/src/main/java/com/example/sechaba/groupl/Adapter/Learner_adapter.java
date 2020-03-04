package com.example.sechaba.groupl.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sechaba.groupl.Classes.LearnerData;
import com.example.sechaba.groupl.R;

import java.util.ArrayList;

/**
 * Created by sechaba on 8/15/2017.
 */

public class Learner_adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private final Context context;
    private final ArrayList<LearnerData> learnerDat;

    public Learner_adapter(Context context, ArrayList<LearnerData> list) {
        this.context = context;
        this.learnerDat = list;
    }

    @Override
    public int getCount() {
        return learnerDat.size();
    }

    @Override
    public LearnerData getItem(int position) {
        return learnerDat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.learner_rowlayout, parent, false);
        ImageView img = (ImageView) convertView.findViewById(R.id.image);
        TextView Name = (TextView) convertView.findViewById(R.id.namelis);
        TextView surname = (TextView) convertView.findViewById(R.id.surnamelist);
        TextView idnumber = (TextView) convertView.findViewById(R.id.emaillis);
        Name.setText("Name and LastName:" + "\t" + learnerDat.get(position).getName().toUpperCase() + " " +
                "" + learnerDat.get(position).getLastname().toUpperCase());
        surname.setText("Class Name: " + "\t" + learnerDat.get(position).getClassname());
        idnumber.setText("ID NO: " + "\t" + learnerDat.get(position).getId_number());
        img.setImageResource(R.drawable.myguy);

        return convertView;
    }


}
