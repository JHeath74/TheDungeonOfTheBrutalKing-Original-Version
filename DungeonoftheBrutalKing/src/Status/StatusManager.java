
// src/Status/StatusManager.java
package Status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;

/*Status: Represents persistent or current state (e.g., poisoned, stunned, healthy).
StatusManager: Manages the set of statuses for an entity (add, remove, query).*/

/**
 * Manages the status effects applied to a character.
 * Handles adding, updating, removing, and retrieving statuses.
 */
public class StatusManager {
    private List<Status> activeStatuses = new ArrayList<>();

    /**
     * Adds a new status to the manager.
     * Use the correct subclass, e.g., new PoisonStatus(duration).
     */
    public void addStatus(Status status) {
        activeStatuses.add(status);
    }

    /**
     * Updates all statuses: reduces duration, applies effect, removes expired.
     */
    public void updateStatuses(Charecter character, int timeElapsed) {
        Iterator<Status> iterator = activeStatuses.iterator();
        while (iterator.hasNext()) {
            Status status = iterator.next();
            status.reduceDuration(timeElapsed);
            status.applyEffect(character);
            if (status.isExpired()) {
                iterator.remove();
            }
        }
    }

    /**
     * Returns a copy of all active statuses.
     */
    public List<Status> getActiveStatuses() {
        return new ArrayList<>(activeStatuses);
    }

    /**
     * Removes a status by name.
     */
    public void removeStatusByName(String statusName) {
        activeStatuses.removeIf(status -> status.getName().equals(statusName));
    }

    /**
     * Checks if a status is active by name.
     */
    public boolean hasStatus(String statusName) {
        return activeStatuses.stream().anyMatch(status -> status.getName().equals(statusName));
    }
}
