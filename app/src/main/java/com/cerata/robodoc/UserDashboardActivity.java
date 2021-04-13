package com.cerata.robodoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.annotation.Target;

public class UserDashboardActivity extends AppCompatActivity {
    TextView id, name, sex, age, location , bio ;
    Button changeInfo, diagnosisPage, logOut;
    DBHelperUser myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        changeInfo = (Button) findViewById(R.id.btn_change_info_page);
        diagnosisPage =(Button) findViewById(R.id.btn_diag_page);
        id = (TextView) findViewById(R.id.textViewUserID);
        name = (TextView) findViewById(R.id.textViewUserName);
        sex = (TextView) findViewById(R.id.textViewUserGender);
        age = (TextView) findViewById(R.id.textViewUserAge);
        location = (TextView) findViewById(R.id.textViewUserLocation);
        bio = (TextView) findViewById(R.id.textViewUserBio);
        logOut = (Button) findViewById(R.id.logout);
        myDB = new DBHelperUser(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userId = sharedPreferences.getString("userIdKey", "");


        User user = myDB.getSingleUserInfo(userId);
        if (!userId.equals("")){

            id.setText(String.valueOf(user.getId()));
            name.setText(String.valueOf(user.getName()));
            age.setText(String.valueOf(user.getAge()));
            location.setText(String.valueOf(user.getLocation()));
            sex.setText(String.valueOf(user.getSex()));
            bio.setText(String.valueOf(user.getBio()));
        }

        diagnosisPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboardActivity.this, MainAppChoiceActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                Intent intent = new Intent(UserDashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeUserInfoActivity.class);
                intent.putExtra("user_id", String.valueOf(user.getId()));
                startActivity(intent);
            }
        });


    }
}