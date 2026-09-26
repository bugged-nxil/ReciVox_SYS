package com.example.recivox.quarter2.test.quarter2.practical;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class reciRoomAccessMenu_TRIAL {

    @Test
    public void testReciRoomAccessFlow() {
        StringBuilder automatedInput = new StringBuilder();

        System.out.println("|=====| GENERATING RECIROOM ACCESS TEST DATA |=====|");

        // Step 1: Access ReciRoom
        automatedInput.append("1\n");

        // Step 2: Test first room condition
        automatedInput.append("2\n");
        automatedInput.append("10\n");

        // Step 3: Test second room condition
        automatedInput.append("2\n");
        automatedInput.append("50\n");

        // Step 4: Exit system
        automatedInput.append("3\n");

        System.out.println("|=====| TEST DATA GENERATION COMPLETE |=====|\n");

        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(automatedInput.toString().getBytes());

        Scanner scanner = new Scanner(inputStream);

    }
}