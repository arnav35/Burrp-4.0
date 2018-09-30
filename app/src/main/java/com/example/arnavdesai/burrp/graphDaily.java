package com.example.arnavdesai.burrp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class graphDaily extends AppCompatActivity {

    private DatabaseReference databaseReference;
    String messName;
    ArrayList<BarEntry> entries;
    ArrayList<String> labels;
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_daily);
        messName=getIntent().getStringExtra("messname");
        barChart=(BarChart) findViewById(R.id.barGraph);

        databaseReference=FirebaseDatabase.getInstance().getReference("Count");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Visited v=dataSnapshot.child(messName).getValue(Visited.class);
                entries=new ArrayList<BarEntry>();
                entries.add(new BarEntry(0f,v.Sun));
                entries.add(new BarEntry(1f,v.Mon));
                entries.add(new BarEntry(2f,v.Tue));
                entries.add(new BarEntry(3f,v.Wed));
                entries.add(new BarEntry(4f,v.Thu));
                entries.add(new BarEntry(5f,v.Fri));
                entries.add(new BarEntry(6f,v.Sat));

                BarDataSet dataSet=new BarDataSet(entries,"Count");

                labels=new ArrayList<String>();
                labels.add("Sun");
                labels.add("Mon");
                labels.add("Tue");
                labels.add("Wed");
                labels.add("Thu");
                labels.add("Fri");
                labels.add("Sat");

                BarData data=new BarData(dataSet);
                data.setBarWidth(0.9f);
                barChart.setData(data);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                barChart.setFitBars(true);
                barChart.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
