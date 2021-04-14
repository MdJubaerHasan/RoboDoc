package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;

public class RegistrationActivity extends AppCompatActivity {
    ImageView back_button;
    EditText user_name, user_password, user_re_password, user_dob;
    Button create_account;
    RadioGroup radioGroup;
    RadioButton radioButton;
    DBHelperUser myDB;
    TextView datePicked;
    String age;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        back_button = findViewById(R.id.back_btn2);
        user_name = (EditText) findViewById(R.id.user_name);
        user_password = (EditText) findViewById(R.id.type_password);
        user_re_password = (EditText) findViewById(R.id.retype_password);
        radioGroup = (RadioGroup) findViewById(R.id.gender_selector);
        create_account = (Button) findViewById(R.id.sign_up_btn);
        datePicked = findViewById(R.id.cal);
        myDB = new DBHelperUser(this);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        datePicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this, android.R.style.Theme, onDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date =  dayOfMonth + "/"  +month + "/" + year;
                datePicked.setText(date);
                LocalDate today = LocalDate.now();
                LocalDate birth  = LocalDate.of(year, month, dayOfMonth);
                Period p = Period.between(birth, today);
                age = String.valueOf(p.getYears());
            }
        };

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = user_name.getText().toString();
                String id = String.valueOf(id_generator());
                String pass = user_password.getText().toString();
                String re_pass = user_re_password.getText().toString();
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                String sex = radioButton.getText().toString();
                String dob = datePicked.getText().toString();
                if (user.equals("")||pass.equals("")||re_pass.equals("")||dob.equals("")||sex.equals("")){
                    Toast.makeText(RegistrationActivity.this, "সবগুলো ঘর পূরণ করুন ",Toast.LENGTH_LONG).show();
                }
                else {
                    if(pass.equals(re_pass)){
                        Boolean checkUser = myDB.checkUserID(id);
                        if (!checkUser){
                            id = id + "@_" + user;
                            Boolean insert = myDB.insertData(id,user, pass, dob, sex, age,"", "" ,"","");
                            if (insert){
                                Toast.makeText(RegistrationActivity.this, "অ্যাকাউন্ট তইরি হয়েছে  ",Toast.LENGTH_LONG).show();
//                                Intent login = new Intent(RegistrationActivity.this, LoginActivity.class);
//                                startActivity(login);
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                builder.setMessage("Your ID is: "+id).setTitle("ID GENERATED").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "রেজিস্ট্রেশান হয়নি",Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
//                            Toast.makeText(RegistrationActivity.this, "এই নাম এ অন্য ইউজার আছেন, আরেকটি নাম পছন্দ করুন",Toast.LENGTH_LONG).show();
                            while (checkUser){
                                id = String.valueOf(id_generator());
                            }
                            id = id + "@_" + user;
                            Boolean insert = myDB.insertData(id,user, pass, dob, sex, age,"", "" ,"","");
                            if (insert){
                                Toast.makeText(RegistrationActivity.this, "অ্যাকাউন্ট তইরি হয়েছে  ",Toast.LENGTH_LONG).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                                builder.setMessage("Your ID is: "+id).setTitle("ID GENERATED").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "রেজিস্ট্রেশান হয়নি",Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                    else {
                        Toast.makeText(RegistrationActivity.this, "পাসওয়ার্ড  মিলে নাই , পুনরায় পাসওয়ার্ড দিন ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public static int id_generator(){
        Random random = new Random();
        return random.nextInt(999)+(10*(200/78));

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}