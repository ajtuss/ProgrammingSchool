package pl.coderslab.controller;

import java.util.Scanner;

public class MainController {

    public static void main(String[] args) {
        int action = choseAction();
        switch (action) {
            case 1:
                System.out.println("Option1");
                break;
            case 2:
                System.out.println("Option2");
                break;
            case 3:
                System.out.println("Option3");
                break;
            case 4:
                System.out.println("Option4");
                break;
        }
    }

    private static int choseAction() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What you want to do: ");
            System.out.println("1 - Manage users");
            System.out.println("2 - Manage user group");
            System.out.println("3 - Manage questions");
            System.out.println("4 - Take exam");
            try {
                int option = scanner.nextInt();
                if (option < 1 || option > 4) {
                    throw new Exception("Wrong value");
                }
                return option;

            } catch (Exception e) {
                System.out.println("Wrong value");
                scanner.next();
            }
        }
    }
}
