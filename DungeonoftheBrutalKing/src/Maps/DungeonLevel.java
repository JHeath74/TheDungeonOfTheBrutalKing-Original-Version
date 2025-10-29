
// src/Maps/DungeonLevel.java
package Maps;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import SharedData.LocationType;

public abstract class DungeonLevel {
    protected static final int mapWidth = 128;
    protected static final int mapHeight = 128;
    protected int[][] map;
    protected Map<Point, LocationType> specialLocations = new HashMap<>();
    protected int dungeonLevelNumber; // Add this field

    public int[][] getMap() { return map; }
    public static int getMapWidth() { return mapWidth; }
    public static int getMapHeight() { return mapHeight; }
    public abstract int getDungeonLevelNumber();

    public LocationType getLocationType(int x, int y) {
        return specialLocations.getOrDefault(new Point(x, y), LocationType.EMPTY);
    }

    public DungeonLevel goDown() { return null; }
    public DungeonLevel goUp() { return null; }

    public Point getStairsUpLocation() {
        for (Map.Entry<Point, LocationType> entry : specialLocations.entrySet()) {
            if (entry.getValue() == LocationType.STAIRS_UP) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Point getStairsDownLocation() {
        for (Map.Entry<Point, LocationType> entry : specialLocations.entrySet()) {
            if (entry.getValue() == LocationType.STAIRS_DOWN) {
                return entry.getKey();
            }
        }
        return null;
    }

    // Use the new field for these methods
    public int getCurrentDungeonLevel() {
        return this.dungeonLevelNumber;
    }

    public void setCurrentDungeonLevel(int level) {
        this.dungeonLevelNumber = level;
    }

    public abstract LocationType getSpecialLocation(int x, int y);
}
