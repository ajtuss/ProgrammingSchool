package pl.coderslab.controller;

import java.util.Scanner;

public class UsersGroupController {

    static void startManagingUsers(){

        int action = choseAction();
        switch (action){
            case 1:
                break;
        }

    }

    private static int choseAction() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What you want to do: ");
            System.out.println("1 - Add");
            System.out.println("2 - Edit");
            System.out.println("3 - Delete");
            try {
                int option = scanner.nextInt();
                if (option < 1 || option > 3) {
                    throw new Exception("Wrong value");
                }
                return option;

            } catch (Exception e) {
                System.out.println("Wrong value");
                scanner.next();
            }
        }
    }

    private static void addUser(){
        System.out.println("Starting to add user group");
    }
}
