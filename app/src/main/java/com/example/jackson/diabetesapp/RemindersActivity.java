package com.example.jackson.diabetesapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;


public class RemindersActivity extends ActionBarActivity  {
    public static Vibrator myVib;
    int day_alarm = 0;
    int hour_alarm = 0;
    int minute_alarm = 0;

    public int RQ = 0;



    public String alarmName1,alarmName2,alarmName3,alarmName4,alarmName5,alarmName6,alarmName7;

    public String alarmMsg1,alarmMsg2,alarmMsg3,alarmMsg4,alarmMsg5,alarmMsg6,alarmMsg7;




    Context context = this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

    //Intent serviceIntent=new Intent("com.example.jackson.diabetesapp.AlarmService");
    //this.startService(serviceIntent);






        RQ=0;
        alarmName1 = alarmName2 = alarmName3 = alarmName4 = alarmName5 = alarmName6 = alarmName7 = "Alarm";
        alarmMsg1 = alarmMsg2 = alarmMsg3 = alarmMsg4 = alarmMsg5 = alarmMsg6 = alarmMsg7 = "Hey you!";
        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("remindersList");
        tabSpec.setContent(R.id.tabRemindersList);
        tabSpec.setIndicator("Reminders");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("remindersNew");
        tabSpec.setContent(R.id.tabRemindersNew);
        tabSpec.setIndicator("+");
        tabHost.addTab(tabSpec);

        String[] nums = new String[31];
        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i);
        NumberPicker np = (NumberPicker) findViewById(R.id.npr);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        np.setMaxValue(30);
        np.setMinValue(0);
        np.setValue(0);
        np.setWrapSelectorWheel(true);
        np.setDisplayedValues(nums);




        File f = new File("/data/data/com.example.jackson.diabetesapp/AlarmData");
        if(!f.exists())  {

            String[] alarms =  new String[7];



            for(int i=0; i<alarms.length; i++) {
                alarms[i] = "0";
            }
            try {
                writeJSONAlarm(alarms);
            } catch (IOException e) {
                e.printStackTrace();
            }





        }


String[] alarmsStringArray = recallEntryJsonAlarm();


        final String[] alarmsFinal = alarmsStringArray;

        final Button cancelAlarmsButton= (Button)findViewById(R.id.cancel_alarms_button);
        final Button editAlarmTextButton= (Button)findViewById(R.id.rename_alarm_button);
        final Button createAlarmsButton= (Button)findViewById(R.id.create_alarms_button);


        final TextView alarmtv1 = (TextView) findViewById(R.id.alarmtv1);
        final TextView alarmtv2 = (TextView) findViewById(R.id.alarmtv2);
        final TextView alarmtv3 = (TextView) findViewById(R.id.alarmtv3);
        final TextView alarmtv4 = (TextView) findViewById(R.id.alarmtv4);
        final TextView alarmtv5 = (TextView) findViewById(R.id.alarmtv5);
        final TextView alarmtv6 = (TextView) findViewById(R.id.alarmtv6);
        final TextView alarmtv7 = (TextView) findViewById(R.id.alarmtv7);








        alarmtv1.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
            {
                RQ = 1;
                createAlarm(RQ, alarmsFinal);

            }
        });


        alarmtv2.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
            {
                RQ = 2;
                createAlarm(RQ, alarmsFinal);

            }
        });








        cancelAlarmsButton.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
            {
                cancelAlarm(RQ);
            }
        });

        editAlarmTextButton.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
            {
                editAlarmText(RQ);
            }
        });

        createAlarmsButton.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
            {

createAlarm(RQ, alarmsFinal);

            }
        });






    }

