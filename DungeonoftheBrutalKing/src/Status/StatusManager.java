
package Status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;

/**
 * Manages the status effects applied to a character.
 * Handles adding, updating, removing, and retrieving statuses.
 */
public class StatusManager {
    private List<Status> activeStatuses = new ArrayList<>(); // List of active status effects
    private List<String> myChar; // Reference to character data (e.g., attributes or statuses)

    /**
     * Adds a new status to the manager.
     *
     * @param status The status to be added.
     */
    public void addStatus(Status status) {
        activeStatuses.add(status);
    }

    /**
     * Updates all statuses based on elapsed time.
     * Reduces the duration of each status, applies its effect, and removes expired statuses.
     *
     * @param character   The character to apply the effects to.
     * @param timeElapsed The amount of time that has passed.
     */
    public void updateStatuses(Charecter character, int timeElapsed) {
        Iterator<Status> iterator = activeStatuses.iterator();
        while (iterator.hasNext()) {
            Status status = iterator.next();
            status.reduceDuration(timeElapsed); // Reduce the duration of the status
            status.applyEffect(character); // Apply the effect to the character
            if (status.isExpired()) { // Remove the status if it has expired
                iterator.remove();
            }
        }
    }

    /**
     * Retrieves a list of all active statuses.
     *
     * @return A list of active statuses.
     */
    public List<Status> getActiveStatuses() {
        return new ArrayList<>(activeStatuses); // Return a copy of the active statuses
    }

    /**
     * Removes a specific status by its name.
     *
     * @param statusName The name of the status to be removed.
     */
    public void removeStatusByName(String statusName) {
        activeStatuses.removeIf(status -> status.getName().equals(statusName)); // Remove matching statuses
    }

    /**
     * Checks if a specific status is currently active.
     *
     * @param statusName The name of the status to check.
     * @return True if the status is active, false otherwise.
     */
    public boolean hasStatus(String statusName) {
        return activeStatuses.stream().anyMatch(status -> status.getName().equals(statusName)); // Check for matching status
    }

    /**
     * Adds a status to the character's status list stored in myChar[28].
     *
     * @param status The name of the status to be added.
     */
    public void addStatusToMyChar(String status) {
        String currentStatuses = myChar.get(28); // Get current statuses
        if (!currentStatuses.contains(status)) { // Avoid duplicates
            if (!currentStatuses.isEmpty()) {
                currentStatuses += ","; // Add delimiter if not empty
            }
            currentStatuses += status; // Append new status
            myChar.set(28, currentStatuses); // Update myChar
        }
    }

    /**
     * Removes a status from the character's status list stored in myChar[28].
     *
     * @param status The name of the status to be removed.
     */
    public void removeStatusFromMyChar(String status) {
        String currentStatuses = myChar.get(28); // Get current statuses
        String[] statusesArray = currentStatuses.split(","); // Split into array
        StringBuilder updatedStatuses = new StringBuilder();
        for (String s : statusesArray) {
            if (!s.equals(status)) { // Exclude the status to be removed
                if (updatedStatuses.length() > 0) {
                    updatedStatuses.append(","); // Add delimiter
                }
                updatedStatuses.append(s);
            }
        }
        myChar.set(28, updatedStatuses.toString()); // Update myChar
    }

    /**
     * Retrieves all statuses from the character's status list stored in myChar[28].
     *
     * @return A list of all statuses.
     */
    public List<String> getStatusesFromMyChar() {
        String currentStatuses = myChar.get(28); // Get current statuses
        if (currentStatuses.isEmpty()) {
            return new ArrayList<>(); // Return empty list if no statuses
        }
        return new ArrayList<>(List.of(currentStatuses.split(","))); // Convert to list
    }
}
