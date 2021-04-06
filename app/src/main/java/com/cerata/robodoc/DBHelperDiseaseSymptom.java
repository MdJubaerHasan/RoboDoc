package com.cerata.robodoc;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class DBHelperDiseaseSymptom extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Disease_Symptom.db";
    private static final String TABLE_NAME = "Disease_Symptom";
    private static final String DISEASE = "Disease";
    private static final String SYMPTOM = "Symptom";
    private static final String ID = "Id";
    private boolean hasInserted = false;
    private static final int VERSION = 1;
    public DBHelperDiseaseSymptom(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table "+ TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DISEASE+" TEXT ,"+SYMPTOM+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public void insertData(SQLiteDatabase db, String disease_name, String disease_symptom){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DISEASE, disease_name);
        contentValues.put(SYMPTOM, disease_symptom);
        db.insert(TABLE_NAME, null, contentValues);
    }
    public void insert(){
        if(hasInserted){
            return;
        }
        hasInserted = true;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);
        insertData(db,"ডায়রিয়া","পাতলা পায়খানা");
        insertData(db,"ডায়রিয়া","বমি বমি ভাব / বমি");
        insertData(db,"ডায়রিয়া","নিস্তেজতা");
        insertData(db,"ডায়রিয়া","তলপেটে ব্যথা");
        insertData(db,"ডেঙ্গু","তীব্র জ্বর");
        insertData(db,"ডেঙ্গু","চোখ, মাথা, পেশী ব্যথা");
        insertData(db,"ডেঙ্গু","র\u200D্যাশ");
        insertData(db,"ডেঙ্গু","তীব্র হাড়ের ব্যথা");
        insertData(db,"ডেঙ্গু","জয়েন্টে ব্যথা");
        insertData(db,"ডেঙ্গু","বমি বমি ভাব / বমি");
        insertData(db,"অ্যাপেন্ডিসাইটিস","নাভির চারদিকে ব্যথা যা ডান দিকে সরে যায়");
        insertData(db,"অ্যাপেন্ডিসাইটিস","বমি বমি ভাব / বমি");
        insertData(db,"অ্যাপেন্ডিসাইটিস","ক্ষুধামন্দা");
        insertData(db,"অ্যাপেন্ডিসাইটিস","জ্বর");
        insertData(db,"ইসকেমিক হৃদরোগ","বুকে চাপ ধরা ব্যথা");
        insertData(db,"ইসকেমিক হৃদরোগ","শ্বাসকষ্ট");
        insertData(db,"ইসকেমিক হৃদরোগ","বাম ঘাড়, বাম হাত ব্যথা");
        insertData(db,"ইসকেমিক হৃদরোগ","উপরের পেটে ব্যথা");
        insertData(db,"ইসকেমিক হৃদরোগ","মাথাব্যথা");
        insertData(db,"ইসকেমিক হৃদরোগ","মাথা ঘোরা");
        insertData(db,"ইসকেমিক হৃদরোগ","অতিরিক্ত ঘামা");
        insertData(db,"ইসকেমিক হৃদরোগ","অজ্ঞান হয়ে যাওয়া");
        insertData(db,"ইসকেমিক হৃদরোগ","বমি বমি ভাব / বমি");
        insertData(db,"ডায়াবেটিস","ঘন ঘন প্রস্রাব হওয়া");
        insertData(db,"ডায়াবেটিস","ঘন ঘন তৃষ্ণা পাওয়া");
        insertData(db,"ডায়াবেটিস","অনিচ্ছাকৃত ওজন হ্রাস");
        insertData(db,"ডায়াবেটিস","ঝাপসা দৃষ্টি");
        insertData(db,"ডায়াবেটিস","খুব ক্লান্ত লাগা");
        insertData(db,"ডায়াবেটিস","ক্ষত দ্রুত না শুকানো");
        insertData(db,"ডায়াবেটিস","ঘন ঘন ক্ষুধা পাওয়া");
        insertData(db,"স্ট্রোক","মুখ, বাহু বা পায়ে হঠাৎ অসাড়তা বা দুর্বলতা, বিশেষত দেহের একপাশে");
        insertData(db,"স্ট্রোক","হঠাৎ বিভ্রান্তি, কথা বলতে সমস্যা বা বক্তব্য বুঝতে অসুবিধা");
        insertData(db,"স্ট্রোক","হঠাৎ হঠাৎ এক বা উভয় চোখে দেখতে  সমস্যা");
        insertData(db,"স্ট্রোক","হঠাৎ হঠাৎ মাথা ঘোরা");
        insertData(db,"স্ট্রোক","হাঁটার সমস্যা বা ভারসাম্য হ্রাস হওয়া");
        insertData(db,"স্ট্রোক","হঠাৎ হঠাৎ তীব্র মাথাব্যথা");
        insertData(db,"যক্ষা","একটি কাশি যা তিন সপ্তাহের বেশি স্থায়ী হয়");
        insertData(db,"যক্ষা","কাশির সাথে রক্ত পরা");
        insertData(db,"যক্ষা","সন্ধ্যায় জ্বর আসা");
        insertData(db,"যক্ষা","রাতে শরীর ঘামা");
        insertData(db,"যক্ষা","বুকে ব্যথা");
        insertData(db,"যক্ষা","ক্ষুধামন্দা");
        insertData(db,"যক্ষা","অনিচ্ছাকৃত ওজন হ্রাস");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","প্রস্রাবের সময় জ্বালাপোড়া");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","জ্বর");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","বমি বমি ভাব / বমি");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","তলপেটে ব্যথা");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","ঘন ঘন প্রস্রাব হওয়া");
    }
    public ArrayList<String> getAllDiseaseNames(){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT DISTINCT " + DISEASE + " FROM " + TABLE_NAME, null);

        if(cursor.moveToFirst()){
            do{
                arrayList.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public String getCombinedSymptomsForDisease(String Disease){
        StringBuilder stringBuilder = new StringBuilder();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor;

        cursor = sqLiteDatabase.rawQuery("SELECT " + SYMPTOM + " FROM " + TABLE_NAME + " WHERE " + DISEASE + "='" + Disease + "'", null );

        if(cursor.moveToFirst()){
            do{
                stringBuilder.append("*");
                stringBuilder.append(cursor.getString(0));
                stringBuilder.append("\n");
            }while(cursor.moveToNext());
        }
        cursor.close();
        return stringBuilder.toString();
    }
    public ArrayList<Disease> getDiseaseArrayList() {
        ArrayList<Disease> diseaseArrayList = new ArrayList<>();
        ArrayList<String> diseaseNames = getAllDiseaseNames();
        for(String disease : diseaseNames){
            String combinedSymptom = getCombinedSymptomsForDisease(disease);
            int number_of_symptoms = getTotalSymptom(disease);
            Disease currentDisease = new Disease(disease, combinedSymptom, number_of_symptoms);
            diseaseArrayList.add(currentDisease);
        }
        return diseaseArrayList;
    }
    public int getTotalSymptom(String Disease){
        int counter = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor;

        cursor = sqLiteDatabase.rawQuery("SELECT " + SYMPTOM + " FROM " + TABLE_NAME + " WHERE " + DISEASE + "='" + Disease + "'", null );

        if(cursor.moveToFirst()){
            do{
                counter++;
            }while(cursor.moveToNext());
        }
        cursor.close();
        return counter;
    }
    @SuppressLint("Recycle")
    public ArrayList<String> getAllResultDiseaseNames(ArrayList<String> list_of_selected_symptoms){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        for (int i = 0; i<list_of_selected_symptoms.size();i++){
            Cursor cursor = database.rawQuery("SELECT " + DISEASE + " FROM " + TABLE_NAME + " WHERE " + SYMPTOM + "='" + list_of_selected_symptoms.get(i) + "'", null );
            if (cursor.moveToFirst()){
                do {
                    arrayList.add(cursor.getString(0));
                }
                while (cursor.moveToNext());
            }
        }
        return arrayList;
    }

}
