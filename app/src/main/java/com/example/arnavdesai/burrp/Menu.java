package com.example.arnavdesai.burrp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Menu extends AppCompatActivity {

    String[] nameArray;
    String[] addressArray;
    String[] ratingArray={"4.2"};
    ListView listview;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Owner");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        nameArray=new String[10];
        addressArray=new String[10];

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CustomListAdapter customListAdapter=new CustomListAdapter(this, nameArray, addressArray, ratingArray);
        listview=(ListView) findViewById(R.id.listviewID);
        listview.setAdapter(customListAdapter);
    }

    private void showData(DataSnapshot dataSnapshot) throws NullPointerException {
        int i=0;
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Owner owner=ds.getValue(Owner.class);
            nameArray[i]=owner.getMessName();
            addressArray[i]=owner.getAddress();
            break;
        }
    }
}