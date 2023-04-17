package com.example.gpsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import android.Manifest;

import org.w3c.dom.Text;

import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_FINE_LOCATION = 99; // this can be any arbitrary number that you want
    private TextView AddressText;
    private Button LocationButton;
    private TextView lati;
    private TextView logn;
    private Switch locationUpdate;
    private Switch sgps;
    private TextView lblGPS;

    private LocationRequest locationRequest; // location request is a config file for settings related to fuzedproviderclient

    FusedLocationProviderClient flpc; // Google's API for location services
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Activity objects go here
        AddressText = findViewById(R.id.address_text);
        LocationButton = findViewById(R.id.getIt);
        lati = findViewById(R.id.lat_text);
        logn = findViewById(R.id.lon_text);
        locationUpdate = findViewById(R.id.slocationsupdates);
        sgps = findViewById(R.id.sgps);
        lblGPS = findViewById(R.id.labelgp);
        //Other variables go here
        locationRequest = LocationRequest.create();
        // priority is the accuracy of the location
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Interval is how often does it check for updates, fastest is the best case scenario
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(5000);

        sgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sgps.isChecked()) // checked is high accuracy
                {
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    lblGPS.setText("Using GPS Sensors");
                }
                else
                {
                    locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
                    lblGPS.setText("Using Towers + WIFI");
                }
            }
        });
        LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGPS();
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                updateGPS();
            }
            else {
                Toast.makeText(this, "This app requires that you grant permission to work properly", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        }
    }

    private void updateGPS(){
        // get permissions to track GPS, current location from fused client, and updates the UI
        flpc = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            flpc.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    //after permission is granted, the location is found and the values are put into the UI
                    updateUI(location);
                }
            });
            
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }
    private void updateUI(Location location)
    {
        // update the whole UI to show new values
        lati.setText(String.valueOf(location.getLatitude()));
        logn.setText(String.valueOf(location.getLongitude()));
        /*AddressText.setText(location.);*/
    }
}