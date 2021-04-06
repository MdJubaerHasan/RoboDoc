package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAppChoiceActivity extends AppCompatActivity {
    Button diagnosis, list_doc, list_disease;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_choice);

        diagnosis = (Button) findViewById(R.id.diag_dis);
        list_doc = (Button) findViewById(R.id.list_doc);
        list_disease = (Button) findViewById(R.id.list_dis);

        list_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowDoctorListActivity.class);
                startActivity(intent);
            }
        });
        list_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowDiseaseDetailsActivity.class);
                startActivity(intent);
            }
        });
        diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SelectSymptomsActivity.class);
                startActivity(intent);
            }
        });
    }
}