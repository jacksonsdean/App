package com.example.jackson.diabetesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;


public class AlarmReceiverActivity extends ActionBarActivity {
    public static Vibrator myVib;

    public void onReceive(Context context, Intent intent) {
        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        myVib.vibrate(95);
        Toast
                .makeText(this, "WORKED!", Toast.LENGTH_LONG)

                .show();
    }
}
