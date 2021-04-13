package com.cerata.robodoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class CustomResultDoctorListAdapter extends BaseAdapter {
    Context context;
    ArrayList<DoctorDisease> arrayList;

    public CustomResultDoctorListAdapter(Context context, ArrayList<DoctorDisease> arrayList) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.single_result_doctor_item, null);
        TextView textViewDoctor = convertView.findViewById(R.id.result_doctor_name_text);
        TextView textViewDistance = convertView.findViewById(R.id.showDistance);
        DoctorDisease diseaseDoctor = arrayList.get(position);
        textViewDoctor.setText(diseaseDoctor.getDoctor_name());
        textViewDistance.setText((int)diseaseDoctor.getDoctor_distance()+ " Km");
        return convertView;
    }
}