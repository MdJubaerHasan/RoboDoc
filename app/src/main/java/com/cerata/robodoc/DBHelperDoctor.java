package com.cerata.robodoc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelperDoctor extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Doctor_Info.db";
    private static final String TABLE_NAME = "Doctor_Info";
    private static final String DOCTOR_NAME = "Doctor_Name";
    private static final String DOCTOR_CHAMBER = "Doctor_Chamber";
    private static final String DOCTOR_EMAIL = "Doctor_Email";
    private static final String DOCTOR_PHONE = "Doctor_Contact";
    private static final String CHAMBER_MAP = "Chamber_Map";

    private static final int VERSION = 1;
    public DBHelperDoctor(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table "+ TABLE_NAME+"("+DOCTOR_NAME+" TEXT, "+DOCTOR_CHAMBER+" TEXT, "+DOCTOR_EMAIL+" TEXT, "+DOCTOR_PHONE+" PRIMARY KEY, "+CHAMBER_MAP+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
    public void insertData(SQLiteDatabase db, String doctor_name, String doctor_chamber, String doctor_email, String doctor_phone, String chamber_map){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DOCTOR_NAME, doctor_name);
        contentValues.put(DOCTOR_CHAMBER, doctor_chamber);
        contentValues.put(DOCTOR_EMAIL, doctor_email);
        contentValues.put(DOCTOR_PHONE, doctor_phone);
        contentValues.put(CHAMBER_MAP, chamber_map);
        db.insert(TABLE_NAME, null, contentValues);
    }
    public void insert(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" drop table if exists " + TABLE_NAME);
        onCreate(db);
        insertData(db,"ডাঃ হাবিবুর রহমান","ঢাকা মেডিকেল কলেজ হাসপাতাল","habib7797@gmail.com","01748-296526","Dhaka Medical College Secretariat Rd, Dhaka 1000");
        insertData(db,"ডাঃ মহিউদ্দিন হাবিব","স্যার সলিমুল্লাহ মেডিকেল কলেজ হাসপাতাল","mohiuddin.bdmail@gmail.com","01757-216927","Sir Salimullah Medical College Mitford Rd, Dhaka 1100");
        insertData(db,"ডাঃ নিজাম উদ্দিন ফারুক","সোহরাওয়ার্দী মেডিকেল কলেজ হাসপাতাল","uddin173.bdmail@gmail.com","01977-276777","Shaheed Suhrawardy Medical College and Hospital Dhaka 1207");
        insertData(db,"ডাঃ পার্থো বোরুয়া","গ্রিন লাইফ মেডিকেল কলেজ","partho999@gmail.com","01917-777117","MAK Khan Tower, 31 and 31/1, Bir Uttam KM Shafiullah Sarak, Green Rd, Dhaka 1205");
        insertData(db,"ডাঃ নাজমা আক্তার শিউলি","আরিফ মেমোরিয়াল হাসপাতাল","nazma.bdmail@gmail.com","0199-2924315","Arif Memorial Hospital College Rd, Barishal");
        insertData(db,"ডাঃ শাইরা জিতিয়া","ময়মনসিংহ মেডিকেল কলেজ হাসপাতাল","shaira1993.bdmail@gmail.com","0178-0020077","Mymensingh Medical College & Hospital Char Para, Medical Rd, Mymensingh 2200");
        insertData(db,"ডাঃ আবদুল কালাম শিকদার","এনাম মেডিকেল কলেজ & হাসপাতাল","abdul7717kalam@gmail.com","01744-250428","Enam Medical College and Hospital Enam Medical College Jame Mosque, Savar Union");
        insertData(db,"ডাঃ রোহন খান","খাজা ইউনুস আলী মেডিকেল কলেজ & হাসপাতাল","rohankhan739.bdmail@gmail.com","01921-947823","Khwaja Yunus Ali Medical College and Hospital Enayetpur Sharif, Sirajganj - Enayetpur Rd, 6751");
        insertData(db,"ডাঃ সোনিয়া পার্বিন","খাজা ইউনুস আলী মেডিকেল কলেজ & হাসপাতাল","soniadr333.bdmail@gmail.com","0192-7333623","Khwaja Yunus Ali Medical College and Hospital Enayetpur Sharif, Sirajganj - Enayetpur Rd, 6751");
        insertData(db,"ডাঃ নিপা খোন্দোকার","হলি ফ্যামিলি মেডিকেল কলেজ & হাসপাতাল","nipa3007@gmail.com","0183-2829087","Holy Family Red Crescent Medical College Hospital 1 Eskaton Garden Rd, Dhaka 1000");
        insertData(db,"ডাঃ জুয়াল ইলিয়াশ রব","মনসুর আলী মেডিকেল কলেজ & হাসপাতাল","juyal95rob@gmail.com","01712-709837","Shaheed Monsur Ali Medical College and Hospital House 26 & 26A, Road No 10A, Dhaka 1215");
        insertData(db,"ডাঃ নাতাশা খোন্দোকার","ইবনেসিনা মেডিকেল কলেজ & হাসপাতাল","natasha.bdmail@gmail.com","01932-309999","Ibn Sina Medical College 1/1-B,Kollayanpur, Mirpur, Dhaka, 1216");
        insertData(db,"ডাঃ মোবাশ্বেরা জাহান সাদিয়া","জেড এইচ শিকদার ওমেন্স মেডিকেল কলেজ & হাসপাতাল","sadia999.bdmail@gmail.com","01778-039204","Z H Sikder Women's Medical College & Hospital House No. CEN(E)-5, Rd 104, Dhaka 1212");
        insertData(db,"ডাঃ রাজিয়া বেগম","পপুলার মেডিকেল কলেজ & হাসপাতাল","bagom99933@gmail.com","0131-2307894","Popular Medical College Hospital House # 25, 25 Rd No. 2, Dhaka 1205");
        insertData(db,"ডাঃ মনিরুল হাসান","ন্যাশনাল মেডিকেল কলেজ & হাসপাতাল","monirul.bdmail@gmail.com","01732-406628","Dhaka National Medical Institute Hospital Johnson Rd, Dhaka");
        insertData(db,"ডাঃ খালেক রোশিদ শিকদার","পটুয়াখালী মেডিকেল কলেজ & হাসপাতাল","roshid267shikder@gmail.com","0182-5839205","Patuakhali Medical College Hospital Patuakhali");
        insertData(db,"ডাঃ নিজাম মেরদা","পটুয়াখালী মেডিকেল কলেজ & হাসপাতাল","nijam7372@gmail.com","0173-7288829","Patuakhali Medical College Hospital Patuakhali");
        insertData(db,"ডাঃ হারুন হাওলাদার","খুলনা মেডিকেল কলেজ & হাসপাতাল","harun1987.bdmail@gmail.com","0182-3984674","Khulna Medical College Hospital Khulna");
        insertData(db,"ডাঃ রনবীর পাল","খুলনা মেডিকেল কলেজ & হাসপাতাল","ronbirpal.bdmail@gmail.com","0172-1778977","Khulna Medical College Hospital Khulna");
        insertData(db,"ডাঃ আবদুল হামিদ খান","রাজশাহী মেডিকেল কলেজ & হাসপাতাল","abdulhamid.bdmail@gmail.com","0176-3728998","Rajshahi Medical College Hospital Medical College Road, Rajshahi 6100");
        insertData(db,"ডাঃ শেক মান্না রোশিদ","সিলেট ওসমানী মেডিকেল কলেজ হাসপাতাল","mannaroshid999211@gmail.com","0172-1667911","Sylhet MAG Osmani Medical College Hospital Medical College Rd, Sylhet");
        insertData(db,"ডাঃ এস. এম. হাবিব খান","রংপুর মেডিকেল কলেজ & হাসপাতাল","habib99khan.bdmail@gmail.com","0176-8893670","Rangpur Medical College and Hospital Rangpur- Dinajpur Highway, Cantt, Near Central Jail, Rangpur 5400");
        insertData(db,"ডাঃ অনিমেষ পাল","রংপুর মেডিকেল কলেজ & হাসপাতাল","animeshpal.bdmail@gmail.com","0162-7922672","Rangpur Medical College and Hospital Rangpur- Dinajpur Highway, Cantt, Near Central Jail, Rangpur 5400");
        insertData(db,"ডাঃ এস.এম. রাহুল শিকদার","চট্টগ্রাম মেডিকেল কলেজ & হাসপাতাল","rahulhassan.bdmail@gmail.com","0173-4890333","চট্টগ্রাম মেডিকেল কলেজ হাসপাতাল 57 K.B. Fazlul Kader Rd, Chattogram 4203");
        insertData(db,"ডাঃ রোহান হোসেন","ইসলামী ব্যাংক মেডিক্যাল কলেজ ও হসপিটাল","rohan77796@gmail.com","0192-1144869","Islami Bank Medical College And Hospital, Rajshahi air port road,nawdapara Rajshahi, 6203");
        insertData(db,"ডাঃ গোপাল বরুয়া","ইসলামী ব্যাংক মেডিক্যাল কলেজ ও হসপিটাল","gopal99931.bdmail@gmail.com","0191-2408887","Islami Bank Medical College And Hospital, Rajshahi air port road,nawdapara Rajshahi, 6203");
        insertData(db,"ডাঃ রিয়াদ কোবির খান","মেডিকেল কলেজ ফর ওমেন এন্ড হাসপাতাল","riad01724177899@gmail.com","0172-4177899","Medical College For Women and Hospital Plot No 4 Road-8/9, Sector-1, Dhaka 1230");
        insertData(db,"ডাঃ জাহিদ কোবির সেলিম","শেরে বাংলা মেডিকেল কলেজ হাসপাতাল","jahidkobir0132@gnail.com","0132-7778937","Sher-E-Bangla Medical College Hospital Band Rd, Barishal 8200");
            }
    public Doctor getSingleDoctorInfo(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DOCTOR_NAME + "=?", new String[]{name});
        cursor.moveToFirst();
        String doc_name = cursor.getString(cursor.getColumnIndex(DOCTOR_NAME));
        String doc_chamber = cursor.getString(cursor.getColumnIndex(DOCTOR_CHAMBER));
        String doc_email = cursor.getString(cursor.getColumnIndex(DOCTOR_EMAIL));
        String doc_phone = cursor.getString(cursor.getColumnIndex(DOCTOR_PHONE));
        Doctor doctor = new Doctor(doc_name,doc_chamber,doc_email,doc_phone);
        cursor.close();
        database.close();
        return doctor;
    }
}
