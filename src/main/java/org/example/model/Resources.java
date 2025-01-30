package org.example.model;

public class Resources {
    private int ink;
    private int paper;
    private int cash;
    private int softwareUpdate;

    public Resources() {
        // Set default values
        this.ink = 100;
        this.paper = 100;
        this.cash = 1000;
        this.softwareUpdate = 100;
    }

    // Add a constructor to initialize all resources
    public Resources(int ink, int paper, int cash, int softwareUpdate) {
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

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getSoftwareUpdate() {
        return softwareUpdate;
    }

    public void setSoftwareUpdate(int softwareUpdate) {
        this.softwareUpdate = softwareUpdate;
    }

    // Method to check if any resource is below 20
    public boolean isLowOnResources() {
        return ink <= 20 || paper <= 20 || softwareUpdate <= 20 || cash <= 20;
    }

    // Method to check if any resource has reached 0
    public boolean isOutOfResources() {
        return ink == 0 || paper == 0 || softwareUpdate == 0 || cash == 0;
    }

    // Deduct resources after transaction
    public void deductResources() {
        this.ink -= 2;
        this.paper -= 2;
        this.softwareUpdate -= 2;
    }

    // Deduct cash after a withdrawal and add cash after a deposit
    public void adjustCash(int amount) {
        this.cash -= amount;
    }

    public void depositCash(int amount) {
        this.cash += amount;
    }


    private final ResourcesDAO resourcesDAO = new ResourcesDAO();

    // Get current resource levels from the database
    public void loadResources() {
        Resources resourcesFromDB = resourcesDAO.getResources();
        if (resourcesFromDB != null) {
            this.ink = resourcesFromDB.getInk();
            this.paper = resourcesFromDB.getPaper();
            this.cash = resourcesFromDB.getCash();
            this.softwareUpdate = resourcesFromDB.getSoftwareUpdate();
        }
    }

    // Save current resource levels to the database
    public void saveResources() {
        resourcesDAO.updateResources(this);
    }

    // Refill resources back to default values
    public void refillResources() {
        this.ink = 100;
        this.paper = 100;
        this.cash = 1000;
        this.softwareUpdate = 100;
        resourcesDAO.refillResources();  // Save changes to the database
    }
}

