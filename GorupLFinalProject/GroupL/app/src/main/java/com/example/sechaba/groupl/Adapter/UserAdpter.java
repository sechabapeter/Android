package com.example.sechaba.groupl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.sechaba.groupl.Classes.CircleTransformation;
import com.example.sechaba.groupl.Classes.UserClass;
import com.example.sechaba.groupl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DLMLAPTOP on 9/12/2017.
 */

public class UserAdpter extends ArrayAdapter<BackendlessUser> {
    static Context context;
    List<BackendlessUser> data;
    private LayoutInflater inflater;
    TextView email, names, roles, lastlogin;


    public UserAdpter(Context context, List<BackendlessUser> objects) {
        super(context, R.layout.users_rowlayout, objects);
        this.context = context;
        this.data = objects;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.users_rowlayout, parent, false);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView9);
        email = (TextView) convertView.findViewById(R.id.usermail);
        names = (TextView) convertView.findViewById(R.id.names);
        roles = (TextView) convertView.findViewById(R.id.roling);
        lastlogin = (TextView) convertView.findViewById(R.id.lastlogin);

        email.setText("Email :" + " " + data.get(position).getEmail());
        names.setText("Name and Surname :" + " " + data.get(position).getProperty("name") + " " + data.get(position).getProperty("surname"));
        roles.setText("Role :" + " " + data.get(position).getProperty("role"));
        lastlogin.setText("LastLogin :" + " " + data.get(position).getProperty("lastLogin"));
        String url = "https://api.backendless.com/F6C15024-B723-6512-FF01-73A1E7B9AD00/BBBBAE79-FB50-7B3C-FF3D-76922F472300/files/Photo/"
                + data.get(position).getProperty("photoLocation");
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.mipmap.my_school)
                .transform(new CircleTransformation())
                .into(img);
        return convertView;


    }
}