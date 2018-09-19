package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserType extends AppCompatActivity implements View.OnClickListener{

    private Button studentBut,ownerBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);
        studentBut=(Button)findViewById(R.id.studentBut);
        ownerBut=(Button) findViewById(R.id.ownerBut);

        studentBut.setOnClickListener(this);
        ownerBut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if(view == studentBut)
        {
            Intent intent=new Intent(UserType.this, Signup.class);
            startActivity(intent);
        }
        if(view == ownerBut)
        {
            Intent intent =new Intent(UserType.this, OwnerSignup.class);
            startActivity(intent);
        }
    }
}
