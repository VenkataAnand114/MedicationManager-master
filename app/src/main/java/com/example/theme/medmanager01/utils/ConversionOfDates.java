package com.example.theme.medmanager01.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversionOfDates {
    

    public static Date getDateFromString(String date){
        Date new_date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E,MMM dd yyyy");
        try {
            new_date = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new_date;
    }

    /**
     * this method convert date to string
     * @param date
     * @return
     */

    public static String formatDate(Date date){
        String formatted_date;
        SimpleDateFormat dateFormat = new SimpleDateFormat("E,MMM dd yyyy");

        formatted_date = dateFormat.format(date);

        return formatted_date;
    }
}
