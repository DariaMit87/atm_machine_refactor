package org.example.controller;

import org.example.model.Transaction;
import org.example.model.TransactionDAO;
import org.example.model.User;
import org.example.model.UserDAO;
import org.example.model.Resources;
import org.example.controller.ResourcesController;
import java.util.List;

public class TransactionController {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final UserDAO userDAO = new UserDAO();
    private final ResourcesController resourcesController = new ResourcesController();

    // Deposit funds into user account and update transaction history
    public boolean deposit(int userId, double amount) {
        User user = userDAO.getUserById(userId);
        if (user != null && amount > 0) {
            double newBalance = user.getBalance() + amount;
            userDAO.updateUserBalance(userId, newBalance);
            transactionDAO.addTransaction(userId, "Deposit", amount);
            resourcesController.updateResourcesAfterTransaction();

            // Notify ResourcesController to update resources after deposit (separate handling)
            // ResourcesController.updateResourcesAfterTransaction();

            return true;
        }
        return false;
    }

    // Withdraw funds from user account and update transaction history
    public boolean withdraw(int userId, double amount) {
        User user = userDAO.getUserById(userId);
        if (user != null && user.getBalance() >= amount && amount > 0) {
            double newBalance = user.getBalance() - amount;
            userDAO.updateUserBalance(userId, newBalance);
            transactionDAO.addTransaction(userId, "Withdrawal", amount);
            resourcesController.updateResourcesAfterTransaction();

            // Notify ResourcesController to update resources after withdrawal (separate handling)
            // ResourcesController.updateResourcesAfterTransaction();

            return true;
        }
        return false;
    }

    // Transfer funds between users and update transaction history
    public boolean transfer(int senderId, int receiverId, double amount) {
        User sender = userDAO.getUserById(senderId);
        User receiver = userDAO.getUserById(receiverId);
        if (sender != null && receiver != null && sender.getBalance() >= amount && amount > 0) {
            userDAO.updateUserBalance(senderId, sender.getBalance() - amount);
            userDAO.updateUserBalance(receiverId, receiver.getBalance() + amount);
            transactionDAO.addTransaction(senderId, "Transfer to User " + receiverId, amount);
            transactionDAO.addTransaction(receiverId, "Transfer from User " + senderId, amount);
            resourcesController.updateResourcesAfterTransaction();

            // Notify ResourcesController to update resources after transfer (separate handling)
            // ResourcesController.updateResourcesAfterTransaction();

            return true;
        }
        return false;
    }

    // Get the list of transactions for a specific user
    public List<Transaction> getUserTransactions(int userId) {
        return transactionDAO.getTransactionsByUserId(userId);
    }
}



