package com.example.jackson.diabetesapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    public static Vibrator myVib;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onReceive(Context context, Intent intent) {


        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        String alarmName = intent.getStringExtra("alarmName");
        String alarmMsg = intent.getStringExtra("alarmMsg");
        String cancel = intent.getStringExtra("cancel");

        Log.i("alarmCancel", "" + cancel);

        if (cancel != null) {
            Log.i("alarmCancel", "Inside if statement: " + cancel);
            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(119);
            mNotificationManager.cancelAll();
/*
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.stop();
  */
            Intent stopIntent = new Intent(context, RingtonePlayingService.class);
            context.stopService(stopIntent);


        } else {
            Log.i("alarmCancel", "Inside else statement: " + cancel);

            myVib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


            myVib.vibrate(1000);

/*

*/

            Intent startRIntent = new Intent(context, RingtonePlayingService.class);

            context.startService(startRIntent);


            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(alarmName)
                            .setContentText(alarmMsg);

            Intent resultIntent = new Intent(context, AlarmActivity.class);
            resultIntent.putExtra("alarmName", alarmName);
            resultIntent.putExtra("alarmMsg", alarmMsg);


            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

            stackBuilder.addParentStack(AlarmActivity.class);

            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            // Uses the default lighting scheme


            // Will show lights and make the notification disappear when the presses it


            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification note = mBuilder.build();
            note.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_SHOW_LIGHTS;
            note.defaults |= Notification.DEFAULT_LIGHTS;

            mNotificationManager.notify("", 119, mBuilder.build());


        }

        wl.release();
    }

}