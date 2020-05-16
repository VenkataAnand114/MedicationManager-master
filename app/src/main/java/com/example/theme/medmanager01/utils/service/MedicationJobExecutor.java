package com.example.theme.medmanager01.utils.service;

import android.os.AsyncTask;


public class MedicationJobExecutor extends AsyncTask<Void,Void,String>{

    @Override
    protected String doInBackground(Void... voids) {
        return "running in the background";
    }
}
