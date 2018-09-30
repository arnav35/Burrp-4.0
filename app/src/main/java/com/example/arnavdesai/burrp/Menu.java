package com.example.arnavdesai.burrp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.y;

public class Menu extends AppCompatActivity {

    String[] nameArray;
    String[] addressArray;
    String[] ratingArray;
    ListView listview;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        databaseReference=FirebaseDatabase.getInstance().getReference("Rating");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ratingArray=showRating(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference=FirebaseDatabase.getInstance().getReference("Owner");
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

    private String[] showRating(DataSnapshot dataSnapshot) throws NullPointerException {

        count=(int)dataSnapshot.getChildrenCount();
        ratingArray=new String[count];

        int i=0;
        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            RatingReview ratingReview=ds.getValue(RatingReview.class);
            ratingArray[i]=String.valueOf(ratingReview.getAvgRating());
            i++;
        }
        return ratingArray;
    }

    private void showData(DataSnapshot dataSnapshot) throws NullPointerException {

        count=(int) dataSnapshot.getChildrenCount();
        nameArray=new String[count];
        addressArray=new String[count];

        int i=0;

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Owner owner=ds.getValue(Owner.class);
            nameArray[i]=owner.getMessName();
            addressArray[i]=owner.getAddress();
            i++;
        }

        CustomListAdapter customListAdapter=new CustomListAdapter(this, nameArray, addressArray, ratingArray);
        listview=(ListView) findViewById(R.id.listviewID);
        listview.setAdapter(customListAdapter);
    }

    void getCount(DataSnapshot dataSnapshot) throws NullPointerException
    {
        count=(int)dataSnapshot.getChildrenCount();
    }
}