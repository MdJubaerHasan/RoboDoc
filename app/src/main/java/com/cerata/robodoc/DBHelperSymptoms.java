package com.cerata.robodoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHelperSymptoms extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Symptoms.db";
    private static final String TABLE_NAME = "Symptom_List";
    private static final String ID = "Id";
    private static final String SYMPTOM = "Symptom";
    private static final int VERSION = 1;
    public DBHelperSymptoms(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table "+ TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SYMPTOM+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public void insertData(SQLiteDatabase db, String disease_symptom){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SYMPTOM, disease_symptom);
        db.insert(TABLE_NAME, null, contentValues);
    }
    public void insert(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);
        insertData(db,"র\u200D্যাশ");
        insertData(db,"তীব্র জ্বর");
        insertData(db,"জ্বর");
        insertData(db,"সন্ধ্যায় জ্বর আসা");
        insertData(db,"চোখ, মাথা, পেশী ব্যথা");
        insertData(db,"তলপেটে ব্যথা");
        insertData(db,"তীব্র হাড়ের ব্যথা");
        insertData(db,"জয়েন্টে ব্যথা");
        insertData(db,"বুকে চাপ ধরা ব্যথা");
        insertData(db,"বুকে ব্যথা");
        insertData(db,"নাভির চারদিকে ব্যথা যা ডান দিকে সরে যায়");
        insertData(db,"বাম ঘাড়, বাম হাত ব্যথা");
        insertData(db,"উপরের পেটে ব্যথা");
        insertData(db,"মাথাব্যথা");
        insertData(db,"হঠাৎ হঠাৎ তীব্র মাথাব্যথা");
        insertData(db,"নিস্তেজতা");
        insertData(db,"ঝাপসা দৃষ্টি");
        insertData(db,"খুব ক্লান্ত লাগা");
        insertData(db,"শ্বাসকষ্ট");
        insertData(db,"মুখ, বাহু বা পায়ে হঠাৎ অসাড়তা বা দুর্বলতা, বিশেষত দেহের একপাশে");
        insertData(db,"হঠাৎ বিভ্রান্তি, কথা বলতে সমস্যা বা বক্তব্য বুঝতে অসুবিধা");
        insertData(db,"হঠাৎ হঠাৎ এক বা উভয় চোখে দেখতে  সমস্যা");
        insertData(db,"হাঁটার সমস্যা বা ভারসাম্য হ্রাস হওয়া");
        insertData(db,"মাথা ঘোরা");
        insertData(db,"হঠাৎ হঠাৎ মাথা ঘোরা");
        insertData(db,"অতিরিক্ত ঘামা");
        insertData(db,"অজ্ঞান হয়ে যাওয়া");
        insertData(db,"ক্ষুধামন্দা");
        insertData(db,"ঘন ঘন তৃষ্ণা পাওয়া");
        insertData(db,"ঘন ঘন ক্ষুধা পাওয়া");
        insertData(db,"অনিচ্ছাকৃত ওজন হ্রাস");
        insertData(db,"ক্ষত দ্রুত না শুকানো");
        insertData(db,"একটি কাশি যা তিন সপ্তাহের বেশি স্থায়ী হয়");
        insertData(db,"কাশির সাথে রক্ত পরা");
        insertData(db,"রাতে শরীর ঘামা");
        insertData(db,"পাতলা পায়খানা");
        insertData(db,"বমি বমি ভাব / বমি");
        insertData(db,"ঘন ঘন প্রস্রাব হওয়া");
        insertData(db,"প্রস্রাবের সময় জ্বালাপোড়া");
    }
    public ArrayList<String> getAllSymptoms(){
        ArrayList<String> arrayList= new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT " + SYMPTOM + " FROM " + TABLE_NAME, null);
        if(cursor.moveToFirst()){
            do {
                arrayList.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
}
