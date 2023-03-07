package com.example.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MobileAuthActivity extends AppCompatActivity {

    EditText phone, otp;
    Button send, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_auth);

        setTitle("Mobile Number Authentication");

        phone = findViewById(R.id.txt_phone);
        otp = findViewById(R.id.txt_otp);
        send = findViewById(R.id.btn_send);
        login = findViewById(R.id.login);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}