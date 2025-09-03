
package Maps;

public class DungeonLevel1 {

    // Dimensions of the dungeon
    static int mapWidth = 15;
    static int mapHeight = 15;
    static int DungeonLevel = 1;

    // 2D array representing the first floor of the dungeon
    public static final int[][] map = {
   		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
   		{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
   		{1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
   		{1, 0, 1, 1, 8, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1},
   		{1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1},
   		{1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
   		{1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1},
   		{1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
   		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
   		{1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
   		{1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1},
   		{1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
   		{1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1},
   		{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
   		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    // Constructor
    public DungeonLevel1() {}

    public int[][] getMap() {
        return map;
    }

    public static int getMapWidth() {
        return mapWidth;
    }

    public static int getMapHeight() {
        return mapHeight;
    }

    public int getDungeonLevelNumber() {
        return DungeonLevel;
    }




    
}
