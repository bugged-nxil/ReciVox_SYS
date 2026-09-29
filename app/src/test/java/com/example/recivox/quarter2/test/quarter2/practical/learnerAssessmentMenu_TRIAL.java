package com.example.recivox.quarter2.test.quarter2.practical;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class learnerAssessmentMenu_TRIAL {

    @Test
    public void testLearnerAssessmentFlow() {
        StringBuilder automatedInput = new StringBuilder();

        System.out.println("[]---[] GATHERING & COMPILING LEARNER ASSESSMENT INFO []---[]");

        // Step 1: Enter assessment option
        automatedInput.append("1\n"); // Choose Assessment

        // Step 2: Test correct answer
        automatedInput.append("1\n"); // Expected: Correct / Add Points

        // Step 3: Test wrong answer
        automatedInput.append("2\n"); // Expected: Wrong / No Points

        // Step 4: Exit system
        automatedInput.append("3\n"); // Choose Exit

        System.out.println("[]---[] LEARNER ASSESSMENT HAS BEEN UPDATED []---[]\n");

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(automatedInput.toString().getBytes());

        Scanner scanner = new Scanner(inputStream);
    }
}