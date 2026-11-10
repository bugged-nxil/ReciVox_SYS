
package com.example.recivox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginButton, createAccountButton;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);

        loginButton = findViewById(R.id.loginButton);
        createAccountButton = findViewById(R.id.createAccountButton);

        preferences = getSharedPreferences(
                "ReciVoxAccounts",
                MODE_PRIVATE
        );

        createAccountButton.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        String email = emailInput.getText().toString()
                .trim().toLowerCase();

        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Please enter your email and password.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email address");
            return;
        }

        String savedAccounts = preferences.getString("accounts", "[]");

        try {
            JSONArray accounts = new JSONArray(savedAccounts);

            for (int i = 0; i < accounts.length(); i++) {

                JSONObject account = accounts.getJSONObject(i);

                String savedEmail = account.getString("email");

                if (savedEmail.equals(email)) {

                    String savedSalt = account.getString("salt");
                    String savedHash = account.getString("passwordHash");

                    byte[] salt = Base64.decode(
                            savedSalt,
                            Base64.NO_WRAP
                    );

                    String enteredHash = hashPassword(password, salt);

                    if (enteredHash.equals(savedHash)) {

                        String role = account.getString("role");
                        String name = account.getString("name");

                        Toast.makeText(this,
                                "Login successful! Welcome!",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(
                                LoginActivity.this,
                                RoleSelectionActivity.class
                        );

                        intent.putExtra("USER_EMAIL", email);
                        intent.putExtra("USER_ROLE", role);
                        intent.putExtra("USER_NAME", name);

                        startActivity(intent);
                        finish();

                        return;
                    }

                    break;
                }
            }

            Toast.makeText(this,
                    "Invalid email or password.",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {

            Toast.makeText(this,
                    "Something went wrong. Please try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String hashPassword(String password, byte[] salt)
            throws Exception {

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                120000,
                256
        );

        SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = factory.generateSecret(spec).getEncoded();

        spec.clearPassword();

        return Base64.encodeToString(hash, Base64.NO_WRAP);
    }
}
