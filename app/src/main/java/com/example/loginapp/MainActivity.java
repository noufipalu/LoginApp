package com.example.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView login;
    EditText Email, Password, cPassword;
    Button register;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;

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
        firestore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //to add into the database
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email_ = Email.getText().toString();
                String Password_ = Password.getText().toString();

                Map<String, Object> user = new HashMap<>();
                user.put("Email", Email_);
                user.put("Password", Password_);

                firestore.collection("user").add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                            }
                        });

                //to create an authenticated user. Athentication using email/password authentication
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

        // to create a new user if no user is registered
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            Toast.makeText(this, "Register Here", Toast.LENGTH_LONG).show();
        }
    }
}