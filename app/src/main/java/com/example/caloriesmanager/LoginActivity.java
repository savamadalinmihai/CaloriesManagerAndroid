package com.example.caloriesmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp, loginBtn;
    ImageView imageLogin;
    TextView logoText, sloganText;
    TextInputLayout username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        imageLogin = findViewById(R.id.loginScreenImage);
        logoText = findViewById(R.id.loginScreenText);
        sloganText = findViewById(R.id.loginSloganText);
        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);
        callSignUp = findViewById(R.id.loginGoToSignUpButton);

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
//
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(imageLogin, "signup_screen_image_transition");
                pairs[1] = new Pair<View, String>(logoText, "signup_screen_text_transition");
                pairs[2] = new Pair<View, String>(sloganText, "signup_slogan_text_transition");
                pairs[3] = new Pair<View, String>(username, "signup_username_transition");
                pairs[4] = new Pair<View, String>(password, "signup_password_transition");
                pairs[5] = new Pair<View, String>(loginBtn, "signup_button_transition");
                pairs[6] = new Pair<View, String>(callSignUp, "signup_go_back_to_login_button_transition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation
                        (LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}