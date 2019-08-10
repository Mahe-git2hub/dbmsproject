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

public class junction extends AppCompatActivity implements View.OnClickListener {

    EditText jid, type, name;
    Button btnAdd, btnDelete, btnModify, btnViewAll,  btnView;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junction);
        jid = (EditText) findViewById(R.id.jid);
        type = (EditText) findViewById(R.id.type);
        name = (EditText) findViewById(R.id.name);

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
        db.execSQL("CREATE TABLE IF NOT EXISTS junction(jid VARCHAR,type VARCHAR,name VARCHAR);");
    }

    @Override
    public void onClick(View view) {
        // Adding a record
        if (view == btnAdd) {
            // Checking empty fields
            if (jid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter junctionID");
                return;
            }
            // Inserting record 
            db.execSQL("INSERT INTO vehicle VALUES('" + jid.getText() + "','" + type.getText() +
                    "','" + name.getText() + "');");
            showMessage("Success", "Record added");
            clearText();
        }
        // Deleting a record 
        if (view == btnDelete) {
            // Checking empty roll number 
            if (jid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter junctionID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM junction WHERE jid='" + jid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Deleting record if found 
                showMessage("Success", "Record Deleted");
                db.execSQL("DELETE FROM junction WHERE jid='" + jid.getText() + "'");
            } else {
                showMessage("Error", "Invalid ID");
            }
            clearText();
        }
        // Modifying a record 
        if (view == btnModify) {
            // Checking empty roll number 
            if (jid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter junctionID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM junction WHERE jid'" + jid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Modifying record if found 
                db.execSQL("UPDATE junction SET type='" + type.getText() + "' WHERE jid='" + jid.getText() + "'");
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
            if (jid.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter junctionID");
                return;
            }
            // Searching roll number 
            Cursor c = db.rawQuery("SELECT * FROM junction WHERE jid='" + jid.getText() + "'", null);
            if (c.moveToFirst()) {
                // Displaying record if found 
                type.setText(c.getString(1));
                name.setText(c.getString(2));

            } else {
                showMessage("Error", "Invalid ID");
                clearText();
            }
        }
        // Viewing all records 
        if (view == btnViewAll) {
            // Retrieving all records 
            Cursor c = db.rawQuery("SELECT * FROM junction", null);
            // Checking if no records found 
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            // Appending records to a string buffer 
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext())
            {
                buffer.append("junctionID: " + c.getString(0) + "\n");
                buffer.append("Type: " + c.getString(1) + "\n");
                buffer.append("Name: " + c.getString(2) + "\n\n");


            }
            // Displaying all records 
            showMessage("Junction Details", buffer.toString());
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

        jid.setText("");
        type.setText("");
        name.setText("");
        jid.requestFocus();
    }
}
