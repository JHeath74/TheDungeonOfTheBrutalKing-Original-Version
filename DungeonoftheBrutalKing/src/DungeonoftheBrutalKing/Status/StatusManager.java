package DungeonoftheBrutalKing.Status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;

public class StatusManager {
    private List<Status> activeStatuses = new ArrayList<>();

    public void addStatus(Status status, Charecter charecter) {
        if (status == null) return;

        // If we're adding a negative status, check for a Purity Ward on the target.
        try {
            if (status.isNegative()) {
                boolean hasWard = false;
                // Check activeStatuses in this manager
                for (Status s : activeStatuses) {
                    if (s instanceof PurityWardStatus) { hasWard = true; break; }
                }
                // Check the charecter's local statuses as a backup
                if (!hasWard && charecter != null) {
                    try {
                        java.util.List<Status> local = charecter.getStatuses();
                        if (local != null) {
                            for (Status s : local) {
                                if (s instanceof PurityWardStatus) { hasWard = true; break; }
                            }
                        }
                    } catch (Exception ignored) { }
                }

                if (hasWard) {
                    try {
                        String targetName = (charecter != null) ? charecter.getName() : "Target";
                        System.out.println(targetName + " is protected by a Purity Ward; negative status '" + status.getName() + "' resisted.");
                    } catch (Exception ignored) { }
                    return;
                }
            }
        } catch (Exception ignored) { }

        status.applyEffect(charecter);
        activeStatuses.add(status);
    }

    public void updateStatuses(Charecter charecter, int timeElapsed) {
        Iterator<Status> iterator = activeStatuses.iterator();
        while (iterator.hasNext()) {
            Status status = iterator.next();
            status.reduceDuration(timeElapsed);
            if (status.isExpired()) {
                status.expireEffect(charecter);
                status.removeEffect(charecter);
                iterator.remove();
            }
        }
    }

    public List<Status> getActiveStatuses() {
        return new ArrayList<>(activeStatuses);
    }

    public void removeStatusByName(String statusName, Charecter charecter) {
        Iterator<Status> iterator = activeStatuses.iterator();
        while (iterator.hasNext()) {
            Status status = iterator.next();
            if (status.getName().equals(statusName)) {
                status.removeEffect(charecter);
                iterator.remove();
            }
        }
    }

    public boolean hasStatus(String statusName) {
        return activeStatuses.stream().anyMatch(status -> status.getName().equals(statusName));
    }
}