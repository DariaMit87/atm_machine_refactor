package org.example.controller;

import org.example.model.Resources;
import org.example.model.ResourcesDAO;

public class ResourcesController {
    private final ResourcesDAO resourcesDAO = new ResourcesDAO(); // Data access object to interact with resources table

    public Resources getResources() {
        return resourcesDAO.getResources();  // Fetch the resource data
    }


    public boolean refillResources() {
        boolean success = resourcesDAO.refillResources();
        if (success) {
            System.out.println("Resources successfully refilled.");
        } else {
            System.out.println("Failed to refill resources.");
        }
        return success;  // You can also return this value if needed
    }

    public void updateResourcesAfterTransaction(double amount, boolean isDeposit) {
        Resources currentResources = getResources();

        // Check if the resources are sufficient for the transaction
        if (currentResources.isLowOnResources()) {
            return;  // If resources are low, we don't allow the transaction
        }

        // Deduct 2 from each resource
        currentResources.setInk(currentResources.getInk() - 1);
        currentResources.setPaper(currentResources.getPaper() - 2);
        currentResources.setSoftwareUpdate(currentResources.getSoftwareUpdate() - 3);

        // Deduct from cash based on the transaction
        if (isDeposit) {
            currentResources.setCash(currentResources.getCash() + amount); // Increase cash for deposit
        } else {
            currentResources.setCash(currentResources.getCash() - amount); // Decrease cash for withdrawal
        }

        // Save the updated resources back to the database
        resourcesDAO.updateResources(currentResources);
    }
}
