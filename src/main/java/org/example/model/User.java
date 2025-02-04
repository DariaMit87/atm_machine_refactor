package org.example.model;

public class User {
    private int id;
    private String name;
    private String cardNumber;
    private String pin;
    private double balance;
    private String role;

    // Constructor for retrieving users
    public User(int id, String name, String cardNumber, String pin, double balance, String role) {
        this.id = id;
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.role = role;
    }

    // Constructor for inserting new users
    public User(String name, String cardNumber, String pin, double balance, String role) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
        this.role = role;
    }

    // Getters & Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getCardNumber() { return cardNumber; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public String getRole() { return role; }

}


