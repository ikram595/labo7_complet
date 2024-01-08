package com.example.myrecyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private TextView displayNameTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        displayNameTextView = findViewById(R.id.display_name_text_view);
        emailTextView = findViewById(R.id.email_text_view);

        // Fetch and display user data
        displayUserData();
    }
    private void displayUserData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();
String userName=extractUsername(email);
            displayNameTextView.setText("Nom d'utilisateur: " + userName);
            emailTextView.setText("Email: " + (email != null ? email : "N/A"));
        }
    }
    private static String extractUsername(String email) {
        int atIndex = email.indexOf('@');

            return email.substring(0, atIndex);

    }
}