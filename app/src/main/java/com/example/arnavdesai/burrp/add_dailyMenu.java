package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.arnavdesai.burrp.R.drawable.c;
import static com.example.arnavdesai.burrp.R.drawable.m;

public class add_dailyMenu extends AppCompatActivity  implements View.OnClickListener{

    private Button save;
    TextView bt1,bt2,bt3,bt4;
    List<String> bhajiString;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Daily Menu");
    private FirebaseUser firebaseUser;
    private String userID;
    boolean checkedItems;
    Spinner bhaji1,bhaji2,bhaji3,bhaji4;
    EditText editdailytbhaji,editbhaji2,editbhaji3,editbhaji4;
    String[] bhaji;
    int count=0,flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dailymenu);
        save=(Button)findViewById(R.id.btsave);
        bhaji1=(Spinner)findViewById(R.id.spinner1);
        bhaji2=(Spinner)findViewById(R.id.spinner2);
        bhaji3=(Spinner)findViewById(R.id.spinner3);
        bhaji4=(Spinner)findViewById(R.id.spinner4);
        bt1=(TextView) findViewById(R.id.bt1);
        bt2=(TextView) findViewById(R.id.bt2);
        bt3=(TextView) findViewById(R.id.bt3);
        bt4=(TextView) findViewById(R.id.bt4);
        editdailytbhaji=(EditText)findViewById(R.id.editbhaji1);
        editbhaji2=(EditText)findViewById(R.id.editbhaji2);
        editbhaji3=(EditText)findViewById(R.id.editbhaji3);
        editbhaji4=(EditText)findViewById(R.id.editbhaji4);
        userID=getIntent().getStringExtra("uid");
        save.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        editdailytbhaji.setVisibility(View.INVISIBLE);
        editbhaji2.setVisibility(View.INVISIBLE);
        editbhaji3.setVisibility(View.INVISIBLE);
        editbhaji4.setVisibility(View.INVISIBLE);
        List<String>dailybhaji=new ArrayList<>();
        dailybhaji.add("select");
        dailybhaji.add("batata");
        dailybhaji.add("bhendi");
        dailybhaji.add("guar");
        dailybhaji.add("mataki");
        dailybhaji.add("methi");
        dailybhaji.add("mix-veg");
        dailybhaji.add("pitale");
        dailybhaji.add("patta-kobi");
        dailybhaji.add("wange");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,dailybhaji);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bhaji1.setAdapter(dataAdapter);
        bhaji2.setAdapter(dataAdapter);
        bhaji3.setAdapter(dataAdapter);
        bhaji4.setAdapter(dataAdapter);

    }


    @Override
    public void onClick(View v) {

        if(v ==save){
            if(bhaji1.getSelectedItem().toString()=="select"&&bhaji2.getSelectedItem().toString()=="select"&&bhaji3.getSelectedItem().toString()=="select"&&bhaji4.getSelectedItem().toString()=="select"&&flag==0){
                Toast.makeText(add_dailyMenu.this,"Plase choose atleast one bhaji ",Toast.LENGTH_SHORT).show();
            }
            else {
                addtodatabase();
            }
        }
        if(v==bt1){
            editdailytbhaji.setVisibility(View.VISIBLE);
            flag=1;
            bt1.setVisibility(View.INVISIBLE);
        }
        if(v==bt2){
            editbhaji2.setVisibility(View.VISIBLE);
            flag=2;
            bt2.setVisibility(View.INVISIBLE);
            //bt3.setVisibility(View.VISIBLE);
            editdailytbhaji.setVisibility(View.INVISIBLE);
        }
        if(v==bt3){
            editbhaji3.setVisibility(View.VISIBLE);
            flag=3;
            bt3.setVisibility(View.INVISIBLE);
            //bt4.setVisibility(View.VISIBLE);
            editbhaji2.setVisibility(View.INVISIBLE);
        }
        if(v==bt4){
            editbhaji4.setVisibility(View.VISIBLE);
            flag=4;
            bt4.setVisibility(View.INVISIBLE);
            editbhaji3.setVisibility(View.INVISIBLE);
        }

    }

    private void addtodatabase() throws ArrayIndexOutOfBoundsException,NullPointerException  {

        int pos=0;
        bhajiString = new ArrayList<String>();

        if(bhaji1.getSelectedItem().toString()!="select"){
            bhajiString.add(pos++,bhaji1.getSelectedItem().toString());

        }
        if(bhaji2.getSelectedItem().toString()!="select"){
            bhajiString.add(pos++,bhaji2.getSelectedItem().toString());

        }
        if(bhaji3.getSelectedItem().toString()!="select"){
            bhajiString.add(pos++,bhaji3.getSelectedItem().toString());

        }
        if(bhaji4.getSelectedItem().toString()!="select"){
            bhajiString.add(pos++,bhaji4.getSelectedItem().toString());

        }
        if(flag==1){
            if(editdailytbhaji.getText().toString().trim().length()==0){
                Toast.makeText(add_dailyMenu.this,"Plase edit the bhaji ",Toast.LENGTH_SHORT).show();
            }
            else {
                bhajiString.add(0, editdailytbhaji.getText().toString());
            }
        }
        if(flag==2){
            bhajiString.add(1,editbhaji2.getText().toString());
        }
        if(flag==3){
            bhajiString.add(2,editbhaji3.getText().toString());
        }
        if(flag==4){
            bhajiString.add(3,editbhaji4.getText().toString());
        }
        MenuCard menuCard = new MenuCard(bhajiString);
        databaseReference.child(userID).setValue(menuCard);
        finish();
/*
 List<String> name=new ArrayList<String>();
        List<String> price=new ArrayList<String>();
        name.add(0, spinmenu1.getSelectedItem().toString());
            price.add(0, spinprice1.getSelectedItem().toString());

            name.add(1,spinmenu2.getSelectedItem().toString());
            price.add(1,spinprice2.getSelectedItem().toString());

        name.add(2,spinmenu3.getSelectedItem().toString());
        price.add(2,spinprice3.getSelectedItem().toString());

        name.add(3,spinmenu4.getSelectedItem().toString());
        price.add(3,spinprice4.getSelectedItem().toString());
        IndividualMenu menu=new IndividualMenu(name,price);

        databaseReference.child(uid).setValue(menu);
        finish();
 */
    }
}
