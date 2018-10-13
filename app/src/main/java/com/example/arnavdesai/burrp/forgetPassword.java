package com.example.arnavdesai.burrp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetPassword extends AppCompatActivity implements View.OnClickListener{
    private EditText emailAddress;
    private Button resetPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        emailAddress=(EditText) findViewById(R.id.emailinput);
        resetPassword=(Button)findViewById(R.id.resetPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        resetPassword.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v==resetPassword){
            resetpassword();
        }
    }

    private void resetpassword() {
        email=emailAddress.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(forgetPassword.this,"Enter Your Register emailid ",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgetPassword.this,"We have sent you instructions to reset your activity",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(forgetPassword.this,"Failed To send reset email",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
