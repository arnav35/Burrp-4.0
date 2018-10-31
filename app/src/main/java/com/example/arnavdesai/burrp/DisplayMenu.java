package com.example.arnavdesai.burrp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.arnavdesai.burrp.R.id.messname;


public class DisplayMenu extends AppCompatActivity implements View.OnClickListener{
    private Button updateMenu;
    private TextView menu1,price1,menu2,price2,menu3,price3;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Menu");
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_menu);

        userID=getIntent().getStringExtra("uid");

        updateMenu=(Button) findViewById(R.id.updateMenu);
        menu1=(TextView) findViewById(R.id.menu1);
        menu2=(TextView) findViewById(R.id.menu2);
        menu3=(TextView) findViewById(R.id.menu3);

        price1=(TextView) findViewById(R.id.price1);
        price2=(TextView) findViewById(R.id.price2);
        price3=(TextView) findViewById(R.id.price3);

        updateMenu.setOnClickListener(this);

        databaseReference=FirebaseDatabase.getInstance().getReference("Menu");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showMenu(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showMenu(DataSnapshot dataSnapshot) throws NullPointerException{

        IndividualMenu menu = dataSnapshot.child(userID).getValue(IndividualMenu.class);

        List<String> itemName, itemPrice;
        itemName = menu.ItemName;
        itemPrice = menu.ItemPrice;
        int count = itemName.size();
        int i = 0;

        if (i < count) {
            menu1.setText(itemName.get(i));
            price1.setText(itemPrice.get(i));
            i++;
        }
        if (i < count) {
            menu2.setText(itemName.get(i));
            price2.setText(itemPrice.get(i));
            i++;
        }
        if (i < count) {
            menu3.setText(itemName.get(i));
            price3.setText(itemPrice.get(i));
            i++;
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v==updateMenu)
        {
            Intent intent=new Intent(DisplayMenu.this, addMenu.class);
            intent.putExtra("uid",userID);
            startActivity(intent);
        }
    }
}
