
package Maps;

import java.util.HashMap;
import java.util.Map;

public class DungeonManager {

    // Map to store dungeon levels by floor number
    private final Map<Integer, DungeonLevel> dungeonLevels = new HashMap<>();

    // Constructor to initialize all dungeon levels
    public DungeonManager() {
        dungeonLevels.put(1, new DungeonLevel1());
        dungeonLevels.put(2, new DungeonLevel2());
        dungeonLevels.put(3, new DungeonLevel3());
        dungeonLevels.put(4, new DungeonLevel4());
    }

    // Method to get a dungeon level by floor number
    public DungeonLevel getDungeonLevel(int floorNumber) {
        if (!dungeonLevels.containsKey(floorNumber)) {
            throw new IllegalArgumentException("Invalid floor number: " + floorNumber);
        }
        return dungeonLevels.get(floorNumber);
    }
}
