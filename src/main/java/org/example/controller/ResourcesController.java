package org.example.controller;

import org.example.model.Resources;
import org.example.model.ResourcesDAO;

public class ResourcesController {
    private final ResourcesDAO resourcesDAO = new ResourcesDAO(); // Data access object to interact with resources table

    // Get the current resources from the database
    public Resources getResources() {
        return resourcesDAO.getResources();  // Fetch the resource data
    }

    // Refill the resources to default values
    public boolean refillResources() {
        boolean success = resourcesDAO.refillResources();
        if (success) {
            System.out.println("Resources successfully refilled.");
        } else {
            System.out.println("Failed to refill resources.");
        }
        return success;  // You can also return this value if needed
    }

    // Deduct resources after a transaction (e.g., after a deposit/withdrawal)
    public boolean updateResourcesAfterTransaction() {
        Resources currentResources = getResources();

        // Check if the resources are sufficient for the transaction
        if (currentResources.isLowOnResources()) {
            return false;  // If resources are low, we don't allow the transaction
        }

        // Deduct 2 from each resource
        currentResources.setInk(currentResources.getInk() - 2);
        currentResources.setPaper(currentResources.getPaper() - 2);
        currentResources.setSoftwareUpdate(currentResources.getSoftwareUpdate() - 2);

        // Deduct from cash based on the transaction
        currentResources.setCash(currentResources.getCash() - 2);  // Assuming it's fixed for every transaction

        // Save the updated resources back to the database
        return resourcesDAO.updateResources(currentResources);
    }
}
