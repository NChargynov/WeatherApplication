package com.geektech.weatherapplication.utils;

import com.geektech.weatherapplication.data.pojo.CurrentWeather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String SUNSET_FORMAT ="HH:mm";
    private static final String DAY_FORMAT = "dd";
    private static final String MONTH_FORMAT = "MMMM";
    private static final String YEAR_FORMAT = "YYYY";
    private static Calendar calendar;
    private static SimpleDateFormat simpleDateFormat;



    public static String parseSunset(Integer time){
        DateFormat dateFormat = new SimpleDateFormat(SUNSET_FORMAT, Locale.getDefault());
        Date date = new Date();
        date.setTime((long)time * 1000);
        return dateFormat.format(date.getTime());
    }


    public static String parseDateDay (CurrentWeather currentWeather){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(DAY_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String parseDateMonth(CurrentWeather currentWeather){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(MONTH_FORMAT, Locale.getDefault());
        return  simpleDateFormat.format(calendar.getTime());
    }

    public static String parseDateYear(CurrentWeather currentWeather){
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(YEAR_FORMAT, Locale.getDefault());
        return  simpleDateFormat.format(calendar.getTime());
    }

}
