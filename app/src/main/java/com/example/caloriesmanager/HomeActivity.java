package com.example.caloriesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView profileSectionUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        profileSectionUsername = findViewById(R.id.profileSectionUsername);

        Intent intent = getIntent();
        String username = intent.getStringExtra("signUpUserName");

        profileSectionUsername.setText("Welcome, " + username);
    }
}