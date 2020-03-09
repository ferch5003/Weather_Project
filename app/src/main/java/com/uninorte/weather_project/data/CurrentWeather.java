package com.uninorte.weather_project.data;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeather {

    public static Gson g = new Gson();

    public List<Weather> weather;
    public Main main;
    public int id;
    public String name;

    public static CurrentWeather getCurrentWeather(JSONObject response){
        return g.fromJson(response.toString(),CurrentWeather.class);
    }

    public static class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;

        public Weather(){}

        @NonNull
        @Override
        public String toString() {
            return g.toJson(this);
        }
    }

    public static class Main {
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public double pressure;
        public double humidity;

        public Main(){}

        @NonNull
        @Override
        public String toString() {
            return g.toJson(this);
        }
    }
}
