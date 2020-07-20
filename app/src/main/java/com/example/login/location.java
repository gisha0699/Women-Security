package com.example.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class location extends AppCompatActivity  {
    private FusedLocationProviderClient client;
    String loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        client=LocationServices.getFusedLocationProviderClient(this);

                if(ActivityCompat.checkSelfPermission(location.this, ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                    return;
                }
                client.getLastLocation().addOnSuccessListener(location.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            TextView textView = findViewById(R.id.location);
                           loc=("lat: " + location.getLatitude() + ", lng: " + location.getLongitude());
                            textView.setText(loc);
                        }

                    }


                });

        Intent i = new Intent(location.this, EmergencyContacts.class);
        i.putExtra("Location",loc);
        startActivity(i);
    }
    private  void  requestPermission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }

}
