
package com.example.recivox;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EducatorDashboardActivity extends AppCompatActivity {

    TextView educatorGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_educator_dashboard);

        educatorGreeting = findViewById(R.id.educatorGreeting);

        String name = getIntent().getStringExtra("USER_NAME");

        if (name != null && !name.isEmpty()) {
            educatorGreeting.setText("Hello, " + name + "!");
        }
    }
}
