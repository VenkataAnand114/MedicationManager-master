package com.example.theme.medmanager01.utils.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.theme.medmanager01.utils.MedicationBroadCastReceiver;
import com.example.theme.medmanager01.utils.Util;


public class medManagerJobService extends JobService {

    MedicationJobExecutor medicationJobExecutor;

    @Override
    public boolean onStartJob(JobParameters params) {
    medicationJobExecutor = new MedicationJobExecutor(){
        @Override
        protected void onPostExecute(String s) {
        //    Toast.makeText(getApplicationContext(), "fdfdsdsds"+s, Toast.LENGTH_LONG).show();
            jobFinished(params,false);
        }
    };
    medicationJobExecutor.execute();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        medicationJobExecutor.cancel(true);
        return false;
    }
}
