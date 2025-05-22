
package Maps;

public class DungeonLevel1 implements DungeonLevel {

    // Dimensions of the dungeon
    static int mapWidth = 15;
    static int mapHeight = 15;
    static int DungeonLevel = 1;

    // 2D array representing the first floor of the dungeon
    public static final int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 0, 0, 0, 3, 0, 2, 2, 2, 0, 2, 2, 2},
        {1, 0, 3, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 3, 3, 0, 3, 3, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 2},
        {1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 0, 4, 4, 4},
        {1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4},
        {1, 0, 0, 0, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 4},
        {1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4},
        {1, 0, 0, 0, 0, 0, 1, 4, 0, 3, 3, 3, 3, 0, 4},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4},
        {1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4}
    };

    // Constructor
    public DungeonLevel1() {}

    @Override
    public int[][] getMap() {
        return map;
    }

    @Override
    public int getMapWidth() {
        return mapWidth;
    }

    @Override
    public int getMapHeight() {
        return mapHeight;
    }

    @Override
    public int getDungeonLevelNumber() {
        return DungeonLevel;
    }



}
