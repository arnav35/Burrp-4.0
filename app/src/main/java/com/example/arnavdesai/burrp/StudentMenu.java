package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentMenu extends AppCompatActivity{

    private TextView messName,messAddress,messEmail,messPhone,ratingDisplay;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String userID,name;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        messName=(TextView) findViewById(R.id.MessNameText);
        messAddress=(TextView) findViewById(R.id.MessAddressText);
        messEmail=(TextView) findViewById(R.id.EmailText);
        messPhone=(TextView) findViewById(R.id.PhoneNumberText);
        ratingDisplay=(TextView) findViewById(R.id.RatingDisplay);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar2);

        name=getIntent().getStringExtra("messName");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingDisplay.setText(String.valueOf(rating));
            }
        });
        ratingDisplay.setText(String.valueOf(ratingBar.getRating()));


        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getInstance().getReference("Owner");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) throws NullPointerException{

        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            if(ds.getValue(Owner.class).getMessName().equals(name))
            {
                userID=ds.getKey().toString();
            }
        }

        Owner owner= dataSnapshot.child(userID).getValue(Owner.class);

        messName.setText(owner.getMessName());
        messAddress.setText(owner.getAddress());
        messEmail.setText(owner.getEmail());
        messPhone.setText(owner.getPhone());
    }
}
