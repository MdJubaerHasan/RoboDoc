package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChangeUserInfoActivity extends AppCompatActivity {
    ImageView btn_back;
    TextView change_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);
        btn_back = (ImageView) findViewById(R.id.back_btn3);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserDashboardActivity.class);
                startActivity(intent);
            }
        });
        change_pass = (TextView) findViewById(R.id.change_password);
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeUserPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}