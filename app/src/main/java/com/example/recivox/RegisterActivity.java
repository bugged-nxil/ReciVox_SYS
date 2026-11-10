
package com.example.recivox;

import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class RegisterActivity extends AppCompatActivity {

    EditText fullNameInput, registerEmailInput;
    EditText registerPasswordInput, confirmPasswordInput;

    RadioGroup roleRadioGroup;
    Button registerButton;
    TextView backToLoginText;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        fullNameInput = findViewById(R.id.fullNameInput);
        registerEmailInput = findViewById(R.id.registerEmailInput);
        registerPasswordInput = findViewById(R.id.registerPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        registerButton = findViewById(R.id.registerButton);
        backToLoginText = findViewById(R.id.backToLoginText);


        preferences = getSharedPreferences("ReciVoxAccounts", MODE_PRIVATE);

        registerButton.setOnClickListener(v -> registerUser());

        backToLoginText.setOnClickListener(v -> finish());
    }

    private void registerUser() {

        String name = fullNameInput.getText().toString().trim();
        String email = registerEmailInput.getText().toString().trim().toLowerCase();
        String password = registerPasswordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();

        if (name.isEmpty() || email.isEmpty()
                || password.isEmpty() || confirmPassword.isEmpty()) {

            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerEmailInput.setError("Enter a valid email address");
            return;
        }

        if (password.length() < 6) {
            registerPasswordInput.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Passwords do not match");
            return;
        }

        int selectedRoleId = roleRadioGroup.getCheckedRadioButtonId();

        if (selectedRoleId == -1) {
            Toast.makeText(this, "Please select Learner or Educator.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String role;

        if (selectedRoleId == R.id.learnerRadio) {
            role = "Learner";
        } else {
            role = "Educator";
        }

        String savedAccounts = preferences.getString("accounts", "[]");

        try {
            JSONArray accounts = new JSONArray(savedAccounts);

            for (int i = 0; i < accounts.length(); i++) {
                JSONObject account = accounts.getJSONObject(i);

                if (account.getString("email").equals(email)) {
                    Toast.makeText(this, "Email is already registered.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            String saltString = Base64.encodeToString(salt, Base64.NO_WRAP);
            String passwordHash = hashPassword(password, salt);

            JSONObject newAccount = new JSONObject();

            newAccount.put("name", name);
            newAccount.put("email", email);
            newAccount.put("salt", saltString);
            newAccount.put("passwordHash", passwordHash);
            newAccount.put("role", role);

            accounts.put(newAccount);

            preferences.edit()
                    .putString("accounts", accounts.toString())
                    .apply();

            Toast.makeText(this, "Account created successfully!",
                    Toast.LENGTH_LONG).show();

            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Registration failed. Please try again.",
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
