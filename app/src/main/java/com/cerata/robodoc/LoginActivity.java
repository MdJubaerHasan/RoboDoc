package com.cerata.robodoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    ImageView back_button;
    TextView register;
    EditText u_id, u_pass;
    Button login;
    DBHelperUser myDB;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back_button = findViewById(R.id.back_btn);
        register = findViewById(R.id.register_btn2);
        u_id = (EditText) findViewById(R.id.id_user);
        u_pass = (EditText) findViewById(R.id.password_user);
        login = (Button) findViewById(R.id.login_btn);
        myDB = new DBHelperUser(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoginActivity.this);

        SharedPreferences locate_permission = getSharedPreferences("location_permission", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = locate_permission.edit();
        String choice = locate_permission.getString("userLocationChoice", "");
        System.out.println(choice);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(reg);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = u_id.getText().toString();
                String pass = u_pass.getText().toString();

                if(id.equals("")||pass.equals("")){
                    Toast.makeText(getApplicationContext(), "সবগুলো ঘর পূরণ করুন ",Toast.LENGTH_LONG).show();
                }
                else {
                    Boolean checkUserAndPass = myDB.checkUserIDPassword(id, pass);
                    if (checkUserAndPass){
                        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userIdKey", id);
                        editor.putString("passwordKey", pass);
                        Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
                        editor.apply();
                        if(choice.equals("granted")){
                            getLastKnownLocation(id);
                            Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getApplicationContext(), ShowLocationRequest.class);
                            startActivity(intent);
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "আপনার নাম অথবা পাসওয়ার্ড মিলে নাই । সঠিক নাম এবং পাসওয়ার্ড দিন ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
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
        Geocoder geocoder = new Geocoder(LoginActivity.this);
        String address = "";
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addressList.get(0).getAddressLine(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean result = false;
        if (!address.equals("") && !latitude.equals("") && !longitude.equals("")){
            result = myDB.updateLocation(id, address, latitude, longitude);
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}