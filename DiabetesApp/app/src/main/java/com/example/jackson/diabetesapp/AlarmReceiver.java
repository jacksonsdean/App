package com.example.jackson.diabetesapp;

import android.annotation.TargetApi;
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
import android.view.WindowManager;


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

        Log.i("alarmCancel",""+cancel);

        if (cancel != null) {
            Log.i("alarmCancel","Inside if statement: "+cancel);
            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(resultIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.cancel(119);
/*
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone r = RingtoneManager.getRingtone(context, notification);
            r.stop();
  */
            Intent stopIntent = new Intent(context, RingtonePlayingService.class);
            context.stopService(stopIntent);


        } else {
            Log.i("alarmCancel","Inside else statement: "+cancel);

            myVib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);


            myVib.vibrate(115);

/*

*/

            Intent startRIntent = new Intent(context, RingtonePlayingService.class);

            context.startService(startRIntent);



            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.button_background_orange)
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
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify("", 119, mBuilder.build());





        }

        wl.release();
    }

}