package com.example.arnavdesai.burrp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Menu extends AppCompatActivity {

    String[] nameArray={"Chak le","Shiv Shursti","Nandini"};
    String[] addressArray={"PVG", "TAWRE CHOWK", "PVG"};
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        CustomListAdapter customListAdapter=new CustomListAdapter(this, nameArray, addressArray);
        listview=(ListView) findViewById(R.id.listviewID);
        listview.setAdapter(customListAdapter);
    }
}