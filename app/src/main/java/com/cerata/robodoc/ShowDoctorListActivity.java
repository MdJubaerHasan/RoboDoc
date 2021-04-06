package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowDoctorListActivity extends AppCompatActivity {
    DBHelperDiseaseDoctor dbHelperDiseaseDoctor;
    ListView listView;
    TextView textView;
    public static ArrayList<DoctorDisease> arrayList;
    CustomDoctorListAdapter customDoctorListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_doctor_list);
        listView = (ListView) findViewById(R.id.doc_list_viewID);
        textView = (TextView) findViewById(R.id.current_disease_TextID);
        dbHelperDiseaseDoctor = new DBHelperDiseaseDoctor(ShowDoctorListActivity.this);
        arrayList = new ArrayList<>();
        arrayList.removeAll(arrayList);
        loadDataInListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = arrayList.get(position).getDoctor_name();
                Intent intent = new Intent(ShowDoctorListActivity.this, ShowDoctorDetailsActivity.class);
                intent.putExtra("doctor", name);
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                textView.setText(arrayList.get(firstVisibleItem).getDisease_name());
            }
        });

    }

    public void loadDataInListView(){
        arrayList = dbHelperDiseaseDoctor.getAllData();
        customDoctorListAdapter = new CustomDoctorListAdapter(this,arrayList);
        listView.setAdapter(customDoctorListAdapter);
        customDoctorListAdapter.notifyDataSetChanged();
    }
}