package com.uninorte.weather_project.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {

    public static String formatTemperature(double temp){
        return Math.round(temp)+ "°C";
    }

    public static String formatHumidity(int humidity){
        return humidity + "%";
    }

    public static String formatDate(long posix){
        SimpleDateFormat d = new SimpleDateFormat("dd 'de' MMMM", new Locale("es","CO"));
        Date converter = new Date(posix * 1000);
        return d.format(converter);
    }

    public static String formatTime(long posix){
        SimpleDateFormat t = new SimpleDateFormat("hh:mm a");
        Date converter = new Date(posix * 1000);
        return t.format(converter);
    }

    public static String capitalize(String description){
        return description.substring(0, 1).toUpperCase() + description.substring(1);
    }
}
