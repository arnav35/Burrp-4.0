package com.example.arnavdesai.burrp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.R.attr.rating;
import static android.R.attr.y;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    String[] nameArray;
    String[] addressArray;
    String[] ratingArray;
    String[] ratingArrayFinal;
    ListView listview;
    private EditText bhajiEdit;
    private Button search ;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    private DatabaseReference referenceBhaji = FirebaseDatabase.getInstance().getReference("Daily Menu");
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bhajiEdit = (EditText) findViewById(R.id.editbhaji);
        search = (Button) findViewById(R.id.buttonSearch);
        search.setOnClickListener(this);
    }

    public void functionOne() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Rating");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ratingArray = showRating(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void functionTwo() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Owner");
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
        ratingArrayFinal=new String[count];
        final String requestedBhaji = bhajiEdit.getText().toString();

        final int[] i = {0};
        final int[] total = {0};

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            final Owner owner=ds.getValue(Owner.class);
            String key = ds.getKey();   //getting key for that owner to search in DailyMenu section.
            //DatabaseReference referenceToOwnerBhaji = referenceBhaji.child(key).child("bhaji");
            DatabaseReference userNameRef = referenceBhaji.child(key);
            userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MenuCard m = dataSnapshot.getValue(MenuCard.class);

                    nameArray[i[0]]=new String();
                    addressArray[i[0]]=new String();
                    ratingArrayFinal[i[0]]=new String();

                    List<String> ownerBhaji = (List<String>) m.getBhaji();   //getting bhaji of that owner.

                    if(ownerBhaji.contains(requestedBhaji)){              //if that string contains requested bhaji
                        nameArray[i[0]]=owner.getMessName();       //add to list that owner and address.
                        addressArray[i[0]]=owner.getAddress();
                        ratingArrayFinal[i[0]] = ratingArray[total[0]];
                        i[0]++;
                    }
                    total[0]++;
                }

                /* Rating array has ratings of all owners. We want ratings of only those owners who have that bhaji.
                    So we are increasing value of total in each loop and inserting into final rating array
                      only values of ratings of required owners.*/

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        CustomListAdapter customListAdapter=new CustomListAdapter(this, nameArray, addressArray, ratingArrayFinal);
        listview=(ListView) findViewById(R.id.listviewID);
        listview.setAdapter(customListAdapter);
    }

    void getCount(DataSnapshot dataSnapshot) throws NullPointerException
    {
        count=(int)dataSnapshot.getChildrenCount();
    }

    @Override
    public void onClick(View view) {
        if(view == search){
            functionOne();
            functionTwo();
        }
    }
}