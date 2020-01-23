package com.example.jackson.diabetesapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NewEntryActivity extends ActionBarActivity {
    private final static String STORETEXT = "data.txt";
    static Gson gsonArray;


    private EditText bg_field = null;
    private EditText carbs_field = null;
    private EditText protein_field = null;
    private EditText fat_field = null;
    DataHandler handler;

    public NewEntryActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        bg_field = (EditText) findViewById(R.id.bg_field);
        carbs_field = (EditText) findViewById(R.id.carbs_field);
        protein_field = (EditText) findViewById(R.id.protein_field);
        fat_field = (EditText) findViewById(R.id.fat_field);

        bg_field.requestFocus();

        final Button cancel = (Button) findViewById(R.id.new_entry_cancel_button);

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.myVib.vibrate(65);
                Intent intent = new Intent(NewEntryActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                finish();
            }
        });


        final Button okay = (Button) findViewById(R.id.new_entry_okay_button);

        okay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int retId = 0;
                int retIds[];
                int id = 0;


                try {
                    retIds = retIds();
                    List<Integer> list = new ArrayList<Integer>(retIds.length);
                    for (int i : retIds) list.add(i);


                } catch (IOException e) {
                    Toast
                            .makeText(getApplicationContext(), "FAILED TO RETRIEVE DATA", Toast.LENGTH_LONG)

                            .show();
                }

//assignId(id);


                String bg_string = bg_field.getText().toString();
                Log.i("BG", "" + bg_string);
                if (bg_string.equals("")) {
                    bg_string = "0";
                }
                int bg = Integer.parseInt(bg_string);


                String carbs_string = carbs_field.getText().toString();
                if (carbs_string.equals("")) {
                    carbs_string = "0";
                }
                int carbs = Integer.parseInt(carbs_string);

                String protein_string = protein_field.getText().toString();
                if (protein_string.equals("")) {
                    protein_string = "0";
                }
                int protein = Integer.parseInt(protein_string);

                String fat_string = fat_field.getText().toString();
                if (fat_string.equals("")) {
                    fat_string = "0";
                }
                int fat = Integer.parseInt(fat_string);

                MainActivity.myVib.vibrate(65);
