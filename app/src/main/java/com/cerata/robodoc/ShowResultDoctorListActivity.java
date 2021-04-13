package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

public class ShowResultDoctorListActivity extends AppCompatActivity{
    DBHelperDiseaseDoctor dbHelperDiseaseDoctor;
    DBHelperDoctor dbHelperDoctor;
    DBHelperUser myDB;
    TextView textView;
    ListView listView;
    CustomDoctorListAdapter customDoctorListAdapter;
    ArrayList<DoctorDisease> doctors, updatedDoctors;
    ArrayList<Doctor>doctorsDistance;
    String userId, latitudeUser, longitudeUser, latitudeDoctor, longitudeDoctor, nameDoctor;
    double distance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result_doctor_list);

        // initiating database
        dbHelperDiseaseDoctor = new DBHelperDiseaseDoctor(this);
        dbHelperDoctor = new DBHelperDoctor(this);
        myDB = new DBHelperUser(this);

        // boilerplate
        textView = findViewById(R.id.list_for_disease);
        listView = findViewById(R.id.list_of_disease_docIDX);

        // initiating array list
        doctors = new ArrayList<>();
        doctorsDistance = new ArrayList<>();
        updatedDoctors = new ArrayList<>();


        String dis  = getIntent().getStringExtra("disease");
        textView.setText(dis+" রোগের ডাক্তার তালিকা");


        // Get user id from shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("userIdKey")) {
            userId = sharedPreferences.getString("userIdKey", "");

            // Get user latitude, longitude
            if (!userId.equals("")){
                User user = myDB.getSingleUserInfo(userId);
                latitudeUser = user.getLatitude();
                longitudeUser = user.getLongitude();

                // Get doctors for selected disease
                doctors = dbHelperDiseaseDoctor.getDoctorForSelectedDisease(dis);

                for (int i = 0; i <doctors.size(); i++){
                    nameDoctor = doctors.get(i).getDoctor_name();
                    latitudeDoctor = dbHelperDoctor.getSingleDoctorInfo(nameDoctor).getLatitude();
                    longitudeDoctor = dbHelperDoctor.getSingleDoctorInfo(nameDoctor).getLongitude();
                    LatLng latLngCurrent = new LatLng(Double.parseDouble(latitudeUser), Double.parseDouble(longitudeUser));
                    LatLng latLngDestination = new LatLng(Double.parseDouble(latitudeDoctor), Double.parseDouble(longitudeDoctor));
                    distance = SphericalUtil.computeDistanceBetween(latLngCurrent, latLngDestination)/1000.00;
                    Doctor doctor = new Doctor(nameDoctor,distance);
                    doctorsDistance.add(doctor);
                }
                Collections.sort(doctorsDistance);
                for(Doctor item: doctorsDistance){
                    DoctorDisease doctorDisease = new DoctorDisease(item.getName(),dis);
                    updatedDoctors.add(doctorDisease);
                }


            }
        }
        loadDataInListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = updatedDoctors.get(position).getDoctor_name();
                Intent intent = new Intent(ShowResultDoctorListActivity.this, ShowDoctorDetailsActivity.class);
                intent.putExtra("doctor", name);
                startActivity(intent);
            }
        });
    }


    public void loadDataInListView(){

        customDoctorListAdapter = new CustomDoctorListAdapter(this,updatedDoctors);
        listView.setAdapter(customDoctorListAdapter);
        customDoctorListAdapter.notifyDataSetChanged();
    }



}