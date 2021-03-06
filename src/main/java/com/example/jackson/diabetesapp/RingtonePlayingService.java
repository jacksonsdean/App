package com.example.jackson.diabetesapp;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Jackson on 4/14/2015.
 */
public class RingtonePlayingService extends Service
{
    private Ringtone ringtone;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
       ringtone = RingtoneManager.getRingtone(this, notification);
        ringtone.play();

        Log.i("debugRingtone", "Ringtone Service started");
        //this.ringtone = RingtoneManager.getRingtone(this, notification);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {

        ringtone.stop();
        Log.i("debugRingtone", "Ringtone Service destroyed");
    }
}
