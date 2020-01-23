package com.example.jackson.diabetesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;


public class DeleteData extends ActionBarActivity {
    DataHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteData();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Intent intent = new Intent(DeleteData.this, MainActivity.class);
                        startActivity(intent);

                        break;
                }
            }
        };


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Permanently delete all user data?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }


    public void deleteData() {
        handler = new DataHandler(getBaseContext());
        handler.open();
        handler.delete();
        handler.close();
        Intent intent = new Intent(DeleteData.this, MainActivity.class);
        startActivity(intent);

        Toast
                .makeText(getApplicationContext(), "User Data Deleted", Toast.LENGTH_LONG)

                .show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_data, menu);
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