public void editAlarmText(int RQ){
final  int currentRQ = RQ;
    AlertDialog.Builder alert = new AlertDialog.Builder(this);

    //alert.setTitle("Title");
    //alert.setMessage("Message");

// Set an EditText view to get user input
    final EditText inputName = new EditText(this);

    final EditText inputMsg = new EditText(this);

    final TextView tvInputName = new TextView(this);
    final TextView tvInputMsg = new TextView(this);


    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    layoutParams.setMargins(30, 20, 30, 0);

        tvInputName.setTextSize(22);
        tvInputMsg.setTextSize(22);
        tvInputMsg.setTextSize(22);
        tvInputName.setText("Alarm Name:");
        tvInputMsg.setText("Alarm Message:");

        inputName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    inputMsg.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(tvInputName, layoutParams);
        ll.addView(inputName, layoutParams);
        ll.addView(tvInputMsg, layoutParams);
        ll.addView(inputMsg, layoutParams);


        alert.setView(ll);


    Log.i("RQ_CODE", "RQ in editAlarmText() is: "+ RQ);
    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {


            if (inputName.getText().toString() != "") {



            switch (currentRQ) {
                case 1:   alarmName1 = inputName.getText().toString();
                    break;
                case 2:   alarmName2 = inputName.getText().toString();
                    break;
                case 3:    alarmName3 = inputName.getText().toString();
                    break;
                case 4:    alarmName4 = inputName.getText().toString();
                    break;
                case 5:    alarmName5 = inputName.getText().toString();
                    break;
                case 6:   alarmName6 = inputName.getText().toString();
                    break;
                case 7:   alarmName7 = inputName.getText().toString();
                    break;

                default:   Log.e("DEBUG", "NAMING FAILED");
                    break;
            }


            }


            if (inputMsg.getText().toString() != "") {
                switch (currentRQ) {
                    case 1:   alarmMsg1 = inputMsg.getText().toString();
                        break;
                    case 2:   alarmMsg2 = inputMsg.getText().toString();
                        break;
                    case 3:    alarmMsg3 = inputMsg.getText().toString();
                        break;
                    case 4:    alarmMsg4 = inputMsg.getText().toString();
                        break;
                    case 5:    alarmMsg5 = inputMsg.getText().toString();
                        break;
                    case 6:   alarmMsg6 = inputMsg.getText().toString();
                        break;
                    case 7:   alarmMsg7 = inputMsg.getText().toString();
                        break;

                    default:   Log.e("DEBUG", "NAMING FAILED");
                        break;
                }

            }





        }
    });

    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            // Canceled.
        }
    });

    alert.show();
}










    public void  createAlarm(int RQ, String[] alarms) {
        Log.i("RQ_CODE", "RQ in createAlarm() is: "+ RQ);

        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setCurrentTab(1);


        NumberPicker np = (NumberPicker) findViewById(R.id.npr);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);


        day_alarm = np.getValue();
        hour_alarm = timePicker.getCurrentHour();
        minute_alarm = timePicker.getCurrentMinute();

        Calendar cal_now = Calendar.getInstance();
        Calendar cal_alarm = Calendar.getInstance();
        Date dat = new Date();
        cal_now.setTime(dat);
        cal_alarm.setTime(dat);

        cal_alarm.set(Calendar.HOUR_OF_DAY, hour_alarm);
        cal_alarm.set(Calendar.MINUTE, minute_alarm);

        if (cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }

        final Date cal_alarm_final = cal_alarm.getTime();


        final long alarm_time_ms = cal_alarm.getTimeInMillis();


        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);


        if (RQ == 0) {
            for (int n = 0; n < alarms.length; n++) {
                Log.i("RQ_CODE", "Array Values: " + alarms[n]);

                if (alarms[n] == "0") {
                    RQ = Integer.parseInt(alarms[n]);
                    Log.i("RQ_CODE", "RQ in FOR " + RQ);
                    final int currentRQ = RQ;
                    setAlarm(am, i, currentRQ, cal_alarm_final, alarm_time_ms);
                    break;


                } else {
                    Toast
                            .makeText(this, "Delete some first!", Toast.LENGTH_LONG)

                            .show();


                }
            }
        }
    }

