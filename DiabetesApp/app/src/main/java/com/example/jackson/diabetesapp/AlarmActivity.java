package com.example.jackson.diabetesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class AlarmActivity extends ActionBarActivity {
    Context context = this;
    String cancel = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.activity_alarm);

        Intent startIntent = getIntent();



        cancel="1";
        String alarmName = ("Alarm Name");
        String alarmMsg = ("Alarm Message");

        if (startIntent.getStringExtra("alarmName") != null) {
            alarmName = startIntent.getStringExtra("alarmName");

        }
        if (startIntent.getStringExtra("alarmMsg") != null) {

            alarmMsg = startIntent.getStringExtra("alarmMsg");
        }
        if (startIntent.getStringExtra("alarmMsg") != null && startIntent.getStringExtra("alarmName") != null) {

            Log.i("stringsFromIntent", "The intent returned the following strings: "+ "\n"+alarmName+"\n"+alarmMsg);
        }else{

            Log.i("stringsFromIntent","one of the values was null");

        }

        final Button shutup_alarm= (Button)findViewById(R.id.shutup_alarm);

        final TextView tvName = (TextView)findViewById(R.id.textViewAlarmName);
        final TextView tvMsg = (TextView) findViewById(R.id.textViewAlarmMsg);

        tvName.setText(alarmName);
        tvMsg.setText(alarmMsg);
        shutup_alarm.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){

                Intent intent = new Intent(context, AlarmReceiver.class);
                intent.putExtra("cancel",cancel);
                sendBroadcast(intent);





            }
        });




    }


}


