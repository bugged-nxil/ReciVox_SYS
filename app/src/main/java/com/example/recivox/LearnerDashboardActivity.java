
package com.example.recivox;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LearnerDashboardActivity extends AppCompatActivity {

    TextView learnerGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_learner_dashboard);

        learnerGreeting = findViewById(R.id.learnerGreeting);

        String name = getIntent().getStringExtra("USER_NAME");

        if (name != null && !name.isEmpty()) {
            learnerGreeting.setText("Hello, " + name + "!");
        }
    }
}