package com.example.sechaba.groupl.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sechaba.groupl.Classes.LearnerData;
import com.example.sechaba.groupl.R;

import java.util.List;

/**
 * Created by DLMLAPTOP on 9/11/2017.
 */

public class TuckShopAdapter extends ArrayAdapter<LearnerData> {
    static Context context;
    List<LearnerData> data;
    private LayoutInflater inflater;
    TextView Surname;
    TextView balance;
    TextView seaBuddies;
    int[] image;

    public TuckShopAdapter(Context context, List<LearnerData> objects, int[] image) {
        super(context, R.layout.tuchshop_rowlay, objects);
        this.context = context;
        this.data = objects;
        this.image = image;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.tuchshop_rowlay, parent, false);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageViewcross);
        Surname = (TextView) convertView.findViewById(R.id.surname1);
        balance = (TextView) convertView.findViewById(R.id.year1);
        seaBuddies = (TextView) convertView.findViewById(R.id.student1);
        seaBuddies.setText(data.get(position).getClassname());
        Surname.setText(data.get(position).getLastname() + " " + data.get(position).getName());
        if (data.get(position).getLearner_tuchshop_balance().equals("0.0")) {
            balance.setText("Balance :" + "\n" + "R" + data.get(position).getLearner_tuchshop_balance());
            balance.setTextColor(Color.RED);
            img.setImageResource(R.drawable.wrong);

        } else if (data.get(position).getLearner_tuchshop_balance().equals(null)) {
            balance.setText("0.0");

        } else if (!data.get(position).getLearner_tuchshop_balance().equals("0.0")) {
            balance.setText("Balance :" + "\n" + "R" + data.get(position).getLearner_tuchshop_balance());
            img.setImageResource(R.drawable.yesco);
            balance.setTextColor(Color.CYAN);
        }
        return convertView;


    }
}