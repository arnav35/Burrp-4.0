package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import static com.example.arnavdesai.burrp.R.id.messname;
import static com.example.arnavdesai.burrp.R.id.showGraph;

public class OwnerMenu extends AppCompatActivity implements View.OnClickListener{

    private TextView messName,messAddress,messEmail,messPhone;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private String userID;
    private Button addmenu,addDailyMenu,addLocation,logout;
    private int progress=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_menu);

        messName=(TextView) findViewById(R.id.MessNameText);
        messAddress=(TextView) findViewById(R.id.MessAddressText);
        messEmail=(TextView) findViewById(R.id.EmailText);
        messPhone=(TextView) findViewById(R.id.PhoneNumberText);
        addmenu=(Button) findViewById(R.id.addMenuItem);
        addDailyMenu=(Button) findViewById(R.id.addDailyMenu);
        addLocation=(Button) findViewById(R.id.add_location);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        logout=(Button)findViewById(R.id.logout);

        addDailyMenu.setOnClickListener(this);
        addmenu.setOnClickListener(this);
        addLocation.setOnClickListener(this);
        logout.setOnClickListener(this);

        addDailyMenu.setVisibility(View.INVISIBLE);
        addLocation.setVisibility(View.INVISIBLE);
        logout.setVisibility(View.INVISIBLE);

        progressBar.setMax(100);
        progressBar.setProgress(1);
        progressBar.setVisibility(View.VISIBLE);

        userID=getIntent().getStringExtra("uid");
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

    }


    private void showData(DataSnapshot dataSnapshot) throws NullPointerException{

            Owner owner= dataSnapshot.child(userID).getValue(Owner.class);

            messName.setText(owner.getMessName());
            messAddress.setText(owner.getAddress());
            messEmail.setText(owner.getEmail());
            messPhone.setText(owner.getPhone());

        Calendar calendar = Calendar.getInstance();    //getting current date. if it is 2, we reset the visit stats
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        if(thisDay==2)
        {
            Visited V = new Visited();
            DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Count");
            databaseReference1.child(owner.getMessName()).setValue(V);
        }

    }


    @Override
    public void onClick(View v)
    {
        int pro = 34;

        if(v == addmenu)
        {
            progressBar.setProgress(pro);
            Intent intent=new Intent(OwnerMenu.this, addMenu.class);
            intent.putExtra("uid", userID);
            startActivity(intent);
            addDailyMenu.setVisibility(View.VISIBLE);
            addmenu.setVisibility(View.INVISIBLE);

            if(progressBar.getProgress()==100)
            {
                logout.setVisibility(View.VISIBLE);
                Toast.makeText(OwnerMenu.this, "Signup Successful Login Again Please",Toast.LENGTH_SHORT).show();
            }

        }
        if(v == addDailyMenu)
        {
            pro = pro + 33;
            progressBar.setProgress(pro);
            Intent intent=new Intent(OwnerMenu.this, add_dailyMenu.class);
            intent.putExtra("uid",userID);
            startActivity(intent);
            addLocation.setVisibility(View.VISIBLE);
            addDailyMenu.setVisibility(View.INVISIBLE);
            if(progressBar.getProgress()==100)
            {
                logout.setVisibility(View.VISIBLE);
                Toast.makeText(OwnerMenu.this, "Signup Successful Login Again Please",Toast.LENGTH_SHORT).show();
            }

        }
        if(v == addLocation)
        {
            pro = pro + 66;
            progressBar.setProgress(pro);
            Intent intent=new Intent(OwnerMenu.this, Marker.class);
            intent.putExtra("messName",messName.getText().toString().trim());
            intent.putExtra("uid",userID);
            startActivity(intent);
            addLocation.setVisibility(View.INVISIBLE);
            startActivity(intent);
            if(progressBar.getProgress()==100) {
                logout.setVisibility(View.VISIBLE);
                Toast.makeText(OwnerMenu.this, "Signup Successful Login Again Please", Toast.LENGTH_SHORT).show();
            }
        }
        if(v == logout)
        {
            Intent mintent=new Intent(OwnerMenu.this, MainActivity.class);
            startActivity(mintent);
        }
    }
}