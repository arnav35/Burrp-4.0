package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.annotation.IntegerRes;
import android.support.v4.view.MenuCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class StudentMenu extends AppCompatActivity implements View.OnClickListener{

    private TextView messName,messAddress,messEmail,messPhone,ratingDisplay;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String userID,name;
    private RatingBar ratingBar;
    private float ratingValue;
    private Button sendRatingReview;
    private int countofStudent;
    private TextView[] itemNameText,itemPriceText,dailyMenu;
    private View linearLayout;
    private EditText reviewEdit;
    private Button btnComing,showLoc,showmenu1,showMenucard1,showReview;
    private ImageView back;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        showReview=(Button) findViewById(R.id.showReview);
        messName=(TextView) findViewById(R.id.MessNameText);
        messAddress=(TextView) findViewById(R.id.MessAddressText);
        messEmail=(TextView) findViewById(R.id.EmailText);
        messPhone=(TextView) findViewById(R.id.PhoneNumberText);
        ratingDisplay=(TextView) findViewById(R.id.RatingDisplay);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar2);
        sendRatingReview=(Button) findViewById(R.id.sendReviewRating);
        linearLayout=findViewById(R.id.scroll);
        reviewEdit=(EditText) findViewById(R.id.ReviewEdit);
        sendRatingReview.setOnClickListener(this);
        btnComing = (Button) findViewById(R.id.comingButton);
        showLoc=(Button) findViewById(R.id.showLocation);
        back=(ImageView) findViewById(R.id.back);
        showmenu1=(Button)findViewById(R.id.show_menu);
        showMenucard1=(Button)findViewById(R.id.show_menucrd);


        back.setOnClickListener(this);
        showLoc.setOnClickListener(this);
        btnComing.setOnClickListener(this);
        showmenu1.setOnClickListener(this);
        showMenucard1.setOnClickListener(this);
        showReview.setOnClickListener(this);

        name=getIntent().getStringExtra("messName");
        id=Integer.parseInt(getIntent().getStringExtra("id"));

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
                userID=showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*databaseReference=firebaseDatabase.getInstance().getReference("Daily Menu");
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

        databaseReference=firebaseDatabase.getInstance().getReference("Review");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showReview(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void showReview(DataSnapshot dataSnapshot) {

            int count=(int)dataSnapshot.child(userID).getChildrenCount();

            TextView[] review = new TextView[count + 1];

            review[0] = new TextView(this);
            review[0].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                review[0].setText("Reviews");
            ((LinearLayout) linearLayout).addView(review[0]);
            int j=1;

            for(DataSnapshot ds: dataSnapshot.child(userID).getChildren())
            {
                review[j]=new TextView(this);
                review[j].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                review[j].setText(ds.getValue(String.class));
                ((LinearLayout) linearLayout).addView(review[j]);
                j++;
            }
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


    private String showData(DataSnapshot dataSnapshot) throws NullPointerException{

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
        return userID;
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
        if (v == showLoc)
        {
            Intent intent=new Intent(StudentMenu.this, MessLocation.class);
            intent.putExtra("uid",userID);
            startActivity(intent);
        }
        if(v==showmenu1){
            Intent intent=new Intent(StudentMenu.this,Ownerbhajitostudent.class);
            intent.putExtra("uid",name);
            startActivity(intent);
        }
        if(v==showMenucard1){
            Intent intent=new Intent(StudentMenu.this,OwnermenutoStudent.class);
            intent.putExtra("uid",name);
            startActivity(intent);
        }
        if(v==showReview)
        {
            Intent intent=new Intent(this, showReview.class);
            intent.putExtra("uid",name);
            startActivity(intent);
        }
        if(v==back)
        {
            if(id==1){
                Intent intent=new Intent(StudentMenu.this, MessList.class);
                startActivity(intent);
            }
            if(id==2)
            {
                Intent intent=new Intent(StudentMenu.this, Menu.class);
                startActivity(intent);
            }
        }
    }

    private void i_am_coming() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Count");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Visited V = new Visited();
                V=dataSnapshot.child(name).getValue(Visited.class);
                String weekday_name = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
                Calendar cal= Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMM"); //Short month name e.g. 'Jan'. Use "MMMM" for full name
                String month_name = month_date.format(cal.getTime());  //getting month name.
                V.update(weekday_name,month_name);       //increasing day and month count.
                databaseReference.child(name).setValue(V);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addReview() {
        databaseReference=firebaseDatabase.getInstance().getReference();
        databaseReference.child("Review").child(userID).push().setValue(reviewEdit.getText().toString());
        finish();
    }
}