package com.example.arnavdesai.burrp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.name;
import static com.example.arnavdesai.burrp.R.id.linearLayout;

public class showReview extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase firebaseDatabase;
    String userID,name;
    private View linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_review);
        linearLayout=findViewById(R.id.activity_show_review);
        name=getIntent().getStringExtra("uid");

        databaseReference=firebaseDatabase.getInstance().getReference("Owner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userID=showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference=firebaseDatabase.getInstance().getReference("Review");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showReview(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String showData(DataSnapshot dataSnapshot) throws NullPointerException{

        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            if(ds.getValue(Owner.class).getMessName().equals(name))
            {
                userID=ds.getKey().toString();
            }
        }
        return userID;
    }


    private void showReview(DataSnapshot dataSnapshot) {

        int count=(int)dataSnapshot.child(userID).getChildrenCount();

        TextView[] review = new TextView[count + 1];

        review[0] = new TextView(this);
        review[0].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        review[0].setText("Reviews");
        review[0].setTextSize(20);
        ((LinearLayout) linearLayout).addView(review[0]);
        int j=1;

        for(DataSnapshot ds: dataSnapshot.child(userID).getChildren())
        {
            review[j]=new TextView(this);
            review[j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            review[j].setText(ds.getValue(String.class));
            review[j].setTextSize(15);
            ((LinearLayout) linearLayout).addView(review[j]);
            j++;
        }
    }

}
