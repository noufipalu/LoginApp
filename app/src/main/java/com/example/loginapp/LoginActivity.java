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

public class LoginActivity extends AppCompatActivity {

    Button Login, Register;
    TextView forgot;
    EditText Email, Password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Register = findViewById(R.id.reg_btn);
        forgot = findViewById(R.id.forgot_password);
        Login = findViewById(R.id.login_btn);
        Email = findViewById(R.id.email_txt);
        Password = findViewById(R.id.pass_txt);

        mAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        String email = Email.getText().toString();
        String pass = Password.getText().toString();

        if (TextUtils.isEmpty(email)){
            Email.setError("Email cannot be empty");
            Email.requestFocus();
        }else if (TextUtils.isEmpty(pass)){
            Password.setError("Password cannot be empty");
            Password.requestFocus();
        }else {
            mAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, FrontActivity.class);
                        intent.putExtra("Email", mAuth.getCurrentUser().getEmail());
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Login Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}