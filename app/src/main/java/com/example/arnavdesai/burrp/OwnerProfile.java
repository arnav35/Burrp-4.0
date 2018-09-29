
package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class OwnerProfile extends AppCompatActivity implements View.OnClickListener {

    private TextView messName, messAddress, messPhone,ownerName;
    private FirebaseDatabase firebaseDatabase,firebaseDatabase1;
    private FirebaseAuth firebaseAuth;
    private TextView bhaji1,bhaji2,bhaji3;
    private DatabaseReference databaseReference,databaseReference1;
    private FirebaseUser firebaseUser;
    private String userID,messname;
    private Button update, logout,messList,menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile);



        messName = (TextView) findViewById(R.id.messname);
        messAddress = (TextView) findViewById(R.id.messaddress);
        messPhone = (TextView) findViewById(R.id.messcontact);
        ownerName=(TextView)findViewById(R.id.etName);
        menu=(Button) findViewById(R.id.menucard);
        update = (Button) findViewById(R.id.update_menu);
        logout = (Button) findViewById(R.id.log_out);
        messList=(Button)findViewById(R.id.MessList);
        update.setOnClickListener(this);
        logout.setOnClickListener(this);
        messList.setOnClickListener(this);
        menu.setOnClickListener(this);
        bhaji1=(TextView)findViewById(R.id.bhaji1);
        bhaji2=(TextView)findViewById(R.id.bhaji2);
        bhaji3=(TextView)findViewById(R.id.bhaji3);

        userID=getIntent().getStringExtra("uid");

        firebaseAuth=FirebaseAuth.getInstance();
        //FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        databaseReference=firebaseDatabase.getInstance().getReference("Owner");

        databaseReference1=firebaseDatabase.getInstance().getReference("Daily Menu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData1(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void showData1(DataSnapshot dataSnapshot)throws NullPointerException{


        MenuCard obj=dataSnapshot.child(userID).getValue(MenuCard.class);

        int count;
        List<String> bhajiString=obj.getBhaji();
        count=bhajiString.size();

        //dailyMenu=new TextView[count];

        int i=0;

        if(i<=count) {
            bhaji1.setText(bhajiString.get(0));
            i++;
        }
        if(i<=count) {
            bhaji2.setText(bhajiString.get(1));
            i++;
        }
        if(i<count) {
            bhaji3.setText(bhajiString.get(2));
            i++;
        }
    }

    private void showData(DataSnapshot dataSnapshot) throws NullPointerException{

        Owner owner= dataSnapshot.child(userID).getValue(Owner.class);
        MenuCard menuCard=dataSnapshot.child(userID).getValue(MenuCard.class);
        messName.setText(owner.getMessName());
        messname=owner.getMessName();
        messAddress.setText(owner.getAddress());
        ownerName.setText(owner.getName());
        //messEmail.setText(owner.getEmail());
        messPhone.setText(owner.getPhone());
        //  bhaji1.setText(Menu);

    }
    public void onClick(View v){
        if(update==v){
            Intent intent=new Intent(OwnerProfile.this, add_dailyMenu.class);
            //  intent.putExtra("messName", messName.getText().toString().trim());
            intent.putExtra("uid",userID);
            startActivity(intent);
        }
        else if(messList==v){
            Intent intent=new Intent(OwnerProfile.this, Menu.class);
            startActivity(intent);
        }
        else if(menu==v){
            Intent intent=new Intent(OwnerProfile.this, DisplayMenu.class);
            intent.putExtra("messname",messname);
            startActivity(intent);

        }
        else if(logout==v){
            Intent intent=new Intent(OwnerProfile.this,MainActivity.class);
            startActivity(intent);
        }
    }
}
