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
import android.widget.Toast;

public class ChangeUserPasswordActivity extends AppCompatActivity {
    ImageView btn_back;
    EditText current_pass, new_pass, re_new_pas;
    Button submit_change;
    DBHelperUser dbHelperUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_password);
        btn_back = (ImageView) findViewById(R.id.back_btn4);
        current_pass = findViewById(R.id.current_pass);
        new_pass = findViewById(R.id.new_pass);
        re_new_pas = findViewById(R.id.new_re_pass);
        submit_change = findViewById(R.id.savePass);
        dbHelperUser = new DBHelperUser(this);
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = sharedPreferences.getString("userIdKey", "");
                String old_pass= current_pass.getText().toString();
                String pass = new_pass.getText().toString();
                String re_pass = re_new_pas.getText().toString();
                if (!old_pass.equals("") && !userId.equals("")){
                    if (dbHelperUser.checkUserIDPassword(userId,old_pass)){
                        if (!pass.equals("")&& !re_pass.equals("")){
                            if (pass.equals(re_pass)){
                                dbHelperUser.updatePassword(pass, userId);
                                Toast.makeText(getApplicationContext(), "পাসওয়ার্ড পরিবরতন হয়েছে", Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "পাসওয়ার্ড  মিলে নাই", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "পাসওয়ার্ড এর ঘর পূর্ণ করুন", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "পুরনো পাসওয়ার্ড মিলে নাই", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "পাসওয়ার্ড এর ঘর পূর্ণ করুন", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}