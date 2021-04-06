package com.cerata.robodoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class CustomSymptomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> arrayList;
    ArrayList<String> selected = new ArrayList<>();

    public CustomSymptomAdapter(Context context, ArrayList<String> arrayList) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        new ArrayList<>();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.single_symptomp_item_template,null);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.single_symptom);
        checkBox.setText(arrayList.get(position));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selected.add(checkBox.getText().toString());
                }else{
                    selected.remove(checkBox.getText().toString());
                }
            }
        });
        return convertView;
    }
    public ArrayList<String> getSelectedItems(){
        return selected;
    }
}
