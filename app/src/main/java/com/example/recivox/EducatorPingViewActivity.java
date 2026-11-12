
package com.example.recivox;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class EducatorPingViewActivity extends AppCompatActivity {

    private TextView pendingPingsCount, answeredPingsCount;
    private LinearLayout incomingPingsContainer;

    private SharedPreferences preferences;

    private static final String PREFS_NAME = "RecivoxPings";
    private static final String PINGS_KEY = "pingList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_educator_ping_view);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        pendingPingsCount = findViewById(R.id.pendingPingsCount);
        answeredPingsCount = findViewById(R.id.answeredPingsCount);
        incomingPingsContainer = findViewById(R.id.incomingPingsContainer);

        setupNavigation();
        loadPings();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (incomingPingsContainer != null) {
            loadPings();
        }
    }

    private JSONArray getPings() {
        try {
            String savedData = preferences.getString(PINGS_KEY, "[]");
            return new JSONArray(savedData);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    private void loadPings() {

        incomingPingsContainer.removeAllViews();

        JSONArray pings = getPings();

        int pending = 0;
        int answered = 0;

        for (int i = 0; i < pings.length(); i++) {

            JSONObject ping = pings.optJSONObject(i);

            if (ping == null) continue;

            String status = ping.optString("status", "Pending");

            if (status.equals("Answered")) {
                answered++;
            } else {
                pending++;
            }

            createPingCard(ping);
        }

        pendingPingsCount.setText(
                "PENDING\n" + pending + " questions"
        );

        answeredPingsCount.setText(
                "ANSWERED\n" + answered + " questions"
        );

        if (pings.length() == 0) {
            TextView emptyMessage = new TextView(this);

            emptyMessage.setText("No learner pings yet.");
            emptyMessage.setTextColor(Color.GRAY);
            emptyMessage.setTextSize(15);
            emptyMessage.setPadding(10, 20, 10, 20);

            incomingPingsContainer.addView(emptyMessage);
        }
    }

    private void createPingCard(JSONObject ping) {

        String pingId = ping.optString("id");
        String learnerId = ping.optString("learnerId");
        String room = ping.optString("room");
        String category = ping.optString("category");
        String topic = ping.optString("topic");
        String question = ping.optString("question");
        String status = ping.optString("status", "Pending");

        LinearLayout card = new LinearLayout(this);

        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(35, 30, 35, 30);
        card.setBackgroundColor(Color.WHITE);
        card.setElevation(4);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        cardParams.bottomMargin = 30;
        card.setLayoutParams(cardParams);

        addText(card, "Learner ID: " + learnerId, 15, "#6D45C7", true);
        addText(card, "ReciRoom: " + room, 15, "#252139", true);
        addText(card, "Category: " + category, 15, "#252139", true);
        addText(card, "Topic: " + topic, 17, "#252139", true);
        addText(card, "Question: " + question, 15, "#514B62", false);

        TextView statusView = addText(
                card,
                "Status: " + status,
                14,
                status.equals("Answered") ? "#27834A" : "#B26A00",
                true
        );

        Button respondButton = new Button(this);

        respondButton.setText(
                status.equals("Answered")
                        ? "View Response"
                        : "View and Respond"
        );

        respondButton.setAllCaps(false);
        respondButton.setTextColor(Color.WHITE);
        respondButton.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(
                        Color.rgb(109, 69, 199)
                )
        );

        card.addView(respondButton);

        respondButton.setOnClickListener(v ->
                openPing(pingId)
        );

        incomingPingsContainer.addView(card);
    }

    private TextView addText(
            LinearLayout parent,
            String value,
            int size,
            String color,
            boolean bold
    ) {

        TextView textView = new TextView(this);

        textView.setText(value);
        textView.setTextSize(size);
        textView.setTextColor(Color.parseColor(color));

        if (bold) {
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        }

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.bottomMargin = 15;

        textView.setLayoutParams(params);
        parent.addView(textView);

        return textView;
    }

    private void openPing(String pingId) {

        JSONArray pings = getPings();

        for (int i = 0; i < pings.length(); i++) {

            JSONObject ping = pings.optJSONObject(i);

            if (ping == null ||
                    !ping.optString("id").equals(pingId)) {
                continue;
            }

            String learnerId = ping.optString("learnerId");
            String topic = ping.optString("topic");
            String question = ping.optString("question");
            String response = ping.optString("response", "");
            String status = ping.optString("status", "Pending");

            if (status.equals("Answered")) {

                new AlertDialog.Builder(this)
                        .setTitle("Educator Response")
                        .setMessage(
                                "Learner ID: " + learnerId +
                                        "\nTopic: " + topic +
                                        "\n\nQuestion: " + question +
                                        "\n\nResponse: " + response
                        )
                        .setPositiveButton("Close", null)
                        .show();

                return;
            }

            showResponseDialog(pingId, learnerId, topic, question);
            return;
        }
    }

    private void showResponseDialog(
            String pingId,
            String learnerId,
            String topic,
            String question
    ) {

        EditText responseInput = new EditText(this);

        responseInput.setHint("Type your response here...");
        responseInput.setMinLines(3);
        responseInput.setPadding(30, 20, 30, 20);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Respond to Ping")
                .setMessage(
                        "Learner ID: " + learnerId +
                                "\nTopic: " + topic +
                                "\n\nQuestion: " + question
                )
                .setView(responseInput)
                .setPositiveButton("Send Response", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {

            Button sendButton = dialog.getButton(
                    AlertDialog.BUTTON_POSITIVE
            );

            sendButton.setOnClickListener(v -> {

                String response = responseInput.getText()
                        .toString().trim();

                if (response.isEmpty()) {
                    responseInput.setError(
                            "Please enter a response."
                    );
                    return;
                }

                saveResponse(pingId, response);

                dialog.dismiss();

                Toast.makeText(
                        this,
                        "Response saved successfully!",
                        Toast.LENGTH_SHORT
                ).show();

                loadPings();
            });
        });

        dialog.show();
    }

    private void saveResponse(String pingId, String response) {

        JSONArray pings = getPings();

        for (int i = 0; i < pings.length(); i++) {

            JSONObject ping = pings.optJSONObject(i);

            if (ping == null ||
                    !ping.optString("id").equals(pingId)) {
                continue;
            }

            try {
                ping.put("response", response);
                ping.put("status", "Answered");

                preferences.edit()
                        .putString(PINGS_KEY, pings.toString())
                        .apply();

            } catch (Exception e) {
                Toast.makeText(
                        this,
                        "Unable to save response.",
                        Toast.LENGTH_SHORT
                ).show();
            }

            return;
        }
    }

    private void setupNavigation() {

        findViewById(R.id.navHome).setOnClickListener(v -> finish());

        findViewById(R.id.navRooms).setOnClickListener(v ->
                Toast.makeText(this, "Rooms selected",
                        Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navPings).setOnClickListener(v ->
                Toast.makeText(this, "You are already in Pings",
                        Toast.LENGTH_SHORT).show()
        );

        findViewById(R.id.navProfile).setOnClickListener(v ->
                Toast.makeText(this, "Profile selected",
                        Toast.LENGTH_SHORT).show()
        );
    }
}
