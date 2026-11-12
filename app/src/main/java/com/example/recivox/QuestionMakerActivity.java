

package com.example.recivox;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.UUID;

public class QuestionMakerActivity extends Activity {

    TextView questionNumber, questionSerialNumber;

    EditText questionTitleInput;
    EditText questionTextInput;
    EditText questionPointsInput;
    EditText identificationAnswerInput;

    EditText[] choices;

    Spinner questionTypeSpinner;
    Spinner choiceCountSpinner;
    Spinner correctChoiceSpinner;

    LinearLayout multipleChoiceSection;
    LinearLayout identificationSection;
    LinearLayout essaySection;

    Button saveQuestionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_maker);

        // Connect XML components

        questionNumber = findViewById(R.id.questionNumber);
        questionSerialNumber = findViewById(R.id.questionSerialNumber);

        questionTitleInput = findViewById(R.id.questionTitleInput);
        questionTextInput = findViewById(R.id.questionTextInput);
        questionPointsInput = findViewById(R.id.questionPointsInput);
        identificationAnswerInput = findViewById(R.id.identificationAnswerInput);

        questionTypeSpinner = findViewById(R.id.questionTypeSpinner);
        choiceCountSpinner = findViewById(R.id.choiceCountSpinner);
        correctChoiceSpinner = findViewById(R.id.correctChoiceSpinner);

        multipleChoiceSection = findViewById(R.id.multipleChoiceSection);
        identificationSection = findViewById(R.id.identificationSection);
        essaySection = findViewById(R.id.essaySection);

        saveQuestionButton = findViewById(R.id.saveQuestionButton);

        // Connect choice fields

        choices = new EditText[] {
                findViewById(R.id.choiceAInput),
                findViewById(R.id.choiceBInput),
                findViewById(R.id.choiceCInput),
                findViewById(R.id.choiceDInput),
                findViewById(R.id.choiceEInput),
                findViewById(R.id.choiceFInput)
        };

        // Question number

        questionNumber.setText("Question 1");

        // Generate serial number

        String serial = UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        questionSerialNumber.setText(serial);

        // Question type options

        String[] types = {
                "Multiple Choice",
                "Identification",
                "Essay"
        };

        ArrayAdapter<String> typeAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        types
                );

        typeAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        questionTypeSpinner.setAdapter(typeAdapter);

        // Choice count options

        String[] counts = {"2", "3", "4", "5", "6"};

        ArrayAdapter<String> countAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        counts
                );

        countAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        choiceCountSpinner.setAdapter(countAdapter);

        // Update choices

        choiceCountSpinner.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        updateChoices(position + 2);
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        // Change question type

        questionTypeSpinner.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        multipleChoiceSection.setVisibility(View.GONE);
                        identificationSection.setVisibility(View.GONE);
                        essaySection.setVisibility(View.GONE);

                        if (position == 0) {
                            multipleChoiceSection.setVisibility(View.VISIBLE);
                        } else if (position == 1) {
                            identificationSection.setVisibility(View.VISIBLE);
                        } else {
                            essaySection.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );

        // Save button

        saveQuestionButton.setOnClickListener(v -> saveQuestion());
    }

    // Update choice fields

    private void updateChoices(int count) {

        String[] answers = new String[count];

        for (int i = 0; i < choices.length; i++) {

            if (i < count) {
                choices[i].setVisibility(View.VISIBLE);
            } else {
                choices[i].setVisibility(View.GONE);
            }

            choices[i].setError(null);
        }

        for (int i = 0; i < count; i++) {
            answers[i] = "Choice " + (char) ('A' + i);
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        answers
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        correctChoiceSpinner.setAdapter(adapter);
    }

    // Save question

    private void saveQuestion() {

        String title = questionTitleInput.getText().toString().trim();
        String question = questionTextInput.getText().toString().trim();
        String points = questionPointsInput.getText().toString().trim();

        if (title.isEmpty()) {
            questionTitleInput.setError("Enter question title");
            return;
        }

        if (question.isEmpty()) {
            questionTextInput.setError("Enter question");
            return;
        }

        if (points.isEmpty()) {
            questionPointsInput.setError("Enter points");
            return;
        }

        String type = questionTypeSpinner.getSelectedItem().toString();

        StringBuilder result = new StringBuilder();

        result.append("Question: 1\n");
        result.append("Serial: ").append(
                questionSerialNumber.getText()
        );
        result.append("\nTitle: ").append(title);
        result.append("\nType: ").append(type);
        result.append("\nQuestion: ").append(question);

        // Multiple Choice

        if (type.equals("Multiple Choice")) {

            int count = Integer.parseInt(
                    choiceCountSpinner.getSelectedItem().toString()
            );

            for (int i = 0; i < count; i++) {

                String answer = choices[i].getText()
                        .toString().trim();

                if (answer.isEmpty()) {
                    choices[i].setError("Enter choice");
                    return;
                }

                result.append("\n")
                        .append((char) ('A' + i))
                        .append(". ")
                        .append(answer);
            }

            result.append("\nCorrect Answer: ")
                    .append(correctChoiceSpinner.getSelectedItem());
        }

        // Identification

        else if (type.equals("Identification")) {

            String answer = identificationAnswerInput
                    .getText().toString().trim();

            if (answer.isEmpty()) {
                identificationAnswerInput.setError("Enter answer");
                return;
            }

            result.append("\nCorrect Answer: ").append(answer);
        }

        // Essay

        else if (type.equals("Essay")) {
            result.append("\nAnswer Format: Essay");
        }

        result.append("\nPoints: ").append(points);

        new AlertDialog.Builder(this)
                .setTitle("Question Saved!")
                .setMessage(result.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}
