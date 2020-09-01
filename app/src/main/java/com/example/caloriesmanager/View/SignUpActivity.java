package com.example.caloriesmanager.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriesmanager.Controller.LoginProcedure;
import com.example.caloriesmanager.R;
import com.example.caloriesmanager.Model.UsersHelperClass;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout signUpUserName, signUpPassword, signUpEmail;
    Button signUpRegisterButton, alreadyHaveAnAccount;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    private TextView textViewUser;
    private ImageView imageViewUser;
    private LoginButton facebookLoginButton;
    private static final String TAG = "FacebookAuthentication";

    private CallbackManager callbackManager;
    private FirebaseAuth mFirebaseAuth;

    // this method launches on the creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // initialise facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        // initialise Firebase
        mFirebaseAuth = FirebaseAuth.getInstance();

        // hooking up of objects to layout references
        signUpUserName = findViewById(R.id.signUpUserName);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpRegisterButton = findViewById(R.id.signUpRegisterButton);
        alreadyHaveAnAccount = findViewById(R.id.signUpGoBackToLoginButton);

        textViewUser = findViewById(R.id.profileSectionUsername);
        imageViewUser = findViewById(R.id.profileSectionImage);


        // initialisation of the Facebook Login Procedure

        facebookLoginButton = findViewById(R.id.facebookLoginButton);
        facebookLoginButton.setPermissions("email", "public_profile");

        callbackManager = CallbackManager.Factory.create();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        // onclicklistener for the "i already have an account" button that sends the user to
        // the Login Activity
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // this overrides the onstart method and checks if the current user is logged in on app start
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if (currentUser != null) {
            updateUI(currentUser);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    // this method handles the from the callback manager
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please sign in to continue", Toast.LENGTH_SHORT).show();
        }
    }

    // this method validates the input for the username field
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

    // this method validates the input for the email field
    private Boolean validateEmail() {
        String emailToBeValidated = signUpEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String noWhiteSpace = "(?=\\s+$)";

        if (emailToBeValidated.isEmpty()) {
            signUpEmail.setError("Email cannot be empty!");
            return false;
        } else if (!emailToBeValidated.matches(emailPattern)) {
            signUpEmail.setError("Not a valid email!");
            return false;
        } else {
            signUpEmail.setError(null);
            signUpEmail.setErrorEnabled(false);
            return true;
        }
    }

    // this method validates the input for the password field
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
        } else {
            signUpPassword.setError(null);
            signUpPassword.setErrorEnabled(false);
            return true;
        }
    }

    // this method triggers methods to validate inputs and if inputs are valid
    // it registers user to firebase database
    public void registerUser(View view) {
        if (!validateName() || !validatePassword() || !validateEmail()) {
            return;
        }

        // Assigning sign up credentials to variables to later pass to userhelperclass
        // which creates a new user in the Firebase database
        String userName = signUpUserName.getEditText().getText().toString();
        String password = signUpPassword.getEditText().getText().toString();
        String email = signUpEmail.getEditText().getText().toString();


        // creating a new user object with credentials from above
        UsersHelperClass helperClass = new UsersHelperClass(userName, password, email);

        // getting root node and the reference to the path users
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        // storing a new user to users reference of the firebase node
        reference.child(userName).setValue(helperClass);

        // clearing fields after registration
        signUpUserName.getEditText().setText(null);
        signUpPassword.getEditText().setText(null);
        signUpEmail.getEditText().setText(null);

        // toast for confirmation of registration
        Toast.makeText(SignUpActivity.this, "Registration successful",
                Toast.LENGTH_LONG).show();

        // navigating the user to the login activity where they can sign up with the created account
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}
