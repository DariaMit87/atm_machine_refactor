package org.example.view;

import org.example.controller.TransactionController;
import org.example.controller.UserController;
import org.example.model.ResourcesDAO;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.model.Resources;
import org.example.controller.ResourcesController;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    public UserController userController = new UserController();
    public TransactionController transactionController = new TransactionController();
    ResourcesDAO resourcesDAO = new ResourcesDAO();
    public ResourcesController resourcesController = new ResourcesController(resourcesDAO);
    private final Scanner scanner = new Scanner(System.in);
    private User loggedInUser;

    public void showMenu() {
        while (true) {
            System.out.println("Welcome to the ATM");
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                login();
            } else if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void login() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        loggedInUser = userController.authenticateUser(name, pin);

        if (loggedInUser != null) {
            System.out.println("Login successful. Welcome, " + loggedInUser.getName() + "!");
            if ("technician".equals(loggedInUser.getRole())) {
                showTechnicianMenu();
            } else {
                showUserMenu();
            }
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private void showUserMenu() {
        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Funds");
            System.out.println("5. View Transactions");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    depositFunds();
                    break;
                case 3:
                    withdrawFunds();
                    break;
                case 4:
                    transferFunds();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    loggedInUser = null;
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void showTechnicianMenu() {
        while (true) {
            System.out.println("\nTechnician Menu:");
            System.out.println("1. View Resources");
            System.out.println("2. Refill Resources");
            System.out.println("3. Add New User");
            System.out.println("4. View all Users");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewResources();
                    break;
                case 2:
                    refillResources();
                    break;
                case 3:
                    addNewUser();
                    break;
                case 4:
                    viewAllUsers();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    loggedInUser = null;
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private void checkBalance() {
        double balance = userController.checkBalance(loggedInUser.getId());
        System.out.println("Your current balance: $" + balance);
    }

    private void depositFunds() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        if (transactionController.deposit(loggedInUser.getId(), amount)) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Deposit failed!");
        }
        displayResourcesStatus();
    }

    private void withdrawFunds() {
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        if (transactionController.withdraw(loggedInUser.getId(), amount)) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
        displayResourcesStatus();
    }

    private void transferFunds() {
        System.out.print("Enter recipient Card number: ");
        String receiverCardNumber = scanner.nextLine();

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        if (transactionController.transfer(loggedInUser.getId(), receiverCardNumber, amount)) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed");
        }
        displayResourcesStatus();
    }

    private void viewTransactions() {
        List<Transaction> transactions = transactionController.getUserTransactions(loggedInUser.getId());

        if (transactions.isEmpty()) {
            System.out.println("No transaction history.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction t : transactions) {
                System.out.println(t.getType() + " - $" + t.getAmount() + " - " + t.getTimestamp());
            }
        }
    }

    private void displayResourcesStatus() {
        Resources resources = resourcesController.getResources();
        if (resources.isLowOnResources()) {
            System.out.println("Warning: Resources are low!");
        }
        if (resources.isOutOfResources()) {
            System.out.println("Warning: Resources are depleted! Please contact the technician.");
        }
    }


    private void viewResources() {
        Resources resources = resourcesController.getResources();
        System.out.println("Current ATM resources: ");
        System.out.println("Ink: " + resources.getInk());
        System.out.println("Paper: " + resources.getPaper());
        System.out.println("Cash: $" + resources.getCash());
        System.out.println("Software Update: " + resources.getSoftwareUpdate());
    }


    private void refillResources() {
        if (resourcesController.refillResources()) {
            System.out.println("Resources have been successfully refilled!");
        } else {
            System.out.println("Failed to refill resources.");
        }
    }

    private void addNewUser() {
        System.out.print("Enter new user's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new user's card number: ");
        String cardNumber = scanner.nextLine();

        System.out.print("Enter new user's PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter role (customer/technician): ");
        String role = scanner.nextLine();

        userController.addUser(name, cardNumber, pin, balance, role);
    }

    private void viewAllUsers() {
        List<User> users = userController.getAllUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("\nList of Users:");
            System.out.println("------------------------------------------");
            for (User user : users) {
                System.out.println("ID: " + user.getId());
                System.out.println("Name: " + user.getName());
                System.out.println("Card Number: " + user.getCardNumber());
                System.out.println("Balance: $" + user.getBalance());
                System.out.println("Role: " + user.getRole());
                System.out.println("------------------------------------------");
            }
        }
    }
}


