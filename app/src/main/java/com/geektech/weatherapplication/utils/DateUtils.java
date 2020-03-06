package com.geektech.weatherapplication.utils;

import com.geektech.weatherapplication.data.pojo.CurrentWeather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String SUNSET_FORMAT ="HH:mm";


    public static String parseSunset(Integer time){
        DateFormat dateFormat = new SimpleDateFormat(SUNSET_FORMAT, Locale.getDefault());
        Date date = new Date();
        date.setTime((long)time * 1000);
        return dateFormat.format(date.getTime());
    }

}
