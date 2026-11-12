
package com.example.recivox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EducatorProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educators_profile);

        TextView navHome = findViewById(R.id.navEducatorHome);
        TextView navRooms = findViewById(R.id.navEducatorRooms);
        TextView navPings = findViewById(R.id.navEducatorPings);
        TextView navProfile = findViewById(R.id.navEducatorProfile);

        // HOME
        navHome.setOnClickListener(v -> {
            startActivity(new Intent(
                    EducatorProfileActivity.this,
                    EducatorDashboardActivity.class
            ));
        });

        // ROOMS
        navRooms.setOnClickListener(v -> {
            Toast.makeText(this,
                    "ReciRooms selected",
                    Toast.LENGTH_SHORT).show();
        });

        // PINGS
        navPings.setOnClickListener(v -> {
            startActivity(new Intent(
                    EducatorProfileActivity.this,
                    EducatorPingViewActivity.class
            ));
        });

        // PROFILE
        navProfile.setOnClickListener(v -> {
            Toast.makeText(this,
                    "Already on Profile",
                    Toast.LENGTH_SHORT).show();
        });
    }
}