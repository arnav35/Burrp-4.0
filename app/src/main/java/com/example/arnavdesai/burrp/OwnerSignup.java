package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.Build.VERSION_CODES.O;

public class OwnerSignup extends AppCompatActivity implements View.OnClickListener{

    private EditText name,messName,address,email,phone,password;
    private Button ownerBut;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Owner");
    private  String userID;

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
        ownerBut=(Button) findViewById(R.id.OwnerLoginButton);

        firebaseAuth=FirebaseAuth.getInstance();

        ownerBut.setOnClickListener(this);
    }

    public void ownerSignup(String email, String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(OwnerSignup.this, "Signup successful" ,Toast.LENGTH_SHORT).show();
                }
                if(!task.isSuccessful())
                {
                    Toast.makeText(OwnerSignup.this, "Signup unsuccessful" ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void owneradd() throws NullPointerException
    {
        Owner owner=new Owner(name.getText().toString().trim(), messName.getText().toString().trim(), address.getText().toString().trim(), email.getText().toString().trim(), phone.getText().toString().trim(), password.getText().toString().trim());
        userID=databaseReference.push().getKey();
        Log.d("Burrp",userID);
        databaseReference.child(userID).setValue(owner);

        Intent intent=new Intent(OwnerSignup.this, OwnerMenu.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        if(view == ownerBut)
        {
            ownerSignup(email.getText().toString().trim(), password.getText().toString().trim());
            owneradd();
        }
    }
}