/*
                try {
                    writeJSON(bg, carbs, protein, fat);
                } catch (IOException e) {
                    e.printStackTrace();
         }
           */
                Date dat = new Date();
                Calendar cal_now = Calendar.getInstance();
                cal_now.setTime(dat);

                Log.i("DATE", "" + dat);

                long datLong = cal_now.getTimeInMillis() / 1000;
                int time = (int) datLong;
                handler = new DataHandler(getBaseContext());
                handler.open();
                long dataId = handler.insertData(time, bg, carbs, protein, fat);
                handler.close();

                Toast
                        .makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG)

                        .show();


                MainActivity.myVib.vibrate(65);
                Intent intent = new Intent(NewEntryActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                finish();


            }
        });


    }

    public ArrayList<Integer> recallSQLOLD(String reqColumn) {

        handler = new DataHandler(getBaseContext());
        int colNum = 0;
        int testVar = 0;
        int[] retArray;
        ArrayList<Integer> retArrayList = new ArrayList<Integer>();

        int iLast = 11;


        handler.open();
        Cursor C = handler.returnData();
        if (C.moveToLast()) {

            do {

                iLast = iLast - 1;
                if (iLast == 0) {
                    break;
                }
                Log.i("SQL_DEBUG", "colNum: " + colNum);
                testVar = C.getInt(0);
                //retArray[iLast] = C.getInt(colNum);
                switch (reqColumn) {
                    case ("time"):
                        retArrayList.add(C.getInt(0));
                    case ("bg"):
                        retArrayList.add(C.getInt(1));
                    case ("carbs"):
                        retArrayList.add(C.getInt(2));
                    case ("protein"):
                        retArrayList.add(C.getInt(3));
                    case ("fat"):
                        retArrayList.add(C.getInt(4));
                }
                //Log.i("SQL_DEBUG", "iLast: " + iLast + "\n" + "array[ilast]: " + retArrayList.get(iLast));


            } while (C.moveToPrevious());
            int iFirst = iLast;

            Log.i("SQL_DEBUG", "iFirst: " + iFirst);
            //Log.i("SQL_DEBUG", "array final: " + retArray);
            handler.close();
        }


        return retArrayList;
    }

    public void getIds(int[] ids) throws IOException {


        try {
            ids = retIds();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public int[] retIds() throws IOException {

        Gson retgson = new Gson();


        String ret = "";
        try {
            InputStream inputStream = openFileInput("ids.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = br.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();


                ret = stringBuilder.toString();


            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }


        String[] items = ret.replaceAll("\\[", "").replaceAll("\\]", "").split(",");

        int[] ids = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                ids[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
            }
            ;
        }


        Log.i("DEBUG GSON", "" + retgson);


        return ids;


    }


    public void assignId(int id) throws IOException {


        Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(id);

        StringWriter out = new StringWriter();
        try {
            JSONValue.writeJSONString(json, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonText = out.toString();
        System.out.print(jsonText);

        String FILENAME = "ids.txt";


        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_APPEND);
            fos.write(jsonText.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void writeGSON(int id, int bg, int carbs, int protein, int fat) throws IOException {

        Date dat = new Date();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);

        Log.i("DATE", "" + dat);

        long datLong = cal_now.getTimeInMillis() / 1000;
        int datInt = (int) datLong;

        final int bgFinal = bg;
        final int carbsFinal = carbs;
        final int proteinFinal = protein;
        final int fatFinal = fat;
        int currentId = id;

        final int array[] = {
                currentId, datInt, bgFinal, carbsFinal, proteinFinal, fatFinal

        };


        //gsonArray = Gson.toJson(array, "data.txt");


        Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(array);

        StringWriter out = new StringWriter();
        try {
            JSONValue.writeJSONString(json, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonText = out.toString();
        System.out.print(jsonText);

        String FILENAME = "data.txt";


        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_APPEND);
            fos.write(jsonText.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public void writeJSON(int bg, int carbs, int protein, int fat) throws IOException {

        DateTime time = new DateTime();


        Map obj = new LinkedHashMap();
        obj.put("time", time);
        obj.put("bg", bg);
        obj.put("carbs", carbs);
        obj.put("protein", protein);
        obj.put("fat", fat);


        StringWriter out = new StringWriter();
        try {
            JSONValue.writeJSONString(obj, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonText = out.toString();
        System.out.print(jsonText);

        String FILENAME = "data.txt";


        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_APPEND);
            fos.write(jsonText.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    public ArrayList recallEntryJson(View v) {

        Log.i("DEBUG", "Recalling ENTRY");
        String ret = "";

        try {
            InputStream inputStream = openFileInput("data.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                Log.i("DEBUG", "Ret: " + ret);

                JSONObject jsonObj = null;

                try {


                    ret.replaceAll("\"", "\\\\\"");
                    Log.i("DEBUG", "Ret in TRY: " + ret);

                    jsonObj = new JSONObject("\"" + ret + "\"");
                    int t = jsonObj.getInt("time");
                    Log.i("DEBUG", "tString= " + t);
                    int bg = jsonObj.getInt("bg");

                    //System.out.println(t);
                    String tString = Integer.toString(t);

                    Log.i("DEBUG", "Ret2: " + ret);
                    Log.i("DEBUG", "tString= " + tString);

                    Toast
                            .makeText(this, tString, Toast.LENGTH_LONG)

                            .show();


                } catch (JSONException e) {
                    Toast
                            .makeText(this, "FAILED", Toast.LENGTH_LONG)

                            .show();

                } finally {

                }


            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }


        return null;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
                finish();
        }
        return (super.onOptionsItemSelected(item));
        //noinspection SimplifiableIfStatement


    }


}
