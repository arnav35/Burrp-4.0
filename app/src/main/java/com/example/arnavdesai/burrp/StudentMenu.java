package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v4.view.MenuCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StudentMenu extends AppCompatActivity implements View.OnClickListener{

    private TextView messName,messAddress,messEmail,messPhone,ratingDisplay;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String userID,name;
    private RatingBar ratingBar;
    private float ratingValue;
    private Button sendRatingReview;
    private int countofStudent;
    private TextView[] itemNameText,itemPriceText,dailyMenu;
    private View linearLayout;
    private EditText reviewEdit;
    private Button btnComing;

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
        sendRatingReview=(Button) findViewById(R.id.sendReviewRating);
        linearLayout=(View) findViewById(R.id.activity_student_menu);
        reviewEdit=(EditText) findViewById(R.id.ReviewEdit);
        sendRatingReview.setOnClickListener(this);
        btnComing = (Button) findViewById(R.id.comingButton);

        btnComing.setOnClickListener(this);
        name=getIntent().getStringExtra("messName");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingDisplay.setText(String.valueOf(rating));
                ratingValue=rating;
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

        databaseReference=firebaseDatabase.getInstance().getReference("Daily Menu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showDaily(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference=firebaseDatabase.getInstance().getReference("Menu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showMenu(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showMenu(DataSnapshot dataSnapshot) {
        IndividualMenu obj=dataSnapshot.child(userID).getValue(IndividualMenu.class);

        List<String> Name=new ArrayList<String>();
        Name=obj.ItemName;
        List<String> Price=new ArrayList<String>();
        Price=obj.ItemPrice;

        int length=Name.size();
        itemNameText=new TextView[length];
        itemPriceText=new TextView[length];

        for(int i=0; i<length; i++)
        {
            itemNameText[i]=new TextView(this);
            itemPriceText[i]=new TextView(this);

            itemPriceText[i].setText(Price.get(i));
            itemNameText[i].setText(Name.get(i));

            itemNameText[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            itemPriceText[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            ((LinearLayout) linearLayout).addView(itemNameText[i]);
            ((LinearLayout) linearLayout).addView(itemPriceText[i]);
        }

    }

    private void showDaily(DataSnapshot dataSnapshot) throws NullPointerException {
            MenuCard obj=dataSnapshot.child(userID).getValue(MenuCard.class);

            int count;
            List<String> bhajiString=obj.getBhaji();
            count=bhajiString.size();

            dailyMenu=new TextView[count+1];

            for(int i=0; i<count; i++)
            {
                dailyMenu[i]=new TextView(this);

                dailyMenu[i].setText(bhajiString.get(i));
                dailyMenu[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                ((LinearLayout) linearLayout).addView(dailyMenu[i]);
            }
            dailyMenu[count]=new TextView(this);
            dailyMenu[count].setText("Price Menu");

            dailyMenu[count].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout) linearLayout).addView(dailyMenu[count]);

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

    public void addRating() throws NullPointerException
    {
        databaseReference=firebaseDatabase.getInstance().getReference("Rating");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                countofStudent=dataSnapshot.child(userID).getValue(RatingReview.class).getNoOfStudent();
                countofStudent=countofStudent+1;

                ratingValue=dataSnapshot.child(userID).getValue(RatingReview.class).getAvgRating();
                ratingValue=(ratingBar.getRating()+ratingValue)/countofStudent;

                Map<String,Object> mobj=new HashMap<String,Object>();
                RatingReview obj=new RatingReview(ratingValue,countofStudent);
                mobj.put(userID, obj);

                databaseReference=firebaseDatabase.getInstance().getReference();
                databaseReference.child("Rating").updateChildren(mobj);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        if(v == sendRatingReview)
        {
            addRating();
            addReview();
        }
        if (v== btnComing)
        {
            i_am_coming();
        }
    }
    private void i_am_coming() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Count");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Visited V = new Visited();
                V=dataSnapshot.child(name).getValue(com.example.arnavdesai.burrp.Visited.class);
                String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
                if(weekday_name.equals("Monday")) V.update(1);
                if(weekday_name.equals("Tuesday")) V.update(2);
                if(weekday_name.equals("Wednesday")) V.update(3);
                if(weekday_name.equals("Thurs")) V.update(4);
                if(weekday_name.equals("Friday")) V.update(5);
                if(weekday_name.equals("Saturday")) V.update(6);
                if(weekday_name.equals("Sunday")) V.update(7);
                databaseReference.child(name).setValue(V);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addReview() {
        databaseReference=firebaseDatabase.getInstance().getReference();
        databaseReference.child("Review").child(userID).setValue(reviewEdit.getText().toString());
        finish();
    }
}