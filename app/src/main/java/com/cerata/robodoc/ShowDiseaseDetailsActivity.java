package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowDiseaseDetailsActivity extends AppCompatActivity {
    DBHelperDiseaseSymptom dbHelperDiseaseSymptom;
    ListView listView;
    public static ArrayList<Disease> diseaseArrayList;
    CustomDiseaseDetailsAdapter customDiseaseDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_disease_details);
        populateDiseaseDatabase();
        loadDataInListView();

    }
    private void populateDiseaseDatabase() {
        dbHelperDiseaseSymptom = new DBHelperDiseaseSymptom(this);
        dbHelperDiseaseSymptom.insert();
    }
    public void loadDataInListView(){
        diseaseArrayList = dbHelperDiseaseSymptom.getDiseaseArrayList();
        customDiseaseDetailsAdapter = new CustomDiseaseDetailsAdapter(this,diseaseArrayList);

        listView = findViewById(R.id.symptom_list_viewID);
        listView.setAdapter(customDiseaseDetailsAdapter);
        customDiseaseDetailsAdapter.notifyDataSetChanged();
    }
}