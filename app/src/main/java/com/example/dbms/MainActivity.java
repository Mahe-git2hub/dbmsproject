package com.example.dbms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    ListView lv;
    String tables[]={"vehicle","road","junction"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.listv);
        //android.R.layout.simple_list_item_1
        ArrayAdapter<String> ada=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                tables);
        lv.setAdapter(ada);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(lv.getItemAtPosition(position).toString()=="vehicle")
        {
            startActivity(new Intent(this,vehicle.class));
        }
        else if(lv.getItemAtPosition(position).toString()=="road")
        {
            startActivity(new Intent(this,road.class));
        }
        else if(lv.getItemAtPosition(position).toString()=="junction")
        {
            startActivity(new Intent(this,junction.class));
        }

    }
}
