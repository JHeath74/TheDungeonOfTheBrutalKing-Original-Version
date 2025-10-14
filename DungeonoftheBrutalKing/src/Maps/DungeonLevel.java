
// src/Maps/DungeonLevel.java
package Maps;

import GameEngine.LocationType;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public abstract class DungeonLevel {
    protected static final int mapWidth = 128;
    protected static final int mapHeight = 128;
    protected int[][] map;
    protected Map<Point, LocationType> specialLocations = new HashMap<>();

    public int[][] getMap() { return map; }
    public static int getMapWidth() { return mapWidth; }
    public static int getMapHeight() { return mapHeight; }
    public abstract int getDungeonLevelNumber();

    public LocationType getLocationType(int x, int y) {
        return specialLocations.getOrDefault(new Point(x, y), LocationType.EMPTY);
    }

    public DungeonLevel goDown() {
        return null;
    }

    public DungeonLevel goUp() {
        return null;
    }
	public int[] getDownStairsLocation() {
		// TODO Auto-generated method stub
		return null;
	}
}
