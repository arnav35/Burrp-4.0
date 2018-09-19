package com.example.arnavdesai.burrp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.R.attr.data;
import static android.R.attr.value;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.os.Build.VERSION_CODES.O;

public class OwnerMenu extends AppCompatActivity {

    private TextView messName,messAddress,messEmail,messPhone;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);
        messName=(TextView) findViewById(R.id.MessNameText);
        messAddress=(TextView) findViewById(R.id.MessAddressText);
        messEmail=(TextView) findViewById(R.id.EmailText);
        messPhone=(TextView) findViewById(R.id.PhoneNumberText);
        userID=getIntent().getStringExtra("messName");
        Log.d("Burrp",userID);


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
        //for(DataSnapshot ds: dataSnapshot.getChildren()){
            Owner owner= dataSnapshot.child(userID).getValue(Owner.class);

            messName.setText(owner.getMessName());
            messAddress.setText(owner.getAddress());
            messEmail.setText(owner.getEmail());
            messPhone.setText(owner.getPhone());
        //}
    }
}