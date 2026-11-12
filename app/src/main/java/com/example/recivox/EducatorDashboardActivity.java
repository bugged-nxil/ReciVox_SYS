
package com.example.recivox;

import android.os.Bundle;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EducatorDashboardActivity extends AppCompatActivity {

    private LinearLayout navHome, navPings, navRooms, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educator_dashboard);

        initializeViews();
        setupNavigation();
    }

    // INITIALIZE NAVIGATION VIEWS
    private void initializeViews() {

        navHome = findViewById(R.id.navHome);
        navPings = findViewById(R.id.navPings);
        navRooms = findViewById(R.id.navRooms);
        navProfile = findViewById(R.id.navProfile);
    }

    // BOTTOM NAVIGATION
    private void setupNavigation() {

        // HOME - CURRENT SCREEN
        navHome.setOnClickListener(v -> {
            // Stay on Home
        });

        // HOME TO PINGS
        navPings.setOnClickListener(v -> {

            Intent intent = new Intent(
                    EducatorDashboardActivity.this,
                    EducatorPingViewActivity.class
            );

            startActivity(intent);
            finish();
        });

        // ROOMS PLACEHOLDER
        navRooms.setOnClickListener(v -> {

            Toast.makeText(
                    EducatorDashboardActivity.this,
                    "Rooms selected",
                    Toast.LENGTH_SHORT
            ).show();
        });

        // PROFILE PLACEHOLDER
        navProfile.setOnClickListener(v -> {

            Toast.makeText(
                    EducatorDashboardActivity.this,
                    "Profile selected",
                    Toast.LENGTH_SHORT
            ).show();
        });
//PINGS TO HOME
        navHome.setOnClickListener(v -> {

            Intent intent = new Intent(
                    EducatorDashboardActivity.this,
                    EducatorPingViewActivity.class
            );

            startActivity(intent);
            finish();
        });
    }
}
