package com.example.arnavdesai.burrp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Ownerbhajitostudent extends AppCompatActivity {
    private TextView[] dailyMenu;
    private View linearLayout;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,databaseReference1;
    private FirebaseUser firebaseUser;

    String userID,userId1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerbhajitostudent);
        userID=getIntent().getStringExtra("uid");
        Log.d("userId",userID);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        databaseReference=firebaseDatabase.getInstance().getReference("Daily Menu");

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
                showDaily(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showDaily(DataSnapshot dataSnapshot) throws NullPointerException {
        MenuCard obj = dataSnapshot.child(userId1).getValue(MenuCard.class);

        int count;
        List<String> bhajiString = obj.getBhaji();
        count = bhajiString.size();

        dailyMenu = new TextView[count + 1];

        for (int i = 0; i < count; i++) {
            dailyMenu[i] = new TextView(this);

            dailyMenu[i].setText(bhajiString.get(i));
            dailyMenu[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout) linearLayout).addView(dailyMenu[i]);
            dailyMenu[i].setTextSize(20);
            // dailyMenu[count]=new TextView(this);
            //dailyMenu[count].setText("Price Menu");

            //dailyMenu[count].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            //((LinearLayout) linearLayout).addView(dailyMenu[count]);

        }

    }
}
