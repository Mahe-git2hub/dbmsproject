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

public class vehicle extends AppCompatActivity implements View.OnClickListener {

    EditText vid, type, owner,contact;
    Button btnAdd, btnDelete, btnModify, btnViewAll,  btnView;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        vid = (EditText) findViewById(R.id.vid);
        type = (EditText) findViewById(R.id.type);
        owner = (EditText) findViewById(R.id.owner);
        contact = (EditText) findViewById(R.id.contact);

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
        db.execSQL("CREATE TABLE IF NOT EXISTS vehicle(vid VARCHAR,type VARCHAR,owner VARCHAR,contact varchar);");
    }

    @Override
    public void onClick(View view) {
        // Adding a record
        if (view == btnAdd) {
            // Checking empty fields
            if (vid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter vehicleID");
                return;
            }
            // Inserting record 
            db.execSQL("INSERT INTO vehicle VALUES('" + vid.getText() + "','" + type.getText() +
                    "','" + owner.getText() + "','" + contact.getText() +"');");
            showMessage("Success", "Record added");
            clearText();
        }
        // Deleting a record 
        if (view == btnDelete) {
            // Checking empty roll number 
            if (vid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter vehicleID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM vehicle WHERE vid='" + vid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Deleting record if found 
                showMessage("Success", "Record Deleted");
                db.execSQL("DELETE FROM vehicle WHERE vid='" + vid.getText() + "'");
            } else {
                showMessage("Error", "Invalid ID");
            }
            clearText();
        }
        // Modifying a record 
        if (view == btnModify) {
            // Checking empty roll number 
            if (vid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter vehicleID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM vehicle WHERE vid'" + vid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Modifying record if found 
                db.execSQL("UPDATE vehicle SET type='" + type.getText() + "',owner='" + owner.getText() + "',contact='" + contact.getText() +
                        "' WHERE vid='" + vid.getText() + "'");
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
            if (vid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter vehicleID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM vehicle WHERE vid='" + vid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Displaying record if found 
                type.setText(c.getString(1));
                owner.setText(c.getString(2));
                contact.setText(c.getString(3));

            } else {
                showMessage("Error", "Invalid ID");
                clearText();
            }
        }
        // Viewing all records 
        if (view == btnViewAll) {
            // Retrieving all records 
            Cursor c = db.rawQuery("SELECT * FROM vehicle", null);
            // Checking if no records found 
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            // Appending records to a string buffer 
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext())
            {
                buffer.append("vehicleID: " + c.getString(0) + "\n");
                buffer.append("Type: " + c.getString(1) + "\n");
                buffer.append("Owner: " + c.getString(2) + "\n");
                buffer.append("Contact: " + c.getString(3) + "\n\n");

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

        vid.setText("");
        type.setText("");
        owner.setText("");
        contact.setText("");
        vid.requestFocus();
    }
}
