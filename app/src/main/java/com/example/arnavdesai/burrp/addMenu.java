package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.arnavdesai.burrp.R.*;
import static com.example.arnavdesai.burrp.R.layout.*;

public class addMenu extends AppCompatActivity implements View.OnClickListener{


    private EditText numberofItem;

    private Button add,enter;
    private View linearLayout;
    private Spinner spinmenu1,spinprice1,spinmenu2,spinprice2,spinmenu3,spinprice3,spinmenu4,spinprice4;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Menu");
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_add_menu);
        uid=getIntent().getStringExtra("uid");
        enter=(Button) findViewById(id.itemEnter);
        spinmenu1=(Spinner)findViewById(id.spinnermenu1);
        spinmenu2=(Spinner)findViewById(id.spinnermenu2);
        spinprice1=(Spinner)findViewById(id.spinnerprice1);
        spinprice2=(Spinner)findViewById(id.spinnerprice2);
        spinmenu3=(Spinner)findViewById(id.spinnermenu3);
        spinprice3=(Spinner)findViewById(id.spinnerprice3);
        spinmenu4=(Spinner)findViewById(id.spinnermenu4);
        spinprice4=(Spinner)findViewById(id.spinnerprice4);
        ArrayList<String> list=new ArrayList<>();
        list.add("select");
        list.add("Chapati");
        list.add("poli-Bhaji");
        list.add("Rice-Plate");
        list.add("Dal-Rice");

        //Spinner sp=(Spinner)findViewById(id.spinner);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //setting view of dropdown
        spinmenu1.setAdapter(dataAdapter);
        spinmenu2.setAdapter(dataAdapter);
        spinmenu3.setAdapter(dataAdapter);
        spinmenu4.setAdapter(dataAdapter);

        List<String> menuPrice = new ArrayList<String>();  // a list to be displayed in the spinner
        menuPrice.add("select");
        menuPrice.add("\u20B9 5");
        menuPrice.add("\u20B9 6");
        menuPrice.add("\u20B9 7");
        menuPrice.add("\u20B9 8");
        menuPrice.add("\u20B9 15");
        menuPrice.add("\u20B9 20");
        menuPrice.add("\u20B9 25");
        menuPrice.add("\u20B9 30");
        menuPrice.add("\u20B9 40");
        menuPrice.add("\u20B9 45");
        menuPrice.add("\u20B9 50");


        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,menuPrice);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //setting view of dropdown
        spinprice1.setAdapter(dataAdapter2);
        spinprice2.setAdapter(dataAdapter2);
        spinprice3.setAdapter(dataAdapter2);
        spinprice4.setAdapter(dataAdapter2);
        enter.setOnClickListener(this);

    }

    private void addtoDatabase() throws NullPointerException
    {
        List<String> name=new ArrayList<String>();
        List<String> price=new ArrayList<String>();

        if(spinmenu1.getSelectedItem().toString()!="select"&&spinprice1.getSelectedItem().toString()!="select") {
            name.add(0, spinmenu1.getSelectedItem().toString());
            price.add(0, spinprice1.getSelectedItem().toString());
        }
        if(spinmenu2.getSelectedItem().toString()!="select"&&spinprice2.getSelectedItem().toString()!="select") {
            name.add(1, spinmenu2.getSelectedItem().toString());
            price.add(1, spinprice2.getSelectedItem().toString());
        }
        if(spinmenu3.getSelectedItem().toString()!="select"&&spinprice3.getSelectedItem().toString()!="select") {
            name.add(2, spinmenu3.getSelectedItem().toString());
            price.add(2, spinprice3.getSelectedItem().toString());
        }
        if(spinmenu4.getSelectedItem().toString()!="select"&&spinprice4.getSelectedItem().toString()!="select") {
            name.add(3, spinmenu4.getSelectedItem().toString());
            price.add(3, spinprice4.getSelectedItem().toString());
        }
        IndividualMenu menu=new IndividualMenu(name,price);

        databaseReference.child(uid).setValue(menu);
        //Intent intent=new Intent(addMenu.this,OwnerProfile.class);
        //startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v)
    {
        if(v == enter)
        {
            if(spinmenu1.getSelectedItem().toString()=="select"&&spinmenu2.getSelectedItem().toString()=="select"&&spinmenu3.getSelectedItem().toString()=="select"&&spinmenu4.getSelectedItem().toString()=="select"){
                Toast.makeText(addMenu.this,"Please choose the menu ",Toast.LENGTH_SHORT).show();
            }
            else if(spinprice1.getSelectedItem().toString()=="select"&&spinprice2.getSelectedItem().toString()=="select"&&spinprice3.getSelectedItem().toString()=="select"&&spinprice4.getSelectedItem().toString()=="select"){
                Toast.makeText(addMenu.this,"Please choose the price",Toast.LENGTH_SHORT).show();
            }
            else {
                addtoDatabase();
            }
        }
    }
}