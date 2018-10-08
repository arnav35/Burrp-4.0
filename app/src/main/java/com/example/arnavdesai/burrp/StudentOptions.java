package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

public class StudentOptions extends AppCompatActivity implements View.OnClickListener{
    Button compMess,searchMess,searchLocality,nearby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_options);
        compMess=(Button) findViewById(R.id.compMess);
        searchMess=(Button) findViewById(R.id.searchBhaji);
        searchLocality=(Button) findViewById(R.id.searchLocality);
        nearby=(Button) findViewById(R.id.nearbyMess);
        nearby.setOnClickListener(this);
        compMess.setOnClickListener(this);
        searchLocality.setOnClickListener(this);
        searchMess.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if(v==compMess)
        {
            Intent intent=new Intent(StudentOptions.this, MessList.class);
            startActivity(intent);
        }
        if(v==searchMess)
        {
            Intent intent=new Intent(StudentOptions.this, Menu.class);
            startActivity(intent);
        }
        if(v==searchLocality)
        {
            Intent intent=new Intent(StudentOptions.this, SearchByLocality.class);
            startActivity(intent);
        }
        if(v==nearby)
        {
            Intent intent=new Intent(StudentOptions.this, nearbyMess.class);
            startActivity(intent);
        }
    }
}
