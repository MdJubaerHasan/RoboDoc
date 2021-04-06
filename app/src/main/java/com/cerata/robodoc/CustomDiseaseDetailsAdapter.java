package com.cerata.robodoc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomDiseaseDetailsAdapter extends BaseAdapter {
    Context context;
    ArrayList<Disease> arrayList;

    public CustomDiseaseDetailsAdapter(Context context, ArrayList<Disease> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Disease diseaseAtCurrentPosition = this.arrayList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.single_disease_item_template, null);
        TextView textViewDisease = convertView.findViewById(R.id.disease_name);
        TextView textViewSymptom = convertView.findViewById(R.id.symptom_list);
        Button find_doc = convertView.findViewById(R.id.btn_find_doc);
        textViewDisease.setText(diseaseAtCurrentPosition.getName());
        textViewSymptom.setText(diseaseAtCurrentPosition.getSymptom());
        find_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowResultDoctorListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("disease", diseaseAtCurrentPosition.getName());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
