package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView login;
    EditText Email, Password, cPassword;
    Button register;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.txt_signin);
        Email = findViewById(R.id.txt_email);
        Password = findViewById(R.id.txt_pass);
        cPassword = findViewById(R.id.txt_conpass);
        register = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser() {
        String email = Email.getText().toString();
        String pass = Password.getText().toString();
        String cPass = cPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        }else if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(cPass)){
            Password.setError("Password cannot be empty");
            Password.requestFocus();
        }else {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }else {
                            Toast.makeText(MainActivity.this, "Registration Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            Toast.makeText(this, "Register Here", Toast.LENGTH_LONG).show();
        }
    }
}