public void setAlarm(AlarmManager am, Intent i, int currentRQ,Date cal_alarm_final, Long alarm_time_ms ) {
           Log.i("RQ_CODE", "RQ in setAlarm() is: "+ RQ);
        switch (currentRQ) {
            case 1:
                i.putExtra("alarmName", alarmName1);
                break;
            case 2:
                i.putExtra("alarmName", alarmName2);
                break;
            case 3:
                i.putExtra("alarmName", alarmName3);
                break;
            case 4:
                i.putExtra("alarmName", alarmName4);
                break;
            case 5:
                i.putExtra("alarmName", alarmName5);
                break;
            case 6:
                i.putExtra("alarmName", alarmName6);
                break;
            case 7:
                i.putExtra("alarmName", alarmName7);
                break;

            default:
                Log.e("DEBUG", "PUTTING NAME FAILED");
                break;
        }

        switch (currentRQ) {
            case 1:
                i.putExtra("alarmMsg", alarmMsg1);
                break;
            case 2:
                i.putExtra("alarmMsg", alarmMsg2);
                break;
            case 3:
                i.putExtra("alarmMsg", alarmMsg3);
                break;
            case 4:
                i.putExtra("alarmMsg", alarmMsg4);
                break;
            case 5:
                i.putExtra("alarmMsg", alarmMsg5);
                break;
            case 6:
                i.putExtra("alarmMsg", alarmMsg6);
                break;
            case 7:
                i.putExtra("alarmMsg", alarmMsg7);
                break;

            default:
                Log.e("DEBUG", "NAMING FAILED");
                break;
        }


        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, currentRQ, i,PendingIntent.FLAG_UPDATE_CURRENT);

        am.set(AlarmManager.RTC_WAKEUP, alarm_time_ms, alarmIntent);

        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        Date d1 = cal_alarm_final;
        Calendar c1 = GregorianCalendar.getInstance();
        c1.setTime(d1);

        String alarmTime = df.format(c1.getTime());

        alarmToast(currentRQ, alarmTime);

    }









    private static PendingIntent createPendingIntent(Context context)

    {
        Intent alarmServiceIntent = new Intent(context, AlarmService.class);
        return null;
    }



public void alarmToast(int RQ, String alarmTime) {
    final  int currentRQ = RQ;
    final String currentAlarmTime = alarmTime;

    switch (currentRQ) {
        case 1:     Toast
                .makeText(this, "Set alarm: " + alarmName1 + "\nFor: " + currentAlarmTime, Toast.LENGTH_LONG)

                .show();
            break;
        case 2:    Toast
                .makeText(this, "Set alarm: " + alarmName2 + "\nFor: " + currentAlarmTime, Toast.LENGTH_LONG)

                .show();
            break;
        case 3:     Toast
                .makeText(this, "Set alarm: " + alarmName3 + "\nFor: " + currentAlarmTime, Toast.LENGTH_LONG)

                .show();
            break;
        case 4:     Toast
                .makeText(this, "Set alarm: " + alarmName4 + "\nFor: " + currentAlarmTime, Toast.LENGTH_LONG)

                .show();
            break;
        case 5:     Toast
                .makeText(this, "Set alarm: " + alarmName5 + "\nFor: " + currentAlarmTime, Toast.LENGTH_LONG)

                .show();
            break;
        case 6:    Toast
                .makeText(this, "Set alarm: " + alarmName6 + "\nFor: " + currentAlarmTime, Toast.LENGTH_LONG)

                .show();
            break;
        case 7:    Toast
                .makeText(this, "Set alarm: " + alarmName7 + "\nFor: " + currentAlarmTime, Toast.LENGTH_LONG)

                .show();
            break;

        default:   Log.e("DEBUG", "PUTTING NAME FAILED");
            break;
    }


}



public void cancelAlarm(int RQ){

    Intent intent = new Intent(context, AlarmReceiver.class);
    PendingIntent sender = PendingIntent.getBroadcast(context, RQ, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(sender);

}
    /*
    public void onDestroy(){
        createAlarms();




    }
*/




    public void writeJSONAlarm(String[] alarms) throws IOException {





        Map obj=new LinkedHashMap();
        obj.put("0",alarms[0]);
        obj.put("1",alarms[1]);
        obj.put("2",alarms[2]);
        obj.put("3",alarms[3]);
        obj.put("4",alarms[4]);
        obj.put("5",alarms[5]);
        obj.put("6",alarms[6]);



        StringWriter out = new StringWriter();
        try {
            JSONValue.writeJSONString(obj, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonText = out.toString();
        System.out.print(jsonText);

        String FILENAME = "AlarmData.txt";


        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(jsonText.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }







    }


    public String[] recallEntryJsonAlarm() {


        String[] ret = new String[7];

        try {
            InputStream inputStream = openFileInput("AlarmData.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();

                for(int i=0; i<ret.length; i++) {

                    Log.i("RET_VALUES", "" + ret[i]);
                }
            }
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {

        }







        return ret;
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                overridePendingTransition(R.transition.in_from_left, R.transition.out_to_right);
                finish();
                return (super.onOptionsItemSelected(item));
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}











