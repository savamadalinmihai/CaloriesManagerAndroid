package com.example.caloriesmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;

    Animation topAnimation, bottomAnimation;
    ImageView image;
    TextView txtAppName, txtDescription;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null){
                    Intent intent = new Intent(
                            MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, SPLASH_SCREEN);
    }
}
