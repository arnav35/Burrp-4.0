package com.example.arnavdesai.burrp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Menu extends AppCompatActivity implements View.OnClickListener {
    private EditText bhaji1,bhahi2,bhaji3;
    private Button save;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__menu);

        userID=getIntent().getStringExtra("uid");

        bhaji1=(EditText)findViewById(R.id.etbhaji1);
        bhahi2=(EditText)findViewById(R.id.etbhaji2);
        bhaji3=(EditText)findViewById(R.id.etbhaji3);
        save=(Button)findViewById(R.id.btsave);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getInstance().getReference("Daily Menu");
        save.setOnClickListener(this);
    }


    public void addToDatabase() throws  NullPointerException
    {
        MenuCard menuCard=new MenuCard(bhaji1.getText().toString().trim(), bhahi2.getText().toString().trim(), bhaji3.getText().toString().trim());

        databaseReference.child(userID).setValue(menuCard);
        finish();
    }
    @Override
    public void onClick(View view) {
        if(view==save){
            addToDatabase();
        }
    }

}
