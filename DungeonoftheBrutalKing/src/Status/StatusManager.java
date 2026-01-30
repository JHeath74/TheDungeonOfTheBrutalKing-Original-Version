package Status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import DungeonoftheBrutalKing.Charecter;

public class StatusManager {
    private List<Status> activeStatuses = new ArrayList<>();

    public void addStatus(Status status, Charecter charecter) {
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
