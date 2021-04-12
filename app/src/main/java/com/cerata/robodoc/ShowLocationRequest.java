package com.cerata.robodoc;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;


public class ShowLocationRequest extends AppCompatActivity {
    private static final int REQUEST_CODE = 10001;
    private boolean result = false;
    private boolean isGranted = false;
    DBHelperUser dbHelperUser;
    TextView textView;
    Button button;
    String id = "";

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;

    private static final int INTERVAL = 4000;
    private static final int FASTEST_INTERVAL = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location_request);
        textView = findViewById(R.id.permission_check);
        button = findViewById(R.id.btn_grantPermission);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = locationRequest.create();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        dbHelperUser = new DBHelperUser(this);

        // Getting user id from shared preference

        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        id = sharedPreferences.getString("userIdKey", "");

        // Location Permission Granted or not
        if (ContextCompat.checkSelfPermission(ShowLocationRequest.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            textView.setText("Permission Granted");
            getLastKnownLocation(id);
        }else {
            textView.setText("Permission Not Granted");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
    }


    // Request for location permission

    public void requestPermission(){
        if (ContextCompat.checkSelfPermission(ShowLocationRequest.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(ShowLocationRequest.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(ShowLocationRequest.this)
                        .setMessage("Wew need Permission for fine location")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(ShowLocationRequest.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                    }
                }).show();
            }else {
                ActivityCompat.requestPermissions(ShowLocationRequest.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }
        }else {
            // permission is granted
            textView.setText("Permission Granted");
            getLastKnownLocation(id);
        }
    }


    // Decide what happens on permission granted / denied


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SharedPreferences location_permission = getSharedPreferences("location_permission", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = location_permission.edit();
        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // Permission Granted
                textView.setText("Permission Granted");
                String choice =  "granted";
                editor.putString("userLocationChoice", choice);
                editor.apply();
                getLastKnownLocation(id);
            }else {
                // Permission Not granted
                if (!ActivityCompat.shouldShowRequestPermissionRationale(ShowLocationRequest.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                    // Permanently Denied Permission
                    new AlertDialog.Builder(ShowLocationRequest.this).setMessage("You have permanently denied location permission, to to settings>permissions>location Permission and turn on permission")
                            .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    gotoApplicationSettings();
                                }
                            }).setCancelable(false).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();

                }else {
                    textView.setText("Permission Not Granted");
                    String choice =  "denied";
                    editor.putString("userLocationChoice", choice);
                    editor.apply();
                }
            }
        }
    }


    // Go to settings and allow permission


    public void gotoApplicationSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);

    }


    // Get LAst Known Location
    public void getLastKnownLocation(String user_id){
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location!= null){
                    // we have a location
                    getLocationData(user_id, location);
                }else {
                    location = null;
                    return;
                }
            }
        });
        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Error: "+e.getLocalizedMessage());
            }
        });
    }


    // insert Data in Database


    public void getLocationData(String id , Location location){
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        Geocoder geocoder = new Geocoder(ShowLocationRequest.this);
        String address = "";
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addressList.get(0).getAddressLine(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (!address.equals("") && !latitude.equals("") && !longitude.equals("")){
            result = dbHelperUser.updateLocation(id, address, latitude, longitude);
        }
        if (result){
            Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(), "Couldn't insert Location", Toast.LENGTH_LONG).show();
        }

    }

}