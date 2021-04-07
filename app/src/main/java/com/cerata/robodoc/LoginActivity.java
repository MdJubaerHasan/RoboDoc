package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    ImageView back_button;
    TextView register;
    EditText u_id, u_pass;
    Button login;
    DBHelperUser myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back_button = findViewById(R.id.back_btn);
        register = findViewById(R.id.register_btn2);
        u_id = (EditText) findViewById(R.id.id_user);
        u_pass = (EditText) findViewById(R.id.password_user);
        login = (Button) findViewById(R.id.login_btn);
        myDB = new DBHelperUser(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(reg);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = u_id.getText().toString();
                String pass = u_pass.getText().toString();


                if(id.equals("")||pass.equals("")){
                    Toast.makeText(getApplicationContext(), "সবগুলো ঘর পূরণ করুন ",Toast.LENGTH_LONG).show();
                }
                else {
                    Boolean checkUserAndPass = myDB.checkUserIDPassword(id, pass);
                    if (checkUserAndPass){
                        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userIdKey", id);
                        editor.putString("passwordKey", pass);
                        Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
                        editor.apply();
//                        Toast.makeText(getApplicationContext(), "Thank you laa", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
//                        intent.putExtra("NAME", user);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "আপনার নাম অথবা পাসওয়ার্ড মিলে নাই । সঠিক নাম এবং পাসওয়ার্ড দিন ",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}