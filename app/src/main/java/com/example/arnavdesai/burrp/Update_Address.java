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

import static com.example.arnavdesai.burrp.R.id.addLocation;

public class Update_Address extends AppCompatActivity implements View.OnClickListener {

    private EditText newMessAddress;
    private Button updateLocation;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String userId, messName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__address);

        userId = getIntent().getStringExtra("uid");
        messName = getIntent().getStringExtra("messName");
        updateLocation = (Button) findViewById(R.id.add_location);
        updateLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == updateLocation) {
            Intent intent = new Intent(Update_Address.this, Marker.class);
            intent.putExtra("messName", messName);
            intent.putExtra("uid", userId);
            startActivity(intent);
        }
    }
}