package com.example.recivox.quarter2.test.quarter2.practical;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class reciVoxSession_TRIAL{ 

    @Test
    public void testReciVoxSessionFlow() {
        StringBuilder automatedInput = new StringBuilder();

        System.out.println("-=-=-| FETCHING RECIROOM SERVERS |-=-=-");

        // Step 1: Enter session option
        automatedInput.append("1\n"); // Choose Enter Session

        // Step 2: Test first session option
        automatedInput.append("2\n"); // Choose Session Option

        // Step 3: Test second session option
        automatedInput.append("2\n"); // Choose Session Option

        // Step 4: Exit system
        automatedInput.append("3\n"); // Choose Exit

        System.out.println("-=-=-| ACCESS CONFIRMED ,, JOIN NOW |-=-=-\n");

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(automatedInput.toString().getBytes());

        Scanner scanner = new Scanner(inputStream);

    }
}
