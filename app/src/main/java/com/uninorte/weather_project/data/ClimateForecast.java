package com.uninorte.weather_project.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClimateForecast {

    public static Gson g = new Gson();

    public CurrentWeather.Weather weather;
    public CurrentWeather.Main main;

    public static ArrayList<ClimateForecast> getClimateForecast(JSONObject response){
        ArrayList<ClimateForecast> list = new ArrayList<>();
        try {
            JSONArray info = response.getJSONArray("list");
            for(int i = 0; i < info.length(); i++){
                String weatherList = info.getJSONObject(i).toString();
                ClimateForecast temp = g.fromJson(weatherList, ClimateForecast.class);
                list.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static class List {
        public Temp temp;
        public int dt;
        public float humidity;
        public float pressure;
        public float wind_speed;

        public List(){}

        @NonNull
        @Override
        public String toString() {
            return g.toJson(this);
        }
    }

    public static class Temp {
        public float average;
        public float average_max;
        public float average_min;
        public float record_max;
        public float record_min;
        public float humidity;

        public Temp(){}

        @NonNull
        @Override
        public String toString() {
            return g.toJson(this);
        }
    }
}
