package com.example.arnavdesai.burrp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OwnermenutoStudent extends AppCompatActivity {

    private TextView[] itemNameText;
    private TextView[] itemPriceText;
    String userID,userId1;
    private View linearLayout;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,databaseReference1;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownermenuto_student);
        userID=getIntent().getStringExtra("uid");
        Log.d("userId",userID);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        databaseReference=firebaseDatabase.getInstance().getReference("Menu");

        databaseReference1=firebaseDatabase.getInstance().getReference("Owner");


        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showdata22(dataSnapshot);
            }

            private void showdata22(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    if(ds.getValue(Owner.class).getMessName().equals(userID))
                    {
                        userId1=ds.getKey().toString();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showMenu(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showMenu(DataSnapshot dataSnapshot) {
        IndividualMenu obj=dataSnapshot.child(userId1).getValue(IndividualMenu.class);

        List<String> Name=new ArrayList<String>();
        Name=obj.ItemName;
        List<String> Price=new ArrayList<String>();
        Price=obj.ItemPrice;

        int length=Name.size();
        itemNameText=new TextView[length];
        itemPriceText=new TextView[length];

        for(int i=0; i<length; i++)
        {
            itemNameText[i]=new TextView(this);
            itemPriceText[i]=new TextView(this);

            itemPriceText[i].setText(Price.get(i));
            itemNameText[i].setText(Name.get(i));

            itemNameText[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            itemPriceText[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            itemNameText[i].setTextSize(20);
            itemPriceText[i].setTextSize(20);
            ((LinearLayout) linearLayout).addView(itemNameText[i]);
            ((LinearLayout) linearLayout).addView(itemPriceText[i]);
        }

    }


}
