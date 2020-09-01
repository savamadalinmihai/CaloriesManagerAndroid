package com.example.caloriesmanager.View.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caloriesmanager.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class UserProfileFragment extends Fragment {
    TextView profileSectionUsername;
    ImageView profileSectionImage;
    Button logOutButton;

    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileSectionUsername = view.findViewById(R.id.profileSectionUsername);
        profileSectionImage = view.findViewById(R.id.profileSectionImage);
//        logOutButton = view.findViewById(R.id.profileSectionLogOutButton);
//        logOutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // signing the user out when clicking sign out button in profile section
//                mAuth.signOut();
//                LoginManager.getInstance().logOut();
//            }
//        });


        //initialise Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        // setting the username and image to logged in user
        if (mUser != null) {
            String name = mUser.getDisplayName();
            String photoUrl = mUser.getPhotoUrl().toString() + "?type=large";

            profileSectionUsername.setText(name);
            Picasso.get().load(photoUrl).into(profileSectionImage);
        }
    }
}