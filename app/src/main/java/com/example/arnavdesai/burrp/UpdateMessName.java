package com.example.arnavdesai.burrp;

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

public class UpdateMessName extends AppCompatActivity implements View.OnClickListener {

    private EditText newMessName;
    private Button saveMessName;
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String uid,messName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mess_name);
        newMessName=(EditText)findViewById(R.id.newMessNme);
        saveMessName=(Button)findViewById(R.id.Savemess);
        saveMessName.setOnClickListener(this);
        uid=getIntent().getStringExtra("uid");
        messName=getIntent().getStringExtra("messName");
/*        databaseReference=FirebaseDatabase.getInstance().getReference("Owner");

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/



    }

    @Override
    public void onClick(View v) {
        if(v==saveMessName){
            addtodatabase();
        }
    }
    private void addtodatabase() {

        databaseReference=FirebaseDatabase.getInstance().getReference("Owner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Owner owner=dataSnapshot.child(uid).getValue(Owner.class);
                owner.setMessName(newMessName.getText().toString().trim());

                HashMap<String, Object> result = new HashMap<>();
                result.put(uid, owner);

                FirebaseDatabase.getInstance().getReference("Owner").updateChildren(result);
                finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
