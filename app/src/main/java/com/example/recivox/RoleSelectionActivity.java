
package com.example.recivox;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_role_selection);

        String role = getIntent().getStringExtra("USER_ROLE");
        String name = getIntent().getStringExtra("USER_NAME");
        String email = getIntent().getStringExtra("USER_EMAIL");

        if (role == null) {
            Toast.makeText(this,
                    "Unable to identify your account role.",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Intent intent;

        if (role.equalsIgnoreCase("Learner")) {

            intent = new Intent(
                    RoleSelectionActivity.this,
                    LearnerDashboardActivity.class
            );

        } else if (role.equalsIgnoreCase("Educator")) {

            intent = new Intent(
                    RoleSelectionActivity.this,
                    EducatorDashboardActivity.class
            );

        } else {

            Toast.makeText(this,
                    "Invalid account role.",
                    Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        intent.putExtra("USER_NAME", name);
        intent.putExtra("USER_EMAIL", email);
        intent.putExtra("USER_ROLE", role);

        startActivity(intent);
        finish();
    }
}