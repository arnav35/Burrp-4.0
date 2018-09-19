package com.example.arnavdesai.burrp;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arnavdesai.burrp.R;

import static android.R.attr.start;

/**
 * Created by Arnav Desai on 9/7/2018.
 */

public class CustomListAdapter extends ArrayAdapter{
    private final Activity context;
    private final String[] nameArray;
    private final String[] addressArray;
    private RatingBar ratingBar;

    public CustomListAdapter(Activity context, String[] nameArray, String[] addressArray)
    {
        super(context, R.layout.listview_row, nameArray);

        this.context=context;
        this.nameArray=nameArray;
        this.addressArray=addressArray;
    }

    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView =inflater.inflate(R.layout.listview_row, null, true);
        ratingBar=(RatingBar) rowView.findViewById(R.id.ratingBar);

        ratingBar.setRating(Float.parseFloat("4.2"));

        Button nameButton =(Button) rowView.findViewById(R.id.button2);
        TextView addressTextfield =(TextView) rowView.findViewById(R.id.addressTextfield);

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Messy", "clicked");
            }
        });

        nameButton.setText(nameArray[position]);
        addressTextfield.setText(addressArray[position]);

        return rowView;
    }

}