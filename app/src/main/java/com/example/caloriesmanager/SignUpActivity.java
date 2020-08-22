package com.example.caloriesmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout signUpUserName, signUpPassword, signUpEmail;
    Button signUpRegisterButton, alreadyHaveAnAccount;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpUserName = findViewById(R.id.signUpUserName);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpEmail = findViewById(R.id.signUpEmail);

        signUpRegisterButton = findViewById(R.id.signUpRegisterButton);


        alreadyHaveAnAccount = findViewById(R.id.signUpGoBackToLoginButton);

        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                //getting values from text fields
                String userName = signUpUserName.getEditText().getText().toString();
                String password = signUpPassword.getEditText().getText().toString();
                String email = signUpEmail.getEditText().getText().toString();


                UsersHelperClass helperClass = new UsersHelperClass(userName, password, email);

                reference.setValue(helperClass);
            }
        });
    }
}