package org.example.model;

public class Resources {
    private int ink;
    private int paper;
    private double cash;
    private int softwareUpdate;

    public Resources() {
        // Set default values
        this.ink = 100;
        this.paper = 100;
        this.cash = 1000.0;
        this.softwareUpdate = 100;
    }

    // constructor to initialize all resources
    public Resources(int ink, int paper, double cash, int softwareUpdate) {
        this.ink = ink;
        this.paper = paper;
        this.cash = cash;
        this.softwareUpdate = softwareUpdate;
    }

    // Getters and Setters
    public int getInk() {
        return ink;
    }
    public void setInk(int ink) {
        this.ink = ink;
    }
    public int getPaper() {
        return paper;
    }
    public void setPaper(int paper) {
        this.paper = paper;
    }
    public double getCash() {
        return cash;
    }
    public void setCash(double cash) {
        this.cash = cash;
    }
    public int getSoftwareUpdate() {
        return softwareUpdate;
    }
    public void setSoftwareUpdate(int softwareUpdate) {
        this.softwareUpdate = softwareUpdate;
    }

    public boolean isLowOnResources() {
        return ink <= 20 || paper <= 20 || softwareUpdate <= 20 || cash <= 20;
    }
    public boolean isOutOfResources() {
        return ink == 0 || paper == 0 || softwareUpdate == 0 || cash == 0;
    }
}

