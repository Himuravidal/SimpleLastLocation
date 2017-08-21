package com.adachersoft.simplelocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.icu.text.DateFormat;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private Location currentLocation;
    private static final int RC_GEO = 343;
    private TextView latitudeTv;
    private TextView longitudeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        latitudeTv = (TextView) findViewById(R.id.latitudeTv);
        longitudeTv = (TextView) findViewById(R.id.longitudeTv);
        Button locationBtn = (Button) findViewById(R.id.locationBtn);
        Button startLocation = (Button) findViewById(R.id.startLocationBtn);
        Button stopLocation = (Button) findViewById(R.id.stopLocationBtn);


        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLastLocation();
            }
        });
    }

    private void permission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, RC_GEO);
            }
        } else {

        }
    }

    private void getLastLocation() {
        mFusedLocationClient.getLastLocation().addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    latitudeTv.setText(String.valueOf(location.getLatitude()));
                    longitudeTv.setText(String.valueOf(location.getLongitude()));

                } else
                    Toast.makeText(MainActivity.this, "Algo fallo", Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (RC_GEO == requestCode) {
            getLastLocation();
        }
    }


}





