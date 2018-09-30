package com.example.arnavdesai.burrp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Marker extends FragmentActivity implements OnMapReadyCallback {
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
    private GoogleMap mMap;
    String messName,uid;
    String lat,log;
    Button addLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);
        addLocation=(Button) findViewById(R.id.addLocation);

        messName=getIntent().getStringExtra("messName");
        uid=getIntent().getStringExtra("uid");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        CheckUserPermsions();
    }

    private void addtoDatabase() {
        databaseReference.child("Mess Location").child(uid).setValue(lat,log);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed GSoogle Play services and returned to the app.
     */
    //access to permsions
    void CheckUserPermsions(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }

        listener();// init the contact list

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    listener();// init the contact list
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Location Access Denied" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void listener() {
        locationListener locationListener = new locationListener();

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 10, locationListener);

        markerthread mthread=new markerthread();
        mthread.start();
    }

    class markerthread extends Thread {
        public void run()
        {
            int i=0;

            while (true)
            {
                final int finalI = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (locationListener.location != null && finalI ==0) {
                            LatLng currentPos = new LatLng(locationListener.location.getLatitude(), locationListener.location.getLongitude());
                            //mMap.addMarker(new MarkerOptions().position(currentPos).draggable(true).title(messName));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPos,12));
                            Toast.makeText(Marker.this, "Click on the mess location to add",Toast.LENGTH_LONG).show();
                        }
                        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                mMap.addMarker(new MarkerOptions().position(latLng).draggable(true).title(messName));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
                                lat=String.valueOf(latLng.latitude);
                                log=String.valueOf(latLng.longitude);
                            }
                        });
                        addLocation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addtoDatabase();
                            }
                        });

                    }
                });
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
    }
}