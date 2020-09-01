package com.example.caloriesmanager.View;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.caloriesmanager.R;
import com.example.caloriesmanager.View.Fragments.UserHomeFragment;
import com.example.caloriesmanager.View.Fragments.UserProfileFragment;
import com.example.caloriesmanager.View.Fragments.UserStatsFragment;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    BottomNavigationView fragmentSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        fragmentSwitcher = findViewById(R.id.bottomNavigationView);
        fragmentSwitcher.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout,
                new UserHomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.menu_item_home:
                            selectedFragment = new UserHomeFragment();
                            break;
                        case R.id.menu_item_stats:
                            selectedFragment = new UserStatsFragment();
                            break;
                        case R.id.menu_item_profile:
                            selectedFragment = new UserProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace
                            (R.id.fragmentContainerFrameLayout, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(),
                    "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}