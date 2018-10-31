package com.example.arnavdesai.burrp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.rating;
import static android.R.attr.y;
import static java.lang.String.valueOf;

public class SearchByLocality extends AppCompatActivity implements View.OnClickListener{

    String[] nameArray;
    String[] addressArray;
    String[] ratingArray;
    String[] ratingArrayFinal;
    ListView listview;
    private Spinner spinner;
    private Button search ;
    private TextView norecordsfound;
    private ImageView back,reFresh;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    private DatabaseReference referenceBhaji = FirebaseDatabase.getInstance().getReference("Daily Menu");
    int count=0;
    List<String> localities;
    String requestedLocality;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_locality);
        spinner = (Spinner) findViewById(R.id.spinner2);
        search = (Button) findViewById(R.id.buttonSearch);
        norecordsfound=(TextView)findViewById(R.id.norecord);
        search.setOnClickListener(this);
        back=(ImageView)findViewById(R.id.backLoca);
        reFresh=(ImageView)findViewById(R.id.refresh);
        back.setOnClickListener(this);
        reFresh.setOnClickListener(this);
        norecordsfound.setVisibility(View.INVISIBLE);
        localities = new ArrayList<String>();  // a list to be displayed in the spinner
        localities.add("select");
        localities.add("Sahakarnagar");
        localities.add("PVG College");
        localities.add("VIT College");

        adapter = new ArrayAdapter<String>(SearchByLocality.this, android.R.layout.simple_spinner_item, localities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //setting view of dropdown
        spinner.setAdapter(adapter);
    }

    public void functionOne() {

        databaseReference=FirebaseDatabase.getInstance().getReference("Owner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getCount(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
            ratingArray[i]= valueOf(ratingReview.getAvgRating());
            i++;
        }
        return ratingArray;
    }

    private void showData(DataSnapshot dataSnapshot) throws NullPointerException {

        nameArray=new String[count];
        addressArray=new String[count];
        ratingArrayFinal=new String[count];


        final int[] i = {0};
        final int[] total = {0};
        norecordsfound.setVisibility(View.VISIBLE);

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            final Owner owner = ds.getValue(Owner.class);

            if (owner.locality.equals(requestedLocality)) {  //if that owner's locality is requested.
                String key = ds.getKey();   //getting key for that owner to search in DailyMenu section.
                //DatabaseReference referenceToOwnerBhaji = referenceBhaji.child(key).child("bhaji");
                norecordsfound.setVisibility(View.INVISIBLE);

                DatabaseReference userNameRef = referenceBhaji.child(key);
                userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        MenuCard m = dataSnapshot.getValue(MenuCard.class);

                        nameArray[i[0]] = new String();
                        addressArray[i[0]] = new String();
                        ratingArrayFinal[i[0]] = new String();

                        nameArray[i[0]] = owner.getMessName();       //add to list that owner and address.
                        addressArray[i[0]] = owner.getAddress();
                        ratingArrayFinal[i[0]] = ratingArray[total[0]];
                        i[0]++;
                        total[0]++;
                    }

                /* Rating array has ratings of all owners. We want ratings of only those owners who have that bhaji.
                    So we are increasing value of total in each loop and inserting into final rating array
                      only values of ratings of required owners.*/

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                CustomListAdapter2 customListAdapter = new CustomListAdapter2(this, nameArray, addressArray, ratingArrayFinal);
                listview = (ListView) findViewById(R.id.listviewID);
                listview.setAdapter(customListAdapter);
                listview.setVisibility(View.VISIBLE);
            }
        }
    }

    void getCount(DataSnapshot dataSnapshot) throws NullPointerException {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            if(ds.getValue(Owner.class).locality.equals(requestedLocality))
            {
                count = count + 1;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == search){
            requestedLocality = spinner.getSelectedItem().toString();
            if(requestedLocality=="select"){
                listview.setVisibility(View.INVISIBLE);
                Toast.makeText(SearchByLocality.this,"Please choose the locality",Toast.LENGTH_SHORT).show();
            }
            else {//getting spinner value.

                functionOne();
                functionTwo();
            }

        }
        if(view==back)
        {
            Intent intent=new Intent(SearchByLocality.this, StudentOptions.class);
            startActivity(intent);
        }
        if(view==reFresh){
            listview.setVisibility(View.INVISIBLE);
            norecordsfound.setVisibility(View.INVISIBLE);
        }
    }
}