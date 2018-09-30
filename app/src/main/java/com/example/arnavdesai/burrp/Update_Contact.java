package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Update_Contact extends AppCompatActivity implements View.OnClickListener {

    private EditText messContact;
    private Button saveContact;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__contact);
        messContact=(EditText)findViewById(R.id.newContact);
        saveContact=(Button)findViewById(R.id.SaveContact);
        saveContact.setOnClickListener(this);
        uid= getIntent().getStringExtra("uid");
    }

    @Override
    public void onClick(View v) {
        if(v==saveContact){
            addtodatabase();
        }
    }

    private void addtodatabase() {
        databaseReference=FirebaseDatabase.getInstance().getReference("Owner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Owner owner=dataSnapshot.child(uid).getValue(Owner.class);
                owner.setPhone(messContact.getText().toString().trim());
                HashMap<String,Object>result=new HashMap<>();
                result.put(uid,owner);
                FirebaseDatabase.getInstance().getReference("Owner").updateChildren(result);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
