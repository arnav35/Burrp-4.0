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

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEdit,passEdit;
    private Button LoginButt;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButt=(Button) findViewById(R.id.LoginButton);
        nameEdit=(EditText) findViewById(R.id.LoginNameEdit);
        passEdit=(EditText) findViewById(R.id.LoginPasswordEdit);
        firebaseAuth=FirebaseAuth.getInstance();

        LoginButt.setOnClickListener(this);
    }
    public void LoginUser(String name, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(name, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this, "Login successful ",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Login.this, Menu.class);
                    startActivity(intent);
                }
                if(!task.isSuccessful())
                {
                    Toast.makeText(Login.this, "Login unsuccessful ",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view)
    {
        if(view == LoginButt)
        {
            LoginUser(nameEdit.getText().toString().trim(), passEdit.getText().toString().trim());
        }
    }
}
