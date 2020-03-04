package com.example.sechaba.groupl;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


public class Weather extends AppCompatActivity {
    TextView textTemp,textCondition,textLocation,textDate;
    View toastView;
    TextView TOSTtEXT;
    Toast CustomToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.me3);
        actionBar.setTitle("Wheather");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        try {
            textTemp = (TextView) findViewById(R.id.textTemp);
            textCondition = (TextView) findViewById(R.id.textCondition);
            textLocation = (TextView) findViewById(R.id.textLocation);
            textDate = (TextView) findViewById(R.id.textDate);
            getWeather();
        }catch (Exception e)
        {
           showCustomToast(e.getMessage());
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

    private void getWeather() {
        String URL="http://api.openweathermap.org/data/2.5/weather?q=Bloemfontein,rsa&appid=e2decc62366382812bc7ca7046c22ea9";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object=response.getJSONObject("main");
                    JSONArray array=response.getJSONArray("weather");
                    JSONObject object=array.getJSONObject(0);
                    String temp=String.valueOf(main_object.getDouble("temp"));
                    String description=object.getString("description");
                    String city=response.getString("name");

                    textLocation.setText(city);
                    textCondition.setText(description);
                    double temp_convert=Double.parseDouble(temp);
                    double centi_convert=(temp_convert-32)*5/9-125;

                    centi_convert=Math.round(centi_convert);
                    textTemp.setText(String.valueOf((int)centi_convert)+"°C");
                    Date date=(Date) Calendar.getInstance().getTime();
                    String dt=date.toLocaleString();
                    textDate.setText(""+dt);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }


}
