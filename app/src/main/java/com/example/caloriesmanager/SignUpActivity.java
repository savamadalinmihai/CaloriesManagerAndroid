package com.example.caloriesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.Login;
import com.google.android.material.textfield.TextInputLayout;
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
    }

    private Boolean validateName() {
        String nameToBeValidated = signUpUserName.getEditText().getText().toString();

        if (nameToBeValidated.isEmpty()) {
            signUpUserName.setError("Username cannot be empty!");
            return false;
        } else {
            signUpUserName.setError(null);
            signUpUserName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String emailToBeValidated = signUpEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String noWhiteSpace = "(?=\\s+$)";

        if (emailToBeValidated.isEmpty()) {
            signUpEmail.setError("Email cannot be empty!");
            return false;
        } else if(!emailToBeValidated.matches(emailPattern)) {
            signUpEmail.setError("Not a valid email!");
            return false;
        } else {
            signUpEmail.setError(null);
            signUpEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String passwordToBeValidated = signUpPassword.getEditText().getText().toString();
        String validPasswordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";

        if (passwordToBeValidated.isEmpty()) {
            signUpPassword.setError("Password cannot be empty!");
            return false;
        } else if (passwordToBeValidated.length() < 8) {
            signUpPassword.setError("Password too short");
            return false;
        } else if (!passwordToBeValidated.matches(validPasswordPattern)) {
            signUpPassword.setError("Password too weak!");
            return false;
        }

        else {
            signUpPassword.setError(null);
            signUpPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {
        if (!validateName() || !validatePassword() || !validateEmail()) {
            return;
        }

        String userName = signUpUserName.getEditText().getText().toString();
        String password = signUpPassword.getEditText().getText().toString();
        String email = signUpEmail.getEditText().getText().toString();


        UsersHelperClass helperClass = new UsersHelperClass(userName, password, email);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        reference.child(userName).setValue(helperClass);

        signUpUserName.getEditText().setText(null);
        signUpPassword.getEditText().setText(null);
        signUpEmail.getEditText().setText(null);

        Toast.makeText(SignUpActivity.this, "Registration successful",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
