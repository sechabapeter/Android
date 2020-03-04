package com.example.sechaba.groupl.Classes;

import android.app.Application;

import com.backendless.Backendless;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sechaba on 8/11/2017.
 */

public class ContactsApplication extends Application {

    public static  final String APPLICATION_ID ="F6C15024-B723-6512-FF01-73A1E7B9AD00";
    public  static  final  String API_KEY="721C1267-D757-D81F-FF93-52A3E4E04900";
    public static  final  String SERVER_URL = "https://api.backendless.com";


    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),APPLICATION_ID,API_KEY);


    }

    public static Date create(int day, int month, int year, int hourofday, int minute, int second) {
        if (day == 0 && month == 0 && year == 0) return null;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month -1, day, hourofday, minute, second);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
