package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileAuthActivity extends AppCompatActivity {

    EditText phone, otp;
    Button send, verify;
    FirebaseAuth mauth;
    String phoneNumber;
    String otpid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_auth);

        setTitle("Mobile Number Authentication");

        phone = findViewById(R.id.txt_phone);
        otp = findViewById(R.id.txt_otp);
        send = findViewById(R.id.btn_send);
        verify = findViewById(R.id.login);

        mauth = FirebaseAuth.getInstance();

        //to get otp in registered mobile number
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (otp.getText().toString().isEmpty()){
                    Toast.makeText(MobileAuthActivity.this, "Blank field cannot be processed", Toast.LENGTH_SHORT).show();
                } 
                
                else if (otp.getText().toString().length() != 6) {
                    
                    Toast.makeText(MobileAuthActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, otp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

        //to request the otp
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!phone.getText().toString().isEmpty() && phone.getText().toString().length() == 10){
                    phoneNumber = "+91"+ phone.getText().toString();
                    requestOtp(phoneNumber);
                }
                else {
                    phone.setError("Phone Number Invalid");
                }
            }
        });
    }

    private void requestOtp(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MobileAuthActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                otpid = s;
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MobileAuthActivity.this, LoginActivity.class));
                            finish();
                        } 
                        else {
                            Toast.makeText(MobileAuthActivity.this, "Sigin Code Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}