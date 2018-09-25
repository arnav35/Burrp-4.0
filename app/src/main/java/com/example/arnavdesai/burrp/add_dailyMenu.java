package com.example.arnavdesai.burrp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class add_dailyMenu extends AppCompatActivity implements View.OnClickListener {
    private Button save,add;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String userID;
    private EditText[] bhaji;
    private EditText noOfItem;
    int count;
    private View linearLayout;
    List<String> bhajiString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dailymenu);

        userID=getIntent().getStringExtra("uid");
        linearLayout=findViewById(R.id.addmenu);
        save=(Button)findViewById(R.id.btsave);
        add=(Button) findViewById(R.id.addBhaji);
        save.setOnClickListener(this);
        add.setOnClickListener(this);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getInstance().getReference("Daily Menu");
    }


    public void addToDatabase() throws  NullPointerException
    {
        bhajiString=new ArrayList<String>();

        for(int i=0; i<count; i++)
        {
            bhajiString.add(i,bhaji[i].getText().toString().trim());
        }

        MenuCard menuCard=new MenuCard(bhajiString);

        databaseReference.child(userID).setValue(menuCard);
        finish();
    }
    @Override
    public void onClick(View view) {
        if(view == save){
            addToDatabase();
        }
        if(view == add)
        {
            getData();
        }
    }


    private void getData() {

        noOfItem=(EditText) findViewById(R.id.noOfItem);
        count=Integer.parseInt(noOfItem.getText().toString().trim());
        bhaji=new EditText[count];

        for(int i=0; i<count; i++)
        {
            bhaji[i]=new EditText(this);

            bhaji[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout) linearLayout).addView(bhaji[i]);
        }
    }
}
