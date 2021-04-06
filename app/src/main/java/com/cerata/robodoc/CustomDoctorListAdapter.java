package com.cerata.robodoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomDoctorListAdapter extends BaseAdapter {
    Context context;
    ArrayList<DoctorDisease> arrayList;

    public CustomDoctorListAdapter(Context context, ArrayList<DoctorDisease> arrayList) {
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
        convertView = inflater.inflate(R.layout.single_doctor_item_template, null);
        TextView textViewDoctor = (TextView) convertView.findViewById(R.id.doctor_name_text);
        DoctorDisease diseaseDoctor = arrayList.get(position);
        textViewDoctor.setText(diseaseDoctor.getDoctor_name());
        return convertView;
    }
}

