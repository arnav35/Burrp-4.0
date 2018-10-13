package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Build.VERSION_CODES.O;

public class OwnerSignup extends AppCompatActivity implements View.OnClickListener{

    private EditText name,messName,address,email,phone,password;
    private Button ownerBut;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    private  String userID;
    private Spinner spinner;              //getting a locality chooser spinner
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_signup);
        name=(EditText) findViewById(R.id.OwnerNameEdit);
        messName=(EditText) findViewById(R.id.OwnerMessEdit);
        address=(EditText) findViewById(R.id.OwnerAddressEdit);
        email=(EditText) findViewById(R.id.OwnerEmailEdit);
        phone=(EditText) findViewById(R.id.OwnerPhoneEdit);
        password=(EditText) findViewById(R.id.OwnerPasswordEdit);
        spinner = (Spinner) findViewById(R.id.spinner);
        ownerBut=(Button) findViewById(R.id.OwnerLoginButton);

        firebaseAuth=FirebaseAuth.getInstance();

        ownerBut.setOnClickListener(this);
        List<String> localities = new ArrayList<String>();  // a list to be displayed in the spinner
        localities.add("Sahakarnagar");
        localities.add("PVG College");
        localities.add("VIT College");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, localities);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //setting view of dropdown
        spinner.setAdapter(dataAdapter);
    }


    public void ownerSignup(final String email, final String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(OwnerSignup.this, "Signup successful" ,Toast.LENGTH_SHORT).show();
                    firebaseAuth.signInWithEmailAndPassword(email, password); //Signed in to set uid to object in firebase.
                    owneradd();
                }
                if(!task.isSuccessful())
                {
                    Toast.makeText(OwnerSignup.this, "Signup unsuccessful. Email already exists." ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void owneradd() throws NullPointerException
    {
        Owner owner=new Owner(name.getText().toString().trim(), messName.getText().toString().trim(), address.getText().toString().trim(), email.getText().toString().trim(), phone.getText().toString().trim(), password.getText().toString().trim(),spinner.getSelectedItem().toString());
        //set the owner object its locality through constructor.

        Visited V = new Visited();   //empty visited class.

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        databaseReference.child("Owner").child(uid).setValue(owner);
        databaseReference.child("Count").child(messName.getText().toString().trim()).setValue(V); //setting empty visited class.

        RatingReview obj=new RatingReview(0,0);
        databaseReference.child("Rating").child(uid).setValue(obj);

        Intent intent=new Intent(OwnerSignup.this, OwnerMenu.class);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }



    @Override
    public void onClick(View view)
    {
        if(view == ownerBut)
        {
            ownerSignup(email.getText().toString().trim(), password.getText().toString().trim());
        }
    }
}