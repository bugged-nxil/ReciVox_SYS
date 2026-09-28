package com.example.recivox.quarter2.test.quarter2.practical;

import java.util.Scanner;

public class roleSelectionMenu {

    public void start(Scanner scanner) {

        boolean running = true;

        while (running) {

            System.out.println("\n===== RECIVOX ROLE SELECTION =====");
            System.out.println("1. Learner");
            System.out.println("2. Educator");
            System.out.println("3. Logout");
            System.out.print("Choose your role: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    System.out.println("Learner selected.");

                    reciRoomAccessMenu_LRN lrnAccess =
                            new reciRoomAccessMenu_LRN();

                    lrnAccess.start(scanner);
                    break;

                case "2":
                    System.out.println("Educator selected.");

                    reciRoomAccessMenu_EDU eduAccess =
                            new reciRoomAccessMenu_EDU();

                    eduAccess.start(scanner);
                    break;

                case "3":
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}