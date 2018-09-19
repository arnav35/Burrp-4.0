package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button LoginButt,SignupButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginButt=(Button) findViewById(R.id.LoginButt);
        SignupButt=(Button) findViewById(R.id.SignupButt);

        LoginButt.setOnClickListener(this);
        SignupButt.setOnClickListener(this);
    }
    @Override
    public void onClick(View view)
    {
        if(view == LoginButt)
        {
            Intent intent=new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        if(view == SignupButt)
        {
            Intent intent=new Intent(MainActivity.this, UserType.class);
            startActivity(intent);
        }
    }
}
