package com.cerata.robodoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHelperDiseaseDoctor extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Disease_Doctor.db";
    private static final String TABLE_NAME = "Disease_Doctor";
    private static final String ID = "Id";
    private static final String DISEASE = "Disease";
    private static final String DOCTOR_NAME = "Doctor_Name";
    private static final int VERSION = 1;
    public DBHelperDiseaseDoctor(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table "+ TABLE_NAME +"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DISEASE+" TEXT, "+DOCTOR_NAME+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public void insertData(SQLiteDatabase db, String disease_name, String doctor_name){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DISEASE, disease_name);
        contentValues.put(DOCTOR_NAME, doctor_name);
        db.insert(TABLE_NAME, null, contentValues);
    }
    public void insert(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Drop table if exists "+TABLE_NAME);
        onCreate(db);
        insertData(db,"ডায়রিয়া","ডাঃ মহিউদ্দিন হাবিব");
        insertData(db,"ডায়রিয়া","ডাঃ জুয়াল ইলিয়াশ রব");
        insertData(db,"ডায়রিয়া","ডাঃ শেক মান্না রোশিদ");
        insertData(db,"ডায়রিয়া","ডাঃ জাহিদ কোবির সেলিম");
        insertData(db,"ডেঙ্গু","ডাঃ নিজাম উদ্দিন ফারুক");
        insertData(db,"ডেঙ্গু","ডাঃ নাতাশা খোন্দোকার");
        insertData(db,"ডেঙ্গু","ডাঃ এস. এম. হাবিব খান");
        insertData(db,"ডেঙ্গু","ডাঃ সোনিয়া পার্বিন");
        insertData(db,"অ্যাপেন্ডিসাইটিস","ডাঃ পার্থো বোরুয়া");
        insertData(db,"অ্যাপেন্ডিসাইটিস","ডাঃ মোবাশ্বেরা জাহান সাদিয়া");
        insertData(db,"অ্যাপেন্ডিসাইটিস","ডাঃ এস.এম. রাহুল শিকদার");
        insertData(db,"অ্যাপেন্ডিসাইটিস","ডাঃ নিজাম মেরদা");
        insertData(db,"ইসকেমিক হৃদরোগ","ডাঃ নাজমা আক্তার শিউলি");
        insertData(db,"ইসকেমিক হৃদরোগ","ডাঃ রাজিয়া বেগম");
        insertData(db,"ইসকেমিক হৃদরোগ","ডাঃ রোহান হোসেন");
        insertData(db,"ইসকেমিক হৃদরোগ","ডাঃ হাবিবুর রহমান");
        insertData(db,"ডায়াবেটিস","ডাঃ শাইরা জিতিয়া");
        insertData(db,"ডায়াবেটিস","ডাঃ মনিরুল হাসান");
        insertData(db,"ডায়াবেটিস","ডাঃ গোপাল বরুয়া");
        insertData(db,"স্ট্রোক","ডাঃ আবদুল কালাম শিকদার");
        insertData(db,"স্ট্রোক","ডাঃ খালেক রোশিদ শিকদার");
        insertData(db,"স্ট্রোক","ডাঃ রিয়াদ কোবির খান");
        insertData(db,"যক্ষা","ডাঃ রোহন খান");
        insertData(db,"যক্ষা","ডাঃ হারুন হাওলাদার");
        insertData(db,"যক্ষা","ডাঃ রনবীর পাল");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","ডাঃ নিপা খোন্দোকার");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","ডাঃ আবদুল হামিদ খান");
        insertData(db,"প্রস্রাবের রাস্তায় প্রদাহ","ডাঃ অনিমেষ পাল");
    }
    public ArrayList<DoctorDisease> getAllData(){
        ArrayList<DoctorDisease> arrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()){
            String dis_name = cursor.getString(1);
            String doc_name= cursor.getString(2);
            DoctorDisease dis_doc = new DoctorDisease(doc_name,dis_name);
            arrayList.add(dis_doc);
        }
        cursor.close();
        return arrayList;
    }
    public ArrayList<DoctorDisease> getDoctorForSelectedDisease(String disease){
        ArrayList<DoctorDisease> arrayList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(" SELECT " + DOCTOR_NAME + " FROM " + TABLE_NAME + " WHERE " + DISEASE + "='" + disease + "'", null);
        if(cursor.moveToFirst()){
            do{
                String doc_name = cursor.getString(0);
                DoctorDisease dis_doc = new DoctorDisease(doc_name,disease);
                arrayList.add(dis_doc);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }
}
