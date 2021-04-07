package com.cerata.robodoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button login_button;
    TextView sign_up;
    DBHelperDiseaseDoctor dbDisDoc;
    DBHelperDiseaseSymptom dbDisSym;
    DBHelperSymptoms symDis;
    DBHelperDoctor dbDoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button = (Button) findViewById(R.id.login_btn);
        sign_up =  findViewById(R.id.register_btn);

        dbDisSym = new DBHelperDiseaseSymptom(this);
        dbDisSym.insert();
        dbDisDoc = new DBHelperDiseaseDoctor(this);
        dbDisDoc.insert();
        dbDoc = new DBHelperDoctor(this);
        dbDoc.insert();
        symDis = new DBHelperSymptoms(this);
        symDis.insert();


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(register);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("userIdKey") && sharedPreferences.contains("passwordKey")) {
            String username = sharedPreferences.getString("userIdKey", "");
            String password = sharedPreferences.getString("passwordKey", "");
            if (username.equals("") || password.equals("")) {
                Toast.makeText(getApplicationContext(), "can't auto log in", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
                startActivity(intent);
            }
        }
    }
}