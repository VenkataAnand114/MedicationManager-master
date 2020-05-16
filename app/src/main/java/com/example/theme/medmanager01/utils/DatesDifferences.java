package com.example.theme.medmanager01.utils;

import java.util.Date;


public class DatesDifferences {

    public static long getNumberOfDays(Date date1, Date date2){

        long diff = Math.abs(date1.getTime() - date2.getTime());
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }
}
