
package com.example.recivox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LearnerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_dashboard);

        // Bottom Navigation
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navRooms = findViewById(R.id.navRooms);
        LinearLayout navPings = findViewById(R.id.navPings);
        LinearLayout navProfile = findViewById(R.id.navProfile);

        // Dashboard Buttons
        Button joinRoomButton = findViewById(R.id.joinRoomButton);
        Button sendPingButton = findViewById(R.id.sendPingButton);

        // HOME
        navHome.setOnClickListener(v -> {
            Toast.makeText(this,
                    "You are already on Home",
                    Toast.LENGTH_SHORT).show();
        });

        // REC IROOMS
        navRooms.setOnClickListener(v -> {
            Toast.makeText(this,
                    "ReciRooms selected",
                    Toast.LENGTH_SHORT).show();
        });

        // PINGS
        navPings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LearnerDashboardActivity.this,
                    LearnerPingViewActivity.class
            );
            startActivity(intent);
        });

        // PROFILE
        navProfile.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LearnerDashboardActivity.this,
                    ProfileActivity.class
            );
            startActivity(intent);
        });

        // JOIN A REC IROOM
        joinRoomButton.setOnClickListener(v -> {
            Toast.makeText(this,
                    "Join a ReciRoom selected",
                    Toast.LENGTH_SHORT).show();
        });

        // SEND A PING
        sendPingButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LearnerDashboardActivity.this,
                    LearnerPingViewActivity.class
            );
            startActivity(intent);
        });
    }
}