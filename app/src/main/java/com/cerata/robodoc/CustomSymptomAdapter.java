//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.RadioGroup;
//    Context context;
//    ArrayList<String> arrayList;
//    ArrayList<String> selected = new ArrayList<>();
//
//    public CustomSymptomAdapter(Context context, ArrayList<String> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return arrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        new ArrayList<>();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(R.layout.single_symptomp_item_template,null);
//        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkboxID);
//        TextView symptom = convertView.findViewById(R.id.single_symptom);
//        symptom.setText(arrayList.get(position));
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                if (isChecked) {
////                    checkBox.setChecked(true);
////                    selected.add(symptom.getText().toString());
////                }else{
////                    checkBox.setChecked(false);
////                    selected.remove(symptom.getText().toString());
////                }
//
//            }
//        });
//        return convertView;
//    }
//    public ArrayList<String> getSelectedItems(){
//        return selected;
//    }
//}

package com.cerata.robodoc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomSymptomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Symptom> symptomList;
    private LayoutInflater layoutInflater;
    private Symptom symptom;
    private ArrayList<String> selectedSymptoms = new ArrayList<>();
    public CustomSymptomAdapter(Context context, ArrayList<Symptom> symptomList) {
        this.context = context;
        this.symptomList = symptomList;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return symptomList.size();
    }

    @Override
    public Object getItem(int position) {
        return symptomList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return symptomList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView==null)
        {
            rowView=layoutInflater.inflate(R.layout.single_symptomp_item_template,parent,false);
        }
        TextView moviesName=(TextView)rowView.findViewById(R.id.single_symptom);
        CheckBox checkBox=(CheckBox)rowView.findViewById(R.id.checkboxID);

        symptom = symptomList.get(position);

        moviesName.setText(symptom.getSymptomName());
        checkBox.setChecked(symptom.isIsChecked());
        checkBox.setTag(position);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = (int) v.getTag();
                boolean isChecked = false;
                if (symptomList.get(currentPos).isIsChecked()==false){
                    selectedSymptoms.add(symptomList.get(currentPos).getSymptomName());
                    isChecked=true;
                }else {
                    selectedSymptoms.remove(symptomList.get(currentPos).getSymptomName());
                    isChecked=false;
                }

                symptomList.get(currentPos).setIsChecked(isChecked);
                notifyDataSetChanged();
            }
        });
        return rowView;
    }
    public ArrayList<String> getSelectedItems(){
        return selectedSymptoms;
    }
}

















