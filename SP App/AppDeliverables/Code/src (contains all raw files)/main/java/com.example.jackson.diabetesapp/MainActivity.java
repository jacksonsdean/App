package com.example.jackson.diabetesapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
    public static Vibrator myVib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);


        final Button newEntry = (Button) findViewById(R.id.new_entry);
        final Button settings = (Button) findViewById(R.id.settings);
        final Button reminders = (Button) findViewById(R.id.reminders_button);
        final Button tracking = (Button) findViewById(R.id.tracking);

        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myVib.vibrate(65);
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.transition.in_from_left, R.transition.out_to_right);
                finish();
            }
        });

        reminders.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myVib.vibrate(65);
                Intent intent = new Intent(MainActivity.this, RemindersActivity.class);
                startActivity(intent);
                overridePendingTransition(R.transition.in_from_right, R.transition.out_to_left);
                finish();

            }
        });


        tracking.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myVib.vibrate(65);
                Intent intent = new Intent(MainActivity.this, TrackingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                finish();
            }
        });

        newEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myVib.vibrate(65);
                Intent intent = new Intent(MainActivity.this, NewEntryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
