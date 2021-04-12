package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

public class ChangeUserInfoActivity extends AppCompatActivity {
    ImageView btn_back;
    TextView change_pass, change_dob;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText bio_input, name_input;
    Button save_info;
    private String name, dob, sex, bio, age, id, location, latitude, longitude;

    DatePickerDialog.OnDateSetListener onDateSetListener;
    DBHelperUser myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        name_input = findViewById(R.id.change_username);
        btn_back = (ImageView) findViewById(R.id.back_btn3);
        radioGroup = (RadioGroup) findViewById(R.id.update_gender);
        change_dob = findViewById(R.id.change_dob);
        bio_input = findViewById(R.id.change_bio);
        change_pass = (TextView) findViewById(R.id.change_password);
        save_info = findViewById(R.id.updateUserInfo);
        myDB = new DBHelperUser(this);
        id = getIntent().getStringExtra("user_id");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserDashboardActivity.class);
                startActivity(intent);
            }
        });

        change_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ChangeUserInfoActivity.this, android.R.style.Theme, onDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date =  dayOfMonth + "/"  +month + "/" + year;
                change_dob.setText(date);
                LocalDate today = LocalDate.now();
                LocalDate birth  = LocalDate.of(year, month, dayOfMonth);
                Period p = Period.between(birth, today);
                age = String.valueOf(p.getYears());
            }
        };



        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeUserPasswordActivity.class);
                startActivity(intent);
            }
        });


         if (myDB.checkUserID(id)) {
             save_info.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int radioId = radioGroup.getCheckedRadioButtonId();
                     radioButton = findViewById(radioId);
                     name = name_input.getText().toString();
                     sex = radioButton.getText().toString();
                     dob = change_dob.getText().toString();
                     bio = bio_input.getText().toString();
                     location = "Dhaka";
                     latitude = "1234";
                     longitude = "4567";

                     if (name.equals("")||sex.equals("")||dob.equals("")||location.equals("")||latitude.equals("")||longitude.equals("")){
                         Toast.makeText(ChangeUserInfoActivity.this, "সবগুলো ঘর পূরণ করুন ",Toast.LENGTH_LONG).show();
                     }else {
                         boolean res = myDB.updateData(id, name, dob, sex, age, location,latitude,longitude, bio);
                         if (res) {
                             Toast.makeText(getApplicationContext(), "Successfully Updated Data ", Toast.LENGTH_LONG).show();
                             Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
                             startActivity(intent);
                         } else {
                             Toast.makeText(getApplicationContext(), "Failed to Updated Data ", Toast.LENGTH_LONG).show();

                         }
                     }
                 }
             });
         }
    }
}