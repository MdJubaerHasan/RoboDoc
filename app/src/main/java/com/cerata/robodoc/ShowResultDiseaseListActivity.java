package com.cerata.robodoc;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ShowResultDiseaseListActivity extends AppCompatActivity {
    DBHelperDiseaseSymptom dbHelperDiseaseSymptom;
    ArrayList<String> arrayList,updatedArrayList,updatedArrayListSorted;
    ArrayList<Disease> resultDiseaseList = new ArrayList<>();
    CustomResultDiseaseListAdapter customResultDiseaseListAdapter;
    ListView listView;
    Button button_find_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result_disease_list);
        Intent intent = getIntent();
        ArrayList<String> pickedSymptoms = intent.getStringArrayListExtra("list_of_selected_symptoms");
        dbHelperDiseaseSymptom = new DBHelperDiseaseSymptom(this);
        button_find_doc = (Button) findViewById(R.id.btn_find_doc_result_for_result_diseases);
        arrayList = dbHelperDiseaseSymptom.getAllResultDiseaseNames(pickedSymptoms);

        updatedArrayList = new ArrayList<>();
        for(int j =0; j<arrayList.size(); j++){
            updatedArrayList.add(arrayList.get(j)+ Collections.frequency(arrayList,arrayList.get(j)));
        }
        Set<String> set = new HashSet<>(updatedArrayList);
        updatedArrayList.clear();
        updatedArrayList.addAll(set);

        // Sorting array list

        String[] array = updatedArrayList.toArray(new String[0]);
        Arrays.sort(array, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int a = Integer.valueOf(o1.replaceAll("[^0-9]", ""));
                int b = Integer.valueOf(o2.replaceAll("[^0-9]", ""));
                return Integer.compare(b,a);
            }
        });
        updatedArrayListSorted = new ArrayList<>(Arrays.asList(array));

        String[] matched = new String[updatedArrayListSorted.size()];
        for (int k =0; k<updatedArrayListSorted.size(); k++){
            matched[k] = updatedArrayListSorted.get(k).substring(updatedArrayListSorted.get(k).length()-1);
            updatedArrayListSorted.set(k, updatedArrayListSorted.get(k).substring(0, updatedArrayListSorted.get(k).length() - 1));
        }
        ArrayList<Disease> temp= dbHelperDiseaseSymptom.getDiseaseArrayList();
        for (int i = 0; i<updatedArrayListSorted.size();i++){
            for (int j = 0; j<temp.size(); j++){
                if (updatedArrayListSorted.get(i).equals(temp.get(j).getName())){
                    double matched_total = Integer.parseInt(matched[i]);
                    double have_total = temp.get(j).getNumberOfSymptoms();
                    double percentage = ((matched_total/have_total)*100);
                    String res_name = temp.get(j).getName();
                    String res_sym = temp.get(j).getSymptom();
                    Disease resultDisease = new Disease(res_name,res_sym,(short) percentage);
                    resultDiseaseList.add(resultDisease);
                }
            }
        }
        Collections.sort(resultDiseaseList);
        loadDataInListView();
    }

    public void loadDataInListView(){
        customResultDiseaseListAdapter = new CustomResultDiseaseListAdapter(this,resultDiseaseList);

        listView = findViewById(R.id.result_diseases_list_viewID);
        listView.setAdapter(customResultDiseaseListAdapter);
        customResultDiseaseListAdapter.notifyDataSetChanged();
    }
}