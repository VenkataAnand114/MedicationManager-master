package com.example.theme.medmanager01.utils;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.theme.medmanager01.R;

public class MedicationBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Util.scheduleJob(context);
        Notifications.createNotification(context,
                "MED-MANAGER",
                "It's Time !!! (::)<(:)> take ",
                "Quick recovery from med-manager team",
                R.mipmap.ic_launcher);


    }
}
