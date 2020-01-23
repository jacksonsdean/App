package com.example.jackson.diabetesapp;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {
    public AlarmService() {
    }

    public void onStartCommand() {


    }


    @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    protected void onCreate(Bundle savedInstanceState) {

        Log.i("alarmService", "Alarm Service Started");

        int[] alarms = new int[7];


        for (int i = 0; i < alarms.length; i++) {
            alarms[i] = 0;
        }

    }

}

