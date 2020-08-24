package com.example.caloriesmanager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    private Boolean validateName() {
        String nameToBeValidated = username.getEditText().getText().toString();

        if (nameToBeValidated.isEmpty()) {
            username.setError("Username cannot be empty!");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String nameToBeValidated = password.getEditText().getText().toString();

        if (nameToBeValidated.isEmpty()) {
            password.setError("Password cannot be empty!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view) {
        if (!validateName() || !validatePassword()){
            return;
        } else {
            isUser();
        }
    }

    private void isUser(){
        final String enteredUserNameValue = username.getEditText().getText().toString();
        final String enteredPasswordValue = password.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("signUpUserName").equalTo(enteredUserNameValue);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDataBase = snapshot.child(enteredUserNameValue)
                            .child("signUpPassword").getValue(String.class);
                    if (passwordFromDataBase.equals(enteredPasswordValue)) {

                        username.setError(null);
                        username.setErrorEnabled(false);

                        String usernameFromDatabase = snapshot.child(enteredUserNameValue)
                                .child("signUpUserName").getValue(String.class);

                        String emailFromDataBase = snapshot.child(enteredUserNameValue)
                                .child("signUpEmail").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

                        intent.putExtra("signUpUserName", usernameFromDatabase);
                        intent.putExtra("signUpPassword", passwordFromDataBase);
                        intent.putExtra("signUpEmail", emailFromDataBase);

                        startActivity(intent);
                    } else {
                        password.setError("Wrong password!");
                        password.requestFocus();
                    }
                } else {
                    username.setError("Username doesn't exist!");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}