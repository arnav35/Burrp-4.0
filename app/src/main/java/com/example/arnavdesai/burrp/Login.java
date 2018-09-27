package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEdit,passEdit;
    private Button LoginButtOwner;
    private Button LoginButtStudent;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceOwner = FirebaseDatabase.getInstance().getReference("Owner");
    private DatabaseReference databaseReferenceStudent = FirebaseDatabase.getInstance().getReference("Student");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButtOwner=(Button) findViewById(R.id.LoginButtonOwner);
        LoginButtStudent = (Button) findViewById(R.id.LoginButtonStudent);
        nameEdit=(EditText) findViewById(R.id.LoginNameEdit);
        passEdit=(EditText) findViewById(R.id.LoginPasswordEdit);
        firebaseAuth=FirebaseAuth.getInstance();
        LoginButtOwner.setOnClickListener(this);
        LoginButtStudent.setOnClickListener(this);

    }



    public void LoginUserOwner(String name, String password) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password))
            Toast.makeText(Login.this,"email or password cannot be empty.",Toast.LENGTH_LONG).show();
        else if(password.length()<6)
            Toast.makeText(Login.this,"Password should contain at least 6 characters..",Toast.LENGTH_LONG).show();
        else if (!name.contains("@") && !name.contains("."))
            Toast.makeText(Login.this,"Please enter valid email.",Toast.LENGTH_LONG).show();
        else
        {
            firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        final String uid = firebaseUser.getUid();
                        databaseReferenceOwner.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(uid))
                                {
                                    Toast.makeText(Login.this, "Login successful ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, OwnerMenu.class);
                                    intent.putExtra("uid",uid);
                                    finish();
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this,"You are not a mess owner. Please login as a student",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login unsuccessful. Please register yourself.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View view)
    {
        if(view == LoginButtOwner)
        {
            LoginUserOwner(nameEdit.getText().toString().trim(), passEdit.getText().toString().trim());
        }

        if(view == LoginButtStudent)
        {
            LoginUserStudent(nameEdit.getText().toString().trim(), passEdit.getText().toString().trim());
        }
    }

    private void LoginUserStudent(String name, String password) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password))
            Toast.makeText(Login.this,"email or password cannot be empty.",Toast.LENGTH_LONG).show();
        else if(password.length()<6)
            Toast.makeText(Login.this,"Password should contain at least 6 characters..",Toast.LENGTH_LONG).show();
        else if (!name.contains("@") && !name.contains("."))
            Toast.makeText(Login.this,"Please enter valid email.",Toast.LENGTH_LONG).show();
        else
        {
            firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        final String uid = firebaseUser.getUid();
                        databaseReferenceStudent.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(uid))
                                {
                                    Toast.makeText(Login.this, "Login successful ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Menu.class);
                                    finish();
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Login.this,"You are not a Student. Please login as a Owner",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login unsuccessful. Please register yourself. ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
