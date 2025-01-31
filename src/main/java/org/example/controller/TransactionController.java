package org.example.controller;

import org.example.model.*;

import java.util.List;

public class TransactionController {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final UserDAO userDAO = new UserDAO();
    ResourcesDAO resourcesDAO = new ResourcesDAO();  // Or a mock in tests
    ResourcesController resourcesController = new ResourcesController(resourcesDAO);

    public boolean deposit(int userId, double amount) {
        User user = userDAO.getUserById(userId);
        if (user != null && amount > 0) {
            double newBalance = user.getBalance() + amount;
            userDAO.updateUserBalance(userId, newBalance);
            transactionDAO.addTransaction(userId, "Deposit", amount);
            resourcesController.updateResourcesAfterTransaction(amount, true);

            return true;
        }
        return false;
    }


    public boolean withdraw(int userId, double amount) {
        User user = userDAO.getUserById(userId);
        if (user != null && user.getBalance() >= amount && amount > 0) {
            double newBalance = user.getBalance() - amount;
            userDAO.updateUserBalance(userId, newBalance);
            transactionDAO.addTransaction(userId, "Withdrawal", amount);
            resourcesController.updateResourcesAfterTransaction(amount, false);

            return true;
        }
        return false;
    }

    // Transfer funds between users
    public boolean transfer(int senderId, String receiverCardNumber, double amount) {
        User sender = userDAO.getUserById(senderId);
        User receiver = userDAO.getUserByCardNumber(receiverCardNumber);

        // Check if both users exist
        if (sender == null || receiver == null) {
            System.out.println("Invalid sender or recipient card number.");
            return false;
        }

        if (sender.getId() == receiver.getId()) {
            System.out.println("You cannot transfer money to yourself.");
            return false;
        }

        //Prevent transactions to technicians
        if ("technician".equalsIgnoreCase(receiver.getRole())) {
            System.out.println("Transfers to technicians are not allowed.");
            return false;
        }

        // Check if the sender has enough balance
        if (sender.getBalance() < amount || amount <= 0) {
            System.out.println("Insufficient funds or invalid amount.");
            return false;
        }

        // Update balances
        double newSenderBalance = sender.getBalance() - amount;
        double newReceiverBalance = receiver.getBalance() + amount;

        userDAO.updateUserBalance(senderId, newSenderBalance);
        userDAO.updateUserBalance(receiver.getId(), newReceiverBalance);

        // Add transaction records
        transactionDAO.addTransaction(senderId, "Transfer Sent", -amount);
        transactionDAO.addTransaction(receiver.getId(), "Transfer Received", amount);
        resourcesController.updateResourcesAfterTransaction(0, false);

        return true;
    }


    // Get the list of transactions for a specific user
    public List<Transaction> getUserTransactions(int userId) {
        return transactionDAO.getTransactionsByUserId(userId);
    }
}



