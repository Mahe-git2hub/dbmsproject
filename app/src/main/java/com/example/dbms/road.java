package com.example.dbms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class road extends AppCompatActivity implements View.OnClickListener {

    EditText rid, area;
    Button btnAdd, btnDelete, btnModify, btnViewAll,  btnView;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_road);
        rid = (EditText) findViewById(R.id.rid);
        area = (EditText) findViewById(R.id.area);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnModify = (Button) findViewById(R.id.btnModify);
        btnView = (Button) findViewById(R.id.btnView);
        btnViewAll = (Button) findViewById(R.id.btnViewAll);
        //Registering Event Handlers
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnModify.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        // Creating database and table  
        db = openOrCreateDatabase("TrafficDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS road(rid VARCHAR,area VARCHAR);");
    }

    @Override
    public void onClick(View view) {
        // Adding a record
        if (view == btnAdd) {
            // Checking empty fields
            if (rid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter roadID");
                return;
            }
            // Inserting record 
            db.execSQL("INSERT INTO road VALUES('" + rid.getText() + "','" + area.getText() +
                    "','" + "');");
            showMessage("Success", "Record added");
            clearText();
        }
        // Deleting a record 
        if (view == btnDelete) {
            // Checking empty roll number 
            if (rid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter roadID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM road WHERE rid='" + rid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Deleting record if found 
                showMessage("Success", "Record Deleted");
                db.execSQL("DELETE FROM road WHERE vid='" + rid.getText() + "'");
            } else {
                showMessage("Error", "Invalid ID");
            }
            clearText();
        }
        // Modifying a record 
        if (view == btnModify) {
            // Checking empty roll number 
            if (rid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter roadID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM road WHERE rid'" + rid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Modifying record if found 
                db.execSQL("UPDATE road SET area='" + area.getText() + "' WHERE rid='" + rid.getText() + "'");
                showMessage("Success", "Record Modified");
            }
            else {
                showMessage("Error", "Invalid ID");
            }
            clearText();
        }
        // Viewing a record 
        if (view == btnView) {
            // Checking empty roll number 
            if (rid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter roadID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM road WHERE rid='" + rid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Displaying record if found 
                area.setText(c.getString(1));

            } else {
                showMessage("Error", "Invalid ID");
                clearText();
            }
        }
        // Viewing all records 
        if (view == btnViewAll) {
            // Retrieving all records 
            Cursor c = db.rawQuery("SELECT * FROM road", null);
            // Checking if no records found 
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            // Appending records to a string buffer 
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext())
            {
                buffer.append("roadID: " + c.getString(0) + "\n");
                buffer.append("area: " + c.getString(1) + "\n\n");

            }
            // Displaying all records 
            showMessage("Vehicle Details", buffer.toString());
        }

    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText(){

        rid.setText("");
        area.setText("");
        rid.requestFocus();
    }
}
