package com.example.recivox.quarter2.test.quarter2.practical;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class pingSystemMenu_TRIAL {

    @Test
    public void testPingSystemFlow() {
        StringBuilder automatedInput = new StringBuilder();

        System.out.println("<>---<> NOTIFYING EDUCATOR'S INTERFACE <>---<>");

        // Step 1: Send Ping option
        automatedInput.append("1\n"); // Choose Send Ping

        // Step 2: Enter Ping message
        automatedInput.append("I need help with the assessment.\n");

        // Step 3: Check Ping response
        automatedInput.append("2\n"); // Choose View/Check Response

        // Step 4: Exit system
        automatedInput.append("3\n"); // Choose Exit

        System.out.println("<>---<> PING REQUEST VOIDED/GRANTED <>---<>\n");

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(automatedInput.toString().getBytes());

        Scanner scanner = new Scanner(inputStream);

    }
}
