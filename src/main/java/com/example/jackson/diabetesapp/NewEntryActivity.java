package com.example.jackson.diabetesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class NewEntryActivity extends ActionBarActivity {
    private final static String STORETEXT="data.txt";



    private  EditText bg_field = null;
    private  EditText carbs_field = null;
    private  EditText protein_field = null;
    private  EditText fat_field = null;


    public NewEntryActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bg_field=(EditText)findViewById(R.id.bg_field);
        carbs_field=(EditText)findViewById(R.id.carbs_field);
        protein_field=(EditText)findViewById(R.id.protein_field);
        fat_field=(EditText)findViewById(R.id.fat_field);

        final Button cancel= (Button)findViewById(R.id.new_entry_cancel_button);

        cancel.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
            {
                MainActivity.myVib.vibrate(65);
                Intent intent = new Intent(NewEntryActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
                finish();
            }
        });




        final Button okay= (Button)findViewById(R.id.new_entry_okay_button);

        okay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String bg_string = bg_field.getText().toString();
                int bg = Integer.parseInt(bg_string);


                String carbs_string = carbs_field.getText().toString();
                int carbs = Integer.parseInt(carbs_string);

                String protein_string = protein_field.getText().toString();
                int protein = Integer.parseInt(protein_string);

                String fat_string = fat_field.getText().toString();
                int fat = Integer.parseInt(fat_string);

                MainActivity.myVib.vibrate(65);

                try {
                    writeJSON(bg, carbs, protein, fat);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


        final Button clearJson= (Button)findViewById(R.id.new_entry_clear_button);

        clearJson.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {

                    OutputStreamWriter out=

                            new OutputStreamWriter(openFileOutput("data.txt", 1));
                       out.flush();

                    out.close();

                    Toast
                            .makeText(getApplicationContext(), "Deleted.", Toast.LENGTH_SHORT)

                            .show();
                }

                catch (Throwable t) {
                    Toast
                            .makeText(getApplicationContext(), "Exception: " + t.toString(), Toast.LENGTH_LONG)

                            .show();

                }





            }
        });


    }


    public void writeJSON(int bg, int carbs,int protein,int fat) throws IOException {

        DateTime time= new DateTime();



        Map obj=new LinkedHashMap();
        obj.put("time",time);
        obj.put("bg",bg);
        obj.put("carbs",carbs);
        obj.put("protein",protein);
        obj.put("fat",fat);


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



    public void saveEntry(String bg, String carbs,String protein, String fat) {

        try {

            OutputStreamWriter out=

                    new OutputStreamWriter(openFileOutput("entries.text", 0));

                    out.write(bg.toString() + ", ");

                    out.write(carbs.toString() + ", ");

                    out.write(protein.toString() + ", ");

                    out.write(fat.toString() + ", ");

                    out.close();

                        Toast
                            .makeText(this, "Saved.", Toast.LENGTH_LONG)

                            .show();
        }

        catch (Throwable t) {
            Toast
                    .makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)

                    .show();

        }

    }

    /*public void saveEntryXML(String bg, String carbs,String protein, String fat) {

        try {

            OutputStreamWriter out=

                    new OutputStreamWriter(openFileOutput("entries.xml", 32768));


                        out.write(bg.toString() + ", ");

                        out.write(carbs.toString() + ", ");

                        out.write(protein.toString() + ", ");



                        out.close();

            Toast
                    .makeText(this, "Saved.", Toast.LENGTH_LONG)

                    .show();
        }

        catch (Throwable t) {
            Toast
                    .makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)

                    .show();

        }

    }



*/

    public void saveEntryJson(String bg, String carbs,String protein, String fat) {

        try {


            Toast
                    .makeText(this, "Saved.", Toast.LENGTH_LONG)

                    .show();
        }

        catch (Throwable t) {
            Toast
                    .makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG)

                    .show();

        }

    }



    public void recallEntry(View v) {

         String ret = "";

            try {
                InputStream inputStream = openFileInput("entries.text");

                if ( inputStream != null ) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ( (receiveString = bufferedReader.readLine()) != null ) {
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    ret = stringBuilder.toString();
                }
            }
            catch (FileNotFoundException e) {

            } catch (IOException e) {

            }


        Toast
                .makeText(this, ret, Toast.LENGTH_LONG)

                .show();
        }

    public void recallEntryJson(View v) {
        TextView debug_text_view =(TextView)findViewById(R.id.new_entry_debug_text_view);

        String ret = "";

        try {
            InputStream inputStream = openFileInput("data.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

debug_text_view.setText(ret);


        Toast
                .makeText(this, ret, Toast.LENGTH_LONG)

                .show();
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
                overridePendingTransition(R.anim.abc_slide_in_top,R.anim.abc_slide_out_bottom);
                            finish();        }
        return (super.onOptionsItemSelected(item));
        //noinspection SimplifiableIfStatement


    }






}
