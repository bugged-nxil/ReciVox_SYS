package com.example.recivox.quarter2.test.quarter2.practical;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class loginRegistrationMenu_TRIAL {

    @Test
    public void testLoginRegistrationFlow() {
        StringBuilder automatedInput = new StringBuilder();

        System.out.println("======= AUTHORIZING LOGIN CREDENTIALS =======");

        // Step 1: Login option
        automatedInput.append("1\n");

        // Step 2: Test login credentials
        automatedInput.append("2\n");
        automatedInput.append("200\n");

        // Step 3: Test second login condition
        automatedInput.append("2\n");
        automatedInput.append("600\n");

        // Step 4: Exit system
        automatedInput.append("3\n");

        System.out.println("======= CREDENTIALS APPROVED - PROCEED =======\n");

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(automatedInput.toString().getBytes());

        Scanner scanner = new Scanner(inputStream);

    }
}