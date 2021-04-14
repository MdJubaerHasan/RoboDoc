package com.cerata.robodoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    Button login_button;
    TextView sign_up;
    DBHelperDiseaseDoctor dbDisDoc;
    DBHelperDiseaseSymptom dbDisSym;
    DBHelperSymptoms symDis;
    DBHelperDoctor dbDoc;
    private static String id = "";
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    DBHelperUser dbHelperUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button = (Button) findViewById(R.id.login_btn);
        sign_up =  findViewById(R.id.register_btn);

        dbDisSym = new DBHelperDiseaseSymptom(this);
        dbDisSym.insert();
        dbDisDoc = new DBHelperDiseaseDoctor(this);
        dbDisDoc.insert();
        dbDoc = new DBHelperDoctor(this);
        dbDoc.insert();
        symDis = new DBHelperSymptoms(this);
        symDis.insert();
        dbHelperUser = new DBHelperUser(this);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(register);
            }
        });

        SharedPreferences location_permission = getSharedPreferences("location_permission", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if (location_permission.contains("userLocationChoice")){
            if (location_permission.getString("userLocationChoice", "").equals("granted")){
                if (sharedPreferences.contains("userIdKey") && sharedPreferences.contains("passwordKey")) {
                    final  String userId = sharedPreferences.getString("userIdKey", "");
                    final  String password = sharedPreferences.getString("passwordKey", "");
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
                    locationRequest = LocationRequest.create();
                    locationRequest.setInterval(4000);
                    locationRequest.setFastestInterval(2000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                    locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            if (locationResult==null){
                                return;
                            }else {
                                if (!userId.equals("")){
                                    getLocationData(userId,locationResult.getLastLocation());
                                    stopLocationUpdate();
                                }
                            }
                        }
                    };

                    checkSettingsAndStartLocationUpdate();
                    if (!userId.equals("") && !password.equals("")) {
                        Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
                        startActivity(intent);
                    }
                }
            }else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        }



    }
    private void checkSettingsAndStartLocationUpdate(){
        LocationSettingsRequest request = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);

        // success

        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdate();
            }
        });

        // failure
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException){
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(MainActivity.this, 8881);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void startLocationUpdate(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, null);
    }
    private void stopLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    // find values

    public void getLocationData(String id , Location location){
        boolean result = false;
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        Geocoder geocoder = new Geocoder(MainActivity.this);
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
    @Override
    public void onBackPressed(){
        finishAffinity();
    }
}