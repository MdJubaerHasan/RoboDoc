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
    ArrayList<Doctor> arrayList;

    public CustomResultDoctorListAdapter(Context context, ArrayList<Doctor> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.single_result_doctor_item, null);
        TextView textViewDoctor = (TextView) convertView.findViewById(R.id.doctor_name_text);
        Doctor diseaseDoctor = arrayList.get(position);
        textViewDoctor.setText(diseaseDoctor.getName());
        System.out.println(diseaseDoctor.getName());
        return convertView;
    }
}
