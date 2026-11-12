
package com.example.recivox;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

public class LearnerPingViewActivity extends AppCompatActivity {

    private Spinner roomSpinner, categorySpinner;
    private EditText topicInput, questionInput;
    private CheckBox privateCheckBox;
    private Button sendPingButton;

    private static final String PREFS_NAME = "RecivoxPings";
    private static final String PINGS_KEY = "pingList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learner_ping_view);

        roomSpinner = findViewById(R.id.roomSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        topicInput = findViewById(R.id.topicInput);
        questionInput = findViewById(R.id.questionInput);
        privateCheckBox = findViewById(R.id.privateCheckBox);
        sendPingButton = findViewById(R.id.sendPingButton);

        setupSpinners();

        sendPingButton.setOnClickListener(v -> sendPing());
    }

    private void setupSpinners() {

        String[] rooms = {
                "Item 1",
                "Room 101",
                "Room 102"
        };

        String[] categories = {
                "General Question",
                "Lesson Clarification",
                "Technical Problem",
                "Assignment",
                "Other"
        };

        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                rooms
        );

        roomAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        roomSpinner.setAdapter(roomAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );

        categoryAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        categorySpinner.setAdapter(categoryAdapter);
    }

    private void sendPing() {

        String room = roomSpinner.getSelectedItem().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String topic = topicInput.getText().toString().trim();
        String question = questionInput.getText().toString().trim();

        if (topic.isEmpty()) {
            topicInput.setError("Please enter a topic.");
            return;
        }

        if (question.isEmpty()) {
            questionInput.setError("Please enter your question.");
            return;
        }

        // Retrieve the logged-in learner ID.
        // Change the preference name/key if your login uses different ones.
        String learnerId = getSharedPreferences(
                "LearnerAccount", MODE_PRIVATE
        ).getString("learnerId", "Unknown Learner");

        try {
            android.content.SharedPreferences preferences =
                    getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

            String savedData = preferences.getString(PINGS_KEY, "[]");

            JSONArray pings = new JSONArray(savedData);

            JSONObject newPing = new JSONObject();

            newPing.put("id", UUID.randomUUID().toString());
            newPing.put("learnerId", learnerId);
            newPing.put("room", room);
            newPing.put("category", category);
            newPing.put("topic", topic);
            newPing.put("question", question);
            newPing.put("private", privateCheckBox.isChecked());
            newPing.put("status", "Pending");
            newPing.put("response", "");

            pings.put(newPing);

            preferences.edit()
                    .putString(PINGS_KEY, pings.toString())
                    .apply();

            Toast.makeText(
                    this,
                    "Your ping has been sent!",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "Unable to send ping.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
