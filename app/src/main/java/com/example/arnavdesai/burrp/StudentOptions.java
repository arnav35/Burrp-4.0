package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

public class StudentOptions extends AppCompatActivity implements View.OnClickListener{
    Button compMess,searchMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_options);
        compMess=(Button) findViewById(R.id.compMess);
        searchMess=(Button) findViewById(R.id.searchBhaji);
        compMess.setOnClickListener(this);
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
    }
}
