package com.cerata.robodoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowLocationRequest extends AppCompatActivity {
    DBHelperUser dbHelperUser;
    Button btn_locationPermission;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final int INTERVAL_VALUE = 30;
    public static final int FASTEST_INTERVAL_VALUE = 5;
    private static final int PERMISSION_FINE_LOCATION = 10001;
    boolean granted = false;
    boolean result = false;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location_request);
        btn_locationPermission = findViewById(R.id.btn_grantPermission);
        dbHelperUser = new DBHelperUser(this);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000 * INTERVAL_VALUE);
        locationRequest.setFastestInterval(1000 * FASTEST_INTERVAL_VALUE);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                // get location data
                updateUIValues(location);
            }
        };
        btn_locationPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocationPermission();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    // Getting Permission
    private void getLocationPermission() {
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            ActivityCompat.requestPermissions(this, permission, PERMISSION_FINE_LOCATION);
        }
    }

    // Checking if have permission
    private boolean isGranted() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Set data in shared preference
            granted = true;
            return true;
        } else {
            granted = false;
            return false;
        }
    }


    public void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        granted = false;
        switch (requestCode){
            case PERMISSION_FINE_LOCATION:{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    SharedPreferences sharedPreferences = getSharedPreferences("location_permission", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String choice =  "granted";
                    editor.putString("userLocationChoice", choice);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Choice Saved Successfully", Toast.LENGTH_LONG).show();
                    editor.apply();

                    startLocationUpdate();
                    updateGPS();
                }else {
                    Toast.makeText(this, "This App Requires Permission", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    // Update GPS
    public void updateGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(ShowLocationRequest.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    updateUIValues(location);
                }
            });
        }else {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, PERMISSION_FINE_LOCATION);
        }
    }

    private void updateUIValues(Location location) {
        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        String address = "";

        Geocoder geocoder = new Geocoder(ShowLocationRequest.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addresses.get(0).getAddressLine(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userId = sharedPreferences.getString("userIdKey", "");
        if (!address.equals("") && !lat.equals("") && !lon.equals("")){
            result = dbHelperUser.updateLocation(userId,address, lat, lon);
        }
        if (result){
            Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
            startActivity(intent);
        }
    }

}