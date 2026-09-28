package com.example.recivox.quarter2.test.quarter2.practical;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class loginRegistrationMenu_TRIAL {

    private String registeredUsername;
    private String registeredPassword;

    @Test
    public void start() {

        StringBuilder automatedInput = new StringBuilder();

        System.out.println("======= RECIVOX LOGIN & REGISTRATION =======");

// Step 1: Choose Register
        automatedInput.append("1\n");

// Step 2: Create username
        automatedInput.append("Neil\n");

// Step 3: Create password
        automatedInput.append("12345\n");

// Step 4: Choose Login
        automatedInput.append("2\n");

// Step 5: Enter correct username
        automatedInput.append("Neil\n");

// Step 6: Enter correct password
        automatedInput.append("12345\n");

// Step 7: Choose Learner
        automatedInput.append("1\n");

// Step 8: Exit Learner ReciRoom
        automatedInput.append("2\n");

// Step 9: Choose Educator
        automatedInput.append("2\n");

// Step 10: Create ReciRoom
        automatedInput.append("1\n");

// Step 11: Exit Educator ReciRoom
        automatedInput.append("2\n");

// Step 12: Exit ReciVox
        automatedInput.append("3\n");
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(automatedInput.toString().getBytes());

        Scanner scanner = new Scanner(inputStream);

        boolean running = true;

        while (running && scanner.hasNextLine()) {

            System.out.println("\n===== RECIVOX =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    System.out.println("\n===== REGISTER =====");

                    System.out.print("Create username: ");
                    String username = scanner.nextLine();

                    System.out.print("Create password: ");
                    String password = scanner.nextLine();

                    if (username.isEmpty() || password.isEmpty()) {
                        System.out.println("Username and password cannot be empty.");
                    } else {
                        registeredUsername = username;
                        registeredPassword = password;

                        System.out.println("Registration successful!");
                    }
                    break;

                case "2":
                    System.out.println("\n===== LOGIN =====");

                    if (registeredUsername == null) {
                        System.out.println("No account registered yet.");
                        break;
                    }

                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();

                    if (loginUsername.equals(registeredUsername)
                            && loginPassword.equals(registeredPassword)) {

                        System.out.println("Login successful!");

                        roleSelectionMenu roleMenu = new roleSelectionMenu ();
                        roleMenu.start(scanner);

                    } else {

                        System.out.println("Invalid username or password.");
                    }
                    break;

                case "3":
                    System.out.println("\nExiting ReciVox...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("\n======= CREDENTIALS APPROVED =======");
    }
}