package com.example.recivox.quarter2.test.quarter2.practical;

import java.util.Scanner;

public class reciRoomAccessMenu_LRN {

    public void start(Scanner scanner) {

        boolean running = true;

        while (running) {

            System.out.println("\n===== LEARNER RECIROOM =====");
            System.out.println("1. Join ReciRoom");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("\nEnter ReciRoom Code: ");
                    String roomCode = scanner.nextLine();

                    System.out.println("Room code entered: " + roomCode);
                    System.out.println("Checking ReciRoom...");
                    break;

                case "2":
                    System.out.println("Exiting ReciRoom...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}