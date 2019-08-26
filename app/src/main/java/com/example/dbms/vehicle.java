package com.example.dbms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

public class vehicle extends AppCompatActivity {
    EditText vehicleno, district_code, region_code;
    Button button;
    SQLiteDatabase db;
    private Spinner spinner;
    String[] statecodes = new String[]{"KL", "AP", "TN", "KA", "MH", "DL"};
    ArrayAdapter<String> arrayAdapter;
    Switch aSwitch;
    String switcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        vehicleno = findViewById(R.id.vehicle_no);
        district_code = findViewById(R.id.district_code);
        region_code = findViewById(R.id.region_id);
        spinner = findViewById(R.id.states);
        button = findViewById(R.id.search_vehicle);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                statecodes);
        aSwitch = findViewById(R.id.switch1);
        spinner.setAdapter(arrayAdapter);

        // Creating database and table  
        db = openOrCreateDatabase("TrafficDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS vehicle(spinner VARCHAR PRIMARY KEY,district_code VARCHAR," +
                "region_code VARCHAR,vehicleno varchar,switcher varchar);");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked())
                    switcher = aSwitch.getTextOn().toString();
                else
                    switcher = aSwitch.getTextOff().toString();

                if (spinner.getContentDescription().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter vehicleID");
                } else if (button.getText().toString().equals("save vehicle")) {
                    // Inserting record 
                    db.execSQL("INSERT INTO vehicle VALUES('" + spinner.getContentDescription() + "','"
                            + district_code.getText() +
                            "','" + region_code.getText() + "','" + vehicleno.getText() + "','" + switcher + "');");
                    showMessage("Success", "Record added");
                    clearText();
                    button.setText(getString(R.string.view_entered_data));
                } else {
                    try (Cursor c = db.rawQuery("SELECT * FROM vehicle WHERE spinner='" +
                                    spinner.getContentDescription() + "'",
                            null)) {
                        if (c.moveToFirst()) {
                            // Displaying record if found 
                            district_code.setText(c.getString(1));
                            region_code.setText(c.getString(2));
                            vehicleno.setText(c.getString(3));

                            showMessage("Info", "THE DETAILS ARE AS FOLLOWS \n" +
                                    spinner.getContentDescription().toString() + "\t"
                                    + district_code.getText().toString() + "\t" + region_code.getText().toString() + "\t"
                                    + vehicleno.getText().toString() + aSwitch.getText().toString());
                        }
                    }
                    button.setText(getString(R.string.save_vehicle));
                }
            }
        });
    }



    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText(){

        district_code.setText("");
        region_code.setText("");
        vehicleno.setText("");
        spinner.requestFocus();
    }
}
