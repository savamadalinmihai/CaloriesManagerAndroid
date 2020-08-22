package com.example.caloriesmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation topAnimation, bottomAnimation;
    ImageView image;
    TextView txtAppName, txtDescription;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animations
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);

        txtAppName = findViewById(R.id.txtAppName);
        txtDescription = findViewById(R.id.txtDescription);

        //Animations assignment
        image.setAnimation(topAnimation);
        txtAppName.setAnimation(bottomAnimation);
        txtDescription.setAnimation(bottomAnimation);


//        // this checks for authentication and redirects user to home screen if authenticated
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        if (currentUser != null) {
//            // User is signed in, send to mainmenu
//            Log.d(TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
//            startActivity(new Intent(MainActivity.this, HomeActivity.class));
//        } else {
//            // User is signed out, send to register/login
//
//
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(txtAppName, "logo_text");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());

            }
        }, SPLASH_SCREEN);
    }
}
