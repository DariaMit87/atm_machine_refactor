package org.example.controller;

import org.example.model.Resources;
import org.example.model.ResourcesDAO;

public class ResourcesController {
    private final ResourcesDAO resourcesDAO;

    public ResourcesController(ResourcesDAO resourcesDAO) {
        this.resourcesDAO = resourcesDAO;
    }

    public Resources getResources() {
        return resourcesDAO.getResources();
    }

    public boolean refillResources() {
        boolean success = resourcesDAO.refillResources();
        if (success) {
            System.out.println("Resources successfully refilled.");
        } else {
            System.out.println("Failed to refill resources.");
        }
        return success;
    }

    public void updateResourcesAfterTransaction(double amount, boolean isDeposit) {
        Resources currentResources = getResources();

        if (currentResources.isLowOnResources()) {
            return;  // If resources are low, we don't allow the transaction
        }

        currentResources.setInk(currentResources.getInk() - 1);
        currentResources.setPaper(currentResources.getPaper() - 2);
        currentResources.setSoftwareUpdate(currentResources.getSoftwareUpdate() - 3);

        // Deduct from cash based on the transaction
        if (isDeposit) {
            currentResources.setCash(currentResources.getCash() + amount);
        } else {
            currentResources.setCash(currentResources.getCash() - amount);
        }

        resourcesDAO.updateResources(currentResources);
    }
}
