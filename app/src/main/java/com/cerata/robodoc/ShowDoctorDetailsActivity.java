package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowDoctorDetailsActivity extends AppCompatActivity {
    DBHelperDoctor dbHelperDoctor;
    TextView name_doc, doc_chamber, doc_email, doc_contact;
    Button showMap;
    String chamberMap = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_details);
        dbHelperDoctor = new DBHelperDoctor(this);
        name_doc = (TextView) findViewById(R.id.doc_name_id);
        doc_chamber = (TextView) findViewById(R.id.chamber_location_textID);
        doc_email = (TextView) findViewById(R.id.doc_email_textID);
        doc_contact = (TextView) findViewById(R.id.doc_phone_textID);
        showMap = findViewById(R.id.show_doc_in_map);
        String doc_name = getIntent().getStringExtra("doctor");

        if (!doc_name.equals("")){
            Doctor doctor = dbHelperDoctor.getSingleDoctorInfo(doc_name);
            name_doc.setText(String.valueOf(doctor.getName()));
            doc_chamber.setText(String.valueOf(doctor.getChamber()));
            doc_email.setText(String.valueOf(doctor.getEmail()));
            doc_contact.setText(String.valueOf(doctor.getContact()));
            chamberMap = doctor.getChamberMap();
        }

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String map = "http://maps.google.co.in/maps?q=" + chamberMap;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Only if initiating from a Broadcast Receiver
                String mapsPackageName = "com.google.android.apps.maps";
                i.setClassName(mapsPackageName, "com.google.android.maps.MapsActivity");
                i.setPackage(mapsPackageName);
                getApplicationContext().startActivity(i);
            }
        });
    }
}