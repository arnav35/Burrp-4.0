package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.R.attr.name;
import static android.R.attr.value;
import static android.R.interpolator.linear;

public class addMenu extends AppCompatActivity implements View.OnClickListener{

    private EditText numberofItem;
    int count=0;
    private EditText[] ItemName,ItemPrice;
    private Button add,enter;
    private View linearLayout;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Menu");
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        uid=getIntent().getStringExtra("uid");

        linearLayout=findViewById(R.id.activity_add_menu);
        numberofItem=(EditText) findViewById(R.id.noOfItem);
        add=(Button) findViewById(R.id.addButton);
        enter=(Button) findViewById(R.id.itemEnter);
        enter.setOnClickListener(this);
        add.setOnClickListener(this);
    }

    public void add() throws NullPointerException
    {

        count=Integer.valueOf(numberofItem.getText().toString());
        Log.d("Burrp",String.valueOf(count));

        ItemName=new EditText[count];
        ItemPrice=new EditText[count];

        for(int i=0;i<count;i++)
        {
            ItemName[i]=new EditText(this);
            ItemPrice[i]=new EditText(this);

            //ItemName[i].setId(i);
            //ItemPrice[i].setId(i);

            ItemName[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ItemPrice[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            ((LinearLayout) linearLayout).addView(ItemName[i]);
            ((LinearLayout) linearLayout).addView(ItemPrice[i]);
        }
    }

    private void addtoDatabase() throws NullPointerException
    {
        List<String> name=new ArrayList<String>();
        List<String> price=new ArrayList<String>();


        for(int i=0;i<count;i++)
        {
            name.add(i,ItemName[i].getText().toString());
            price.add(i,ItemPrice[i].getText().toString());
        }

        IndividualMenu menu=new IndividualMenu(name,price);

        databaseReference.child(uid).setValue(menu);

        finish();
    }

    @Override
    public void onClick(View v)
    {
        if(v == add)
        {
            add();
        }
        if(v == enter)
        {
            addtoDatabase();
        }
    }
}
