package pl.coderslab.controller;

import java.util.Scanner;

public class Scanners {

    public static int getIntFromUser(Scanner scanner, String message) {
        System.out.print(message);

        while (true) {
            try {
                int id = scanner.nextInt();
                return id;
            } catch (Exception e) {
                System.out.println("Niepoprawna wartość");
                scanner.next();
            }
        }
    }

    public static String getStringFromUser(Scanner scanner, String message){
        System.out.print(message);
        return scanner.nextLine();
    }
}
