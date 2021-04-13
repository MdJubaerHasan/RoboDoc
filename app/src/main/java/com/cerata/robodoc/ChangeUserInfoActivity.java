package com.cerata.robodoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.List;

public class ChangeUserInfoActivity extends AppCompatActivity {
    ImageView btn_back;
    TextView change_pass, change_dob, change_address;
    RadioGroup radioGroup;
    RadioButton radioButton, btn_male, btn_female;
    EditText bio_input, name_input;
    Button save_info;
    private String name, dob, sex, bio, age, id, address, latitude, longitude, gender;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    DatePickerDialog.OnDateSetListener onDateSetListener;
    DBHelperUser myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        name_input = findViewById(R.id.change_username);
        btn_back = (ImageView) findViewById(R.id.back_btn3);
        radioGroup = (RadioGroup) findViewById(R.id.update_gender);
        btn_male = (RadioButton)findViewById(R.id.update_male);
        btn_female = (RadioButton)findViewById(R.id.update_female);
        change_dob = findViewById(R.id.change_dob);
        change_address = findViewById(R.id.change_location);
        bio_input = findViewById(R.id.change_bio);
        change_pass = (TextView) findViewById(R.id.change_password);
        save_info = findViewById(R.id.updateUserInfo);
        myDB = new DBHelperUser(this);
        id = getIntent().getStringExtra("user_id");

        User user = myDB.getSingleUserInfo(id);
        if (!id.equals("")){

            name_input.setText(String.valueOf(user.getName()));
            change_dob.setText(String.valueOf(user.getDob()));
            bio_input.setText(String.valueOf(user.getBio()));
            gender = String.valueOf(user.getSex());
            if (gender.equals(btn_male.getText().toString())){
                btn_male.setChecked(true);
            }else {
                btn_female.setChecked(true);
            }

        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ChangeUserInfoActivity.this);
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
                    getLocationData(locationResult.getLastLocation());
                }
            }
        };



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserDashboardActivity.class);
                startActivity(intent);
            }
        });

        change_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ChangeUserInfoActivity.this, android.R.style.Theme, onDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date =  dayOfMonth + "/"  +month + "/" + year;
                change_dob.setText(date);
                LocalDate today = LocalDate.now();
                LocalDate birth  = LocalDate.of(year, month, dayOfMonth);
                Period p = Period.between(birth, today);
                age = String.valueOf(p.getYears());
            }
        };

        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSettingsAndStartLocationUpdate();
                Toast.makeText(getApplicationContext(), "New Location " + address , Toast.LENGTH_LONG).show();
            }
        });


        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeUserPasswordActivity.class);
                startActivity(intent);
            }
        });


         if (myDB.checkUserID(id)) {
             save_info.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     stopLocationUpdate();
                     int radioId = radioGroup.getCheckedRadioButtonId();
                     radioButton = findViewById(radioId);
                     name = name_input.getText().toString();
                     sex = radioButton.getText().toString();
                     dob = change_dob.getText().toString();
                     bio = bio_input.getText().toString();


                     if (name.equals("")||sex.equals("")||dob.equals("")||address.equals("")||latitude.equals("")||longitude.equals("")){
                         Toast.makeText(ChangeUserInfoActivity.this, "সবগুলো ঘর পূরণ করুন ",Toast.LENGTH_LONG).show();
                     }else {
                         boolean res = myDB.updateData(id, name, dob, sex, age, address,latitude,longitude, bio);
                         if (res) {
                             Toast.makeText(getApplicationContext(), "Successfully Updated Data ", Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
                             startActivity(intent);
                         } else {
                             Toast.makeText(getApplicationContext(), "Failed to Updated Data ", Toast.LENGTH_LONG).show();

                         }
                     }
                 }
             });
         }
    }

    // get latest Location

    // Check device settings
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
                        apiException.startResolutionForResult(ChangeUserInfoActivity.this, 9991);
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

    public void getLocationData(Location location){
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
        Geocoder geocoder = new Geocoder(ChangeUserInfoActivity.this);
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address = addressList.get(0).getAddressLine(0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}