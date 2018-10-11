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

import static android.R.attr.factor;
import static android.R.attr.fillAfter;
import static android.R.attr.rating;
import static android.R.attr.start;
import static com.example.arnavdesai.burrp.R.id.addressTextfield;


public class CustomListAdapter extends ArrayAdapter{
    private final Activity context;

    private final String[] nameArray;
    private final String[] addressArray;
    private final String[] ratingArray;
    private RatingBar ratingBar;

    public CustomListAdapter(Activity context, String[] nameArray, String[] addressArray, String[] ratingArray)
    {
        super(context, R.layout.listview_row, nameArray);

        this.context=context;
        this.nameArray=nameArray;
        this.addressArray=addressArray;
        this.ratingArray=ratingArray;
    }

    public View getView(final int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView =inflater.inflate(R.layout.listview_row, null, true);

        ratingBar=(RatingBar) rowView.findViewById(R.id.ratingBar);
        Button nameButton =(Button) rowView.findViewById(R.id.button2);
        TextView addressTextfield =(TextView) rowView.findViewById(R.id.addressTextfield);
        TextView ratingValue=(TextView) rowView.findViewById(R.id.ratingValue);

        nameButton.setText(nameArray[position]);
        addressTextfield.setText(addressArray[position]);
        ratingBar.setRating(Float.parseFloat(ratingArray[position]));
        ratingValue.setText(ratingArray[position]);

        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, StudentMenu.class);
                intent.putExtra("messName", nameArray[position]);
                intent.putExtra("id","1");
                context.startActivity(intent);
            }
        });

        return rowView;

    }
}