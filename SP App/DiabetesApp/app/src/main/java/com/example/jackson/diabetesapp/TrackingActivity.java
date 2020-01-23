package com.example.jackson.diabetesapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TrackingActivity extends ActionBarActivity {
    DataHandler handler;
    String reqColumn = "bg";
    private PointsGraphSeries<DataPoint> series;
    //private LineGraphSeries<DataPoint> seriesLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_tracking);

        //INITIALIZE SPINNER
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new PointsGraphSeries<>();
        //seriesLine = new LineGraphSeries<>();
        graph.addSeries(series);
        //graph.addSeries(seriesLine);

        setValues(reqColumn);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().toString().toLowerCase().equals("blood glucose vs. time")) {
                    reqColumn = "bg";
                    Log.i("DEBUG:", "spinner is bg");

                } else if (spinner.getSelectedItem().toString().toLowerCase().equals("carbs vs. time")) {
                    reqColumn = "carbs";
                    Log.i("DEBUG:", "spinner is carbs");

                } else if (spinner.getSelectedItem().toString().toLowerCase().equals("protein vs. time")) {
                    reqColumn = "protein";
                    Log.i("DEBUG:", "spinner is protein");

                } else if (spinner.getSelectedItem().toString().toLowerCase().equals("fat vs. time")) {
                    reqColumn = "fat";
                    Log.i("DEBUG:", "spinner is fat");

                } else {
                    reqColumn = "bg";
                    Log.i("DEBUG:", "spinner is fat");
                }
                setValues(reqColumn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


    }

    public void setValues(String reqColumn) {


        ArrayList<Integer> xArrayList = recallSQL("time");

        ArrayList<Integer> yArrayList = recallSQL(reqColumn);

        if (xArrayList.size() == 0) {
            for (int i = 0; i < 10; i++) {
                xArrayList.add(0);

            }

        }


        Log.i("xArrayList: ", "" + xArrayList);

        int[] xArray = convertIntegers(xArrayList);

        Date[] dateArray = new Date[xArray.length];

        for (int i = 0; i < xArray.length; i++) {
            Date d = new Date(xArray[i] * 1000L);
            dateArray[i] = (d);

            Log.i("DEBUG", "dateArray pos " + i + ": " + dateArray[i]);


        }

        for (int it = 0; it < xArray.length; it++) {
            Log.i("DEBUG", "xARRAY POS " + it + ": " + xArray[it]);
        }


        //FOR DEBUGGING TIMES:
/*
        dateArray[0]= new Date(1429255810*1000L);
        dateArray[1]= new Date(1429039210*1000L);
        dateArray[2] = new Date(1429555510*1000L);
*/

        GraphView graph = (GraphView) findViewById(R.id.graph);


        series.resetData(generateData(dateArray, yArrayList));
        // seriesLine.resetData(generateData(dateArray, yArrayList));


        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));


        graph.getGridLabelRenderer().setNumHorizontalLabels(3);//MAX 4


        //graph.getViewport().setMinX(dateArray[0].getTime());
        // graph.getViewport().setMaxX(dateArray[dateArray.length - 1].getTime());
        //graph.getViewport().setXAxisBoundsManual(true);

    }

    private DataPoint[] generateData(Date[] dateArray, ArrayList<Integer> yArrayList) {
        int count = dateArray.length;
        DataPoint[] values = new DataPoint[count];

        if (yArrayList.size() == 0) {
            for (int i = 0; i < 10; i++) {
                yArrayList.add(0);

            }

        }

        for (int i = 0; i < count; i++) {
            Log.i("xValue:", "" + dateArray[i]);
            Log.i("yValue:", "" + yArrayList.get(i));
            DataPoint v = new DataPoint(dateArray[i], yArrayList.get(i));
            values[i] = v;
        }


        return values;

    }

    public ArrayList<Integer> recallSQL(String reqColumn) {


        handler = new DataHandler(getBaseContext());
        ArrayList<Integer> retArrayList = new ArrayList<>();

        int rowNum = 0;

        handler.open();
        Cursor C = handler.returnData();

        retArrayList.clear();

        if (C.moveToFirst()) {

            do {
                rowNum = rowNum + 1;

                //retArray[iLast] = C.getInt(colNum);
                if (reqColumn.equals("time")) {
                    retArrayList.add(C.getInt(0));
                } else if (reqColumn.equals("bg")) {
                    retArrayList.add(C.getInt(1));
                } else if (reqColumn.equals("carbs")) {
                    retArrayList.add(C.getInt(2));
                } else if (reqColumn.equals("protein")) {
                    retArrayList.add(C.getInt(3));
                } else {
                    retArrayList.add(C.getInt(4));
                }


            } while (C.moveToNext() && rowNum < 10);


            Log.i("SQL_DEBUG", "rowNum: " + rowNum);

            handler.close();

        }

        return retArrayList;
    }


    public static int[] convertIntegers(List<Integer> retArrayList) {
        int[] ret = new int[retArrayList.size()];
        Log.i("SQL_DEBUG", "ret size: " + ret.length);
        for (int i = 0; i < ret.length; i++) {
            ret[i] = retArrayList.get(i);
        }
        return ret;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tracking, menu);
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
