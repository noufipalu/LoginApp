package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email;
    Button submit;
    FirebaseAuth mAuth;
    String email_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        setTitle("Forgot Password");

        email = findViewById(R.id.et_email);
        submit = findViewById(R.id.btn_forgot);

        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUser();
            }
        });
    }

    private void validateUser() {
        email_ = email.getText().toString();
        if (email_.isEmpty()){
            email.setError("Email cannot be empty");
        }else {
            forgetPass();
        }
    }

    private void forgetPass() {
        mAuth.sendPasswordResetEmail(email_).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgetPasswordActivity.this, "Check your email", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                    finish();
                }else {
                    Toast.makeText(ForgetPasswordActivity.this, "Error: " +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}