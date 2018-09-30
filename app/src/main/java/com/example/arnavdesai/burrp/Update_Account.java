
package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Update_Account extends AppCompatActivity implements View.OnClickListener{

    private Button updateMessName,updatePassword,updateMessAddress,updateContactNo;
    private String userId,messName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__account2);
        updateMessName=(Button)findViewById(R.id.upmessname);
        updateMessAddress=(Button)findViewById(R.id.upAddress);
        updateContactNo=(Button)findViewById(R.id.upcontactno);
        updatePassword=(Button)findViewById(R.id.upPassword);
        updateMessName.setOnClickListener(this);
        updateMessAddress.setOnClickListener(this);
        updateContactNo.setOnClickListener(this);
        updatePassword.setOnClickListener(this);
        userId=getIntent().getStringExtra("uid");
        messName=getIntent().getStringExtra("messName");
    }

    @Override
    public void onClick(View v) {
        if(v==updateMessName){
            Intent intent=new Intent(Update_Account.this,UpdateMessName.class);
            intent.putExtra("uid",userId);
            intent.putExtra("messName",messName);
            startActivity(intent);

        }
        if(v==updateMessAddress){
            Intent intent=new Intent(Update_Account.this,Update_Address.class);
            intent.putExtra("uid",userId);
            startActivity(intent);
        }
        if(v==updateContactNo){
            Intent intent=new Intent(Update_Account.this, Update_Contact.class);
            intent.putExtra("uid",userId);
            intent.putExtra("messName",messName);
            startActivity(intent);
        }
        if(v==updatePassword){
            Intent intent=new Intent(Update_Account.this, Update_Password.class);
            intent.putExtra("uid",userId);
            startActivity(intent);
        }
    }
}
