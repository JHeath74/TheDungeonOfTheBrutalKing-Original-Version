
// src/Maps/DungeonLevel.java
package Maps;
//0 is floor
//1 is wall



public abstract class DungeonLevel {
    protected static final int mapWidth = 128;
    protected static final int mapHeight = 128;
    protected int[][] map;

    public int[][] getMap() { return map; }
    public static int getMapWidth() { return mapWidth; }
    public static int getMapHeight() { return mapHeight; }
    public abstract int getDungeonLevelNumber();
}
