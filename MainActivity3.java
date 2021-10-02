package com.example.wasabitechnology2021;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity3 extends AppCompatActivity {
    EditText etCity;
    TextView txt2,tvResult;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "e53301e27efa0b66d05045d91b2742d3";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        txt2 = (TextView) findViewById(R.id.txtview1);
        txt2.setTextColor(Color.WHITE);

        etCity = findViewById(R.id.etCity);
        tvResult = findViewById(R.id.tvResult);

    }

    public void getWeatherDetails(View view){
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        if(city.equals("")){
            tvResult.setText("City field can not be empty!");
        }else {
            tempUrl = url + "?q=" + city + "&appid=" + appid;
        }
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        if(city.equals("")){
            tvResult.setText("City field can not be empty!");
        }else {
            tempUrl = url + "?q=" + city + "&appid=" + appid;
        }

        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>(){        @Override
        public void onResponse(String response){
            String output = "";
            try {
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                String description = jsonObjectWeather.getString("description");
                JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                double temp = jsonObjectMain.getDouble("temp") - 273.15;
                double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                float pressure = jsonObjectMain.getInt("pressure");
                int humidity = jsonObjectMain.getInt("humidity");
                JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                String wind = jsonObjectWind.getString("speed");
                JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                String clouds = jsonObjectClouds.getString("all");
                JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                String countryName = jsonObjectSys.getString("country");
                String cityName = jsonResponse.getString("name");
                tvResult.setTextColor(Color.rgb(68, 134, 199));
                output += "Current weather of " + cityName + " (" + countryName + ")"
                        + "\n Temp: " + df.format(temp) + " °C"
                        + "\n Feels Like: " + df.format(feelsLike) + " °C"
                        + "\n Humidity: " + humidity + "%"
                        + "\n Description: " + description
                        + "\n Wind Speed: " + wind + "m/s (meters per second)"
                        + "\n Cloudiness: " + clouds + "%"
                        + "\n Pressure: " + pressure + " hPa";
                tvResult.setText(output);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
