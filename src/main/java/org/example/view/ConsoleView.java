package org.example.view;

import org.example.controller.TransactionController;
import org.example.controller.UserController;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.model.Resources;
import org.example.controller.ResourcesController;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private final UserController userController = new UserController();
    private final TransactionController transactionController = new TransactionController();
    private final ResourcesController resourcesController = new ResourcesController();  // Added ResourcesController to interact with resources
    private final Scanner scanner = new Scanner(System.in);
    private User loggedInUser;

    public void showMenu() {
        while (true) {
            System.out.println("Welcome to the ATM");
            System.out.println("1. Login");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
                showTechnicianMenu();  // Show the technician menu if the role is technician
            } else {
                showUserMenu();  // Regular user menu
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
            scanner.nextLine(); // Consume newline

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
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewResources();
                    break;
                case 2:
                    refillResources();
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
        double balance = userController.checkBalance(loggedInUser.getCardNumber());
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
        System.out.print("Enter recipient ID: ");
        int receiverId = scanner.nextInt();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        if (transactionController.transfer(loggedInUser.getId(), receiverId, amount)) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed! Check your balance or recipient ID.");
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

    // Display current resource status
    private void displayResourcesStatus() {
        Resources resources = resourcesController.getResources();
        if (resources.isLowOnResources()) {
            System.out.println("Warning: Resources are low!");
        }
        if (resources.isOutOfResources()) {
            System.out.println("Warning: Resources are depleted! Please contact the technician.");
        }
    }

    // Technician's method to view resources
    private void viewResources() {
        Resources resources = resourcesController.getResources();
        System.out.println("Current ATM resources: ");
        System.out.println("Ink: " + resources.getInk());
        System.out.println("Paper: " + resources.getPaper());
        System.out.println("Cash: $" + resources.getCash());
        System.out.println("Software Update: " + resources.getSoftwareUpdate());
    }

    // Technician's method to refill resources
    private void refillResources() {
        if (resourcesController.refillResources()) {
            System.out.println("Resources have been successfully refilled!");
        } else {
            System.out.println("Failed to refill resources.");
        }
    }
}


