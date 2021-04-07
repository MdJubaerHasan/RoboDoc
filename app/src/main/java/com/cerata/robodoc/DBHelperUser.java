package com.cerata.robodoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelperUser extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "User.db";
    private static  final String TABLE_NAME = "User_Table";
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String PASSWORD = "Password";
    private static final String DATE_OF_BIRTH = "Date_Of_Birth";
    private static final String GENDER = "Gender";
    private static final String AGE = "Age";
    private static final String LOCATION = "Location";
    private static final String BIO = "Bio";
    private static final int VERSION = 1;
    public DBHelperUser(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table "+ TABLE_NAME+"("+ID+" TEXT PRIMARY KEY ,"+NAME+" TEXT , "+PASSWORD+" TEXT, "+DATE_OF_BIRTH+" TEXT,"+GENDER+" TEXT,"+AGE+" INTEGER, "+LOCATION+" TEXT, "+BIO+" VARCHAR(150))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String id, String name, String password,String d_of_b, String sex, String age, String locate , String bio_info){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(PASSWORD, password);
        contentValues.put(DATE_OF_BIRTH, d_of_b);
        contentValues.put(GENDER, sex);
        contentValues.put(AGE, age);
        contentValues.put(LOCATION, locate);
        contentValues.put(BIO, bio_info);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
//    public Boolean checkUserName(String name){
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select * from "+TABLE_NAME+" where "+NAME+" = ?", new String[]{name});
//        return cursor.getCount() > 0;
//
//    }
    public Boolean checkUserID(String id){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from "+TABLE_NAME+" where "+ID+" = ?", new String[]{id});
        return cursor.getCount() > 0;
    }
    public Boolean checkUserIDPassword(String id, String password){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from "+TABLE_NAME+" where "+ID+" = ? and "+PASSWORD+" = ?", new String[]{id, password});
        return cursor.getCount() > 0;
    }
    public boolean updateData(String id, String name, String dob, String gender, String age, String location,  String bio){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(DATE_OF_BIRTH, dob);
        contentValues.put(GENDER, gender);
        contentValues.put(AGE, age);
        contentValues.put(LOCATION, location);
        contentValues.put(BIO, bio);
        database.update(TABLE_NAME,contentValues, ID + " = ?",new String[]{id});
        return true;
    }
    public boolean updatePassword(String password, String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PASSWORD, password);
        database.update(TABLE_NAME, contentValues, ID + " = ?",new String[]{id});
        return true;
    }


    public User getSingleUserInfo(String id){

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{id});
        cursor.moveToFirst();
        User user = new User();
        user.setId(cursor.getString(cursor.getColumnIndex(ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        user.setAge(cursor.getInt(cursor.getColumnIndex(AGE)));
        user.setSex(cursor.getString(cursor.getColumnIndex(GENDER)));
        user.setLocation(cursor.getString(cursor.getColumnIndex(LOCATION)));
        user.setBio(cursor.getString(cursor.getColumnIndex(BIO)));
        cursor.close();
        database.close();
        return user;
    }

}
