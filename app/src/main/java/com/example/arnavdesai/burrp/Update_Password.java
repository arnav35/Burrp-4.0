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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Update_Password extends AppCompatActivity implements View.OnClickListener {
    private Button savePassword;
    private EditText oldPassword, newPassword, confirmPassword;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String oldPassword1,newPassword1,confirmpassword1;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__password);
        savePassword = (Button) findViewById(R.id.SavePassword);
        oldPassword = (EditText) findViewById(R.id.oldPassword);
        newPassword = (EditText) findViewById(R.id.newPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        uid = getIntent().getStringExtra("uid");
      //  Log.d("old:",String.valueOf(oldPassword))
        //Log.d("new",String.valueOf(newPassword));
        //Log.d("confirm",String.valueOf(confirmPassword));
        savePassword.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == savePassword) {
            updatePassword();
        }
    }

    private void updatePassword() throws NullPointerException{

        oldPassword1=oldPassword.getText().toString();
        newPassword1=newPassword.getText().toString();
        confirmpassword1=confirmPassword.getText().toString();

        Log.d("notescreen","this is my text:"+oldPassword1);
        Log.d("notescreen","this is my text:"+newPassword1);
        Log.d("notescreen","this is my textgg:"+confirmpassword1);
        boolean var1=confirmpassword1.equalsIgnoreCase(newPassword1);

        if(var1) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword1);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPassword1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Update_Password.this, "Something Went wrong,plese try again later", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Update_Password.this, "Password SuccessFully modified", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });

                    } else {
                        Toast.makeText(Update_Password.this, "Authentication failed", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        else{

            Toast.makeText(Update_Password.this, "Re-entered password doesn't matched", Toast.LENGTH_SHORT).show();
        }

    }
}
