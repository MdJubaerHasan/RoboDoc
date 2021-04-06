package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowResultDoctorListActivity extends AppCompatActivity {
    DBHelperDiseaseDoctor dbHelperDiseaseDoctor;
    DBHelperDoctor dbHelperDoctor;
    TextView textView;
    ListView listView;
    CustomDoctorListAdapter customDoctorListAdapter;
    ArrayList<DoctorDisease> doctors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result_doctor_list);
        String dis  = getIntent().getStringExtra("disease");
        dbHelperDiseaseDoctor = new DBHelperDiseaseDoctor(this);
        dbHelperDoctor = new DBHelperDoctor(this);
        textView = findViewById(R.id.list_for_disease);
        listView = findViewById(R.id.list_of_disease_docIDX);
        doctors = new ArrayList<>();
        textView.setText(dis+" রোগের ডাক্তার তালিকা");
        loadDataInListView(dis);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = doctors.get(position).getDoctor_name();
                Intent intent = new Intent(ShowResultDoctorListActivity.this, ShowDoctorDetailsActivity.class);
                intent.putExtra("doctor", name);
                startActivity(intent);
            }
        });

    }
    public void loadDataInListView(String dis){
        doctors = dbHelperDiseaseDoctor.getDoctorForSelectedDisease(dis);
        customDoctorListAdapter = new CustomDoctorListAdapter(this,doctors);
        listView.setAdapter(customDoctorListAdapter);
        customDoctorListAdapter.notifyDataSetChanged();
    }


}