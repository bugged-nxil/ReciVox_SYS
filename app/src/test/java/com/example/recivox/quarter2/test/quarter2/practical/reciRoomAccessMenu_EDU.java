package com.example.recivox.quarter2.test.quarter2.practical;

import java.util.Scanner;
import java.util.Random;

public class reciRoomAccessMenu_EDU {

    public void start(Scanner scanner) {

        boolean running = true;
        Random random = new Random();

        while (running) {

            System.out.println("\n===== EDUCATOR RECIROOM =====");
            System.out.println("1. Create ReciRoom");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":

                    String roomCode = "REC" + (100 + random.nextInt(900));

                    System.out.println("\nReciRoom created successfully!");
                    System.out.println("Room Code: " + roomCode);
                    System.out.println("Share this code with your learners.");

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