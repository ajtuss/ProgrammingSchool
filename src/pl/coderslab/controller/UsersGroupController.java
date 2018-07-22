package pl.coderslab.controller;

import pl.coderslab.entity.UserGroup;

import java.sql.SQLException;
import java.util.Scanner;

public class UsersGroupController {

    static void startManagingUsers() {
        Scanner scanner = new Scanner(System.in);

        int action = choseAction(scanner);
        switch (action) {
            case 0:
                MainController.startApp();
                break;
            case 1:
                addUserGroup(scanner);
                break;
            case 2:
                updateUserGroup(scanner);
                break;
            case 3:
                deleteUserGroup(scanner);
                break;
        }

    }

    private static int choseAction(Scanner scanner) {
        while (true) {
            printUserGroups();
            System.out.println("What you want to do: ");
            System.out.println("1 - Add");
            System.out.println("2 - Edit");
            System.out.println("3 - Delete");
            System.out.println("0 - Main menu");
            try {
                int option = scanner.nextInt();
                if (option < 0 || option > 3) {
                    throw new Exception("Wrong value");
                }
                return option;

            } catch (Exception e) {
                System.out.println("Wrong value");
                scanner.next();
            }
        }
    }

    private static void addUserGroup(Scanner scanner) {
        UserGroup userGroup = new UserGroup();
        System.out.println("Write name for user group: ");
        String name = scanner.nextLine();
        userGroup.setName(name);
        try {
            userGroup.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateUserGroup(Scanner scanner) {
        UserGroup userGroup;
        int id = Scanners.getIntFromUser(scanner, "Write id for edit: ");
        try {
            while (true) {
                userGroup = UserGroup.loadById(id);
                if(userGroup!=null)
                    break;
                System.out.println("User group doesn't exist.");
            }
            String name = Scanners.getStringFromUser(scanner, "Write new name: ");
            userGroup.setName(name);
            userGroup.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUserGroup(Scanner scanner) {
        System.out.println("Starting to delete user group");
    }

    private static void printUserGroups() {

    }
}
