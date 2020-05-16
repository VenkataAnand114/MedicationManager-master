package com.example.theme.medmanager01.utils;

import java.util.Calendar;

public class Years {

    public static int getPreviousYear(){
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR,-1);

        return prevYear.get(Calendar.YEAR);
    }

    public static int getNextYear(){
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,+1);

        return nextYear.get(Calendar.YEAR);
    }
}
