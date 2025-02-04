package org.example.model;

import java.sql.Timestamp;

public class Transaction {
    private int id;
    private int userid;
    private String type;
    private double amount;
    private Timestamp timestamp;

    // Constructor for retrieving transactions
    public Transaction(int id, int userid, String type, double amount, Timestamp timestamp) {
        this.id = id;
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Constructor for inserting transactions
    public Transaction(int userid, String type, double amount, Timestamp timestamp) {
        this.userid = userid;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    // Getters & Setters
    public int getId() { return id; }
    public int getUserId() { return userid; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public Timestamp getTimestamp() { return timestamp; }
}
