package com.example.caloriesmanager.View;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    // declaration of views
    BottomNavigationView fragmentSwitcher;
    CalendarView calendarView;

    // trying to use a Date object to pass to Calendar view as selected date
    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // assignment of bottomnavigationview hook
        fragmentSwitcher = findViewById(R.id.bottomNavigationView);
        fragmentSwitcher.setOnNavigationItemSelectedListener(navListener);

        // this sets userhomefragment as the default selected screen when app is launched
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerFrameLayout,
                new UserHomeFragment()).commit();

        // hooking calendarview object in order to do operations on it
        calendarView = findViewById(R.id.calendarView);
    }


    // this method is always listening for clicks on bottomnavigationview and navigating
    // the user to the correct fragment
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

    // this method overrides the default behaviour of onBackPressed, requiring the user
    // to press the back button twice and confirming exit with a toast message
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