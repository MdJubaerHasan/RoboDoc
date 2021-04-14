package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectSymptomsActivity extends AppCompatActivity {
    Context context;
    DBHelperSymptoms dbHelperSymptoms;
    ArrayList<Symptom> arrayList;
    ListView listView;
    CustomSymptomAdapter customSymptomAdapter;
    Button btn_find_diease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_symptoms);
        listView = (ListView) findViewById(R.id.sym_listView);
        btn_find_diease = (Button) findViewById(R.id.btn_find_disease);
        dbHelperSymptoms = new DBHelperSymptoms(this);
        dbHelperSymptoms.insert();


        loadDataInListView();


        btn_find_diease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selected_symptoms = customSymptomAdapter.getSelectedItems();
                if (!selected_symptoms.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),ShowResultDiseaseListActivity.class);
                    intent.putStringArrayListExtra("list_of_selected_symptoms",selected_symptoms);
//                    selected_symptoms.clear();
                    startActivity(intent);
                }
            }
        });

    }
    public void loadDataInListView(){
        arrayList = dbHelperSymptoms.getAllSymptoms();
        customSymptomAdapter = new CustomSymptomAdapter(this, arrayList);
        listView.setAdapter(customSymptomAdapter);
        customSymptomAdapter.notifyDataSetChanged();
    }
}