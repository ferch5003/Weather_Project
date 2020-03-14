package com.uninorte.weather_project.util;

public class StringUtils {
    public static String formatTemperature(double temp){
        return temp + "°C";
    }

    public static String capitalize(String description){
        return description.substring(0, 1).toUpperCase() + description.substring(1);
    }
}
