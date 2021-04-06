package com.cerata.robodoc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomResultDiseaseListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Disease> arrayList;
    ShowResultDoctorListActivity showResultDoctorListActivity;


    public CustomResultDiseaseListAdapter(Context context, ArrayList<Disease> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Disease diseaseAtCurrentPosition = this.arrayList.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.result_disease_item_template, null);
        TextView textViewDisease = convertView.findViewById(R.id.result_disease_name);
        TextView textViewSymptom = convertView.findViewById(R.id.result_symptom_list);
        Button find = convertView.findViewById(R.id.btn_find_doc_result_for_result_diseases);
        textViewDisease.setText(diseaseAtCurrentPosition.getPercentage()+"% মিলে গিয়েছে "+diseaseAtCurrentPosition.getName()+" রোগের সাথে");
        textViewSymptom.setText(diseaseAtCurrentPosition.getSymptom());
        find.setOnClickListener(new View.OnClickListener() {
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
