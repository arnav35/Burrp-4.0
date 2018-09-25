package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Signup extends AppCompatActivity implements View.OnClickListener{

    private Button SignupButt;
    private EditText Name,College,Phone,Email,Password;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        SignupButt=(Button) findViewById(R.id.signupButton);
        firebaseAuth=FirebaseAuth.getInstance();

        Name=(EditText) findViewById(R.id.NameEdit);
        College=(EditText) findViewById(R.id.CollegeEdit);
        Phone=(EditText) findViewById(R.id.PhoneEdit);
        Email=(EditText) findViewById(R.id.EmailEdit);
        Password=(EditText) findViewById(R.id.PasswordEdit);

        SignupButt.setOnClickListener(this);
    }

    /*public void createUser(String email, String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Signup.this, "Signup Successful Hello "+Name.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Signup.this, "Signup Failed ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addToDatabase() throws  NullPointerException
    {
        Student student=new Student(Name.getText().toString().trim(), College.getText().toString().trim(), Phone.getText().toString().trim(), Email.getText().toString().trim(), Password.getText().toString().trim());

        databaseReference.child("Student").push().setValue(student);
        finish();
    }

    @Override
    public void onClick(View view)
    {
        if(view == SignupButt)
        {
            createUser(Email.getText().toString().trim(), Password.getText().toString().trim());
            addToDatabase();
        }
    }
}*/
    public void createUser(final String email, final String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Signup.this, "Signup Successful Hello "+Name.getText().toString(),Toast.LENGTH_SHORT).show();
                    firebaseAuth.signInWithEmailAndPassword(email, password);
                    addToDatabase();
                }
                else
                {
                    Toast.makeText(Signup.this, "Signup Failed. Email already exists. ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void addToDatabase() throws  NullPointerException
    {
        Student student=new Student(Name.getText().toString().trim(), College.getText().toString().trim(), Phone.getText().toString().trim(), Email.getText().toString().trim(), Password.getText().toString().trim());
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        databaseReference.child("Student").child(uid).setValue(student);
        databaseReference.child("AllUsers").child(uid).setValue(student);

        Intent intent=new Intent(Signup.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        if(view == SignupButt)
        {
            createUser(Email.getText().toString().trim(), Password.getText().toString().trim());
        }
    }
}

