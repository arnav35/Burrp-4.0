
package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateActivity_Owner extends AppCompatActivity implements View.OnClickListener {

    private Button updateMenu,updateAccount;
    String userId,messName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__owner2);
        updateAccount=(Button)findViewById(R.id.update_details);
        updateMenu=(Button)findViewById(R.id.update_menu);
        updateMenu.setOnClickListener(this);
        updateAccount.setOnClickListener(this);

        userId=getIntent().getStringExtra("uid");
        messName=getIntent().getStringExtra("messName");
    }



    @Override
    public void onClick(View v) {
        if(v==updateMenu){
            Intent intent1=new Intent(UpdateActivity_Owner.this,add_dailyMenu.class);
            intent1.putExtra("uid",userId);
            startActivity(intent1);
        }
        if(v==updateAccount){
            Intent intent=new Intent(UpdateActivity_Owner.this,Update_Account.class);
            intent.putExtra("uid",userId);
            intent.putExtra("messName",messName);
            startActivity(intent);
        }
    }
}
