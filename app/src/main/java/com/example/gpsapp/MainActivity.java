package com.example.gpsapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private LocationManager locc;
    private LocationListener locationListener;
    private String provider;
    public TextView locate;
    public Button getLoc;
    public Location metaloc;

    @SuppressLint({"MissingPermission", "MissingInflatedId"})
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locate = (TextView) findViewById(R.id.result);
        getLoc = (Button) findViewById(R.id.getIt);
        provider = locc.GPS_PROVIDER;
        GetCurrent newCont = new GetCurrent();

//        if (ActivityCompat.checkSelfPermission(newCont.GetContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            //return TODO;
//        }

        getLoc.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission)) {
//                    if (isGPSEnabled()){
//
//                    }
//                    else {
//                        turnOnGPS();
//                    }
//                }
//                else {
//                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, );
//                }
                locc.getCurrentLocation(provider, null, newCont.GetContext().getMainExecutor(), new Consumer<Location>() {
                    @Override
                    public void accept(Location location) {
                        metaloc = location;
                    }
                });
            }
            locate.setText(metaloc.getLongitude() + ", " + metaloc.getLatitude());
        });
    }

//    private void turnOnGPS() {
//
//    }
//
//    private boolean isGPSEnabled() {
//        LocationManager locc = null;
//        boolean isEnabled = false;
//
//        if (locc == null){
//            locc = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        }
//        isEnabled = locc.isProviderEnabled(locc.GPS_PROVIDER);
//        return isEnabled;
//    }
}

