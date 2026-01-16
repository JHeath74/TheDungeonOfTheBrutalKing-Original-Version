
package Maps;

import java.awt.Point;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import SharedData.LocationType;

public class DungeonLevel5 extends DungeonLevel {

    private static final int MAP_WIDTH = 128;
    private static final int MAP_HEIGHT = 128;
    private static final int DUNGEON_LEVEL = 5;

    public DungeonLevel5() throws IOException, InterruptedException, ParseException {
        this.map = new int[][] {
            // TODO: Replace with your own level 5 map layout
            // For now, copy the structure from DungeonLevel4 or create a new one
            // Example: all walls with a path in the center
            // You can copy the map from DungeonLevel4 and modify as needed
            // {1,1,1,...}, ...
        };

        for (int[] row : this.map) {
            if (row.length != MAP_WIDTH) {
                throw new IllegalStateException("All rows must have " + MAP_WIDTH + " columns.");
            }
        }

        // Example special locations for level 5
        specialLocations.put(new Point(60, 110), LocationType.STAIRS_UP);
        specialLocations.put(new Point(10, 10), LocationType.STAIRS_DOWN);

        // Assign quests to doors
        assignRandomQuestsToDoors(getDoorLocations(), getAvailableQuests());
    }

    @Override
    public DungeonLevel goDown() {
        return null; // Implement DungeonLevel6 if needed
    }

    @Override
    public DungeonLevel goUp() throws IOException, InterruptedException, ParseException {
        return new DungeonLevel4();
    }

    public int[][] getMap() {
        return map;
    }

    public static int getMapWidth() {
        return MAP_WIDTH;
    }

    public static int getMapHeight() {
        return MAP_HEIGHT;
    }

    public int getDungeonLevelNumber() {
        return DUNGEON_LEVEL;
    }

    @Override
    public LocationType getSpecialLocation(int x, int y) {
        return specialLocations.getOrDefault(new Point(x, y), LocationType.EMPTY);
    }

    @Override
    public List<? extends MapEntity> getEntities() {
        // TODO: Add entities for level 5
        return null;
    }

    public void printNumberCoordinates() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                int value = map[y][x];
                if (value > 1) {
                    System.out.println("Level: " + DUNGEON_LEVEL + " Number: " + value + " at (" + x + ", " + y + ")");
                }
            }
        }
    }
}
