package com.example.arnavdesai.burrp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.arnavdesai.burrp.R.drawable.c;
import static java.lang.String.valueOf;

public class messOfMonth extends AppCompatActivity {

    private DatabaseReference databaseReferenceCount= FirebaseDatabase.getInstance().getReference("Count");
    private DatabaseReference referenceOwner = FirebaseDatabase.getInstance().getReference("Owner");
    private TextView name;
    private TextView locality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_of_month);
        name = (TextView) findViewById(R.id.topMessname);
        locality = (TextView) findViewById(R.id.topLocality);
        final String[] top = {""};


        databaseReferenceCount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Calendar cal =  Calendar.getInstance();
                cal.add(Calendar.MONTH, -1);  //getting previous month name
                String previousMonth  = new SimpleDateFormat("MMM").format(cal.getTime());
                int count = 0;

                for(DataSnapshot ds: dataSnapshot.getChildren())  //iterating through count section
                {
                    Visited v = new Visited();
                    v= ds.getValue(Visited.class);
                    String messName = (String) ds.getKey();  //getting mess name for currently iterating mess
                    //int c = v.count(previousMonth);      //getting count of that mess for previous month

                    if(c>count){           // if it is greater than previous largest count value
                        count = c;
                        top[0] = messName;
                    }
                }
                name.setText(top[0]);         //mess name is the top mess name.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        referenceOwner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Owner owner = new Owner();
                for(DataSnapshot ds : dataSnapshot.getChildren())   //iterating through owner section
                {
                    owner = ds.getValue(Owner.class);
                    if(owner.getMessName()== top[0])      //if that owner's mess name is the top mess
                    {
                        locality.setText(owner.locality);    //select its locality in text view.
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
