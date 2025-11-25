
// src/Maps/DungeonLevel.java
package Maps;

import java.awt.Point;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import DungeonoftheBrutalKing.MainGameScreen;
import Quests.Quest;
import Quests.Quests.QuestCleanseCursedShrine;
import Quests.Quests.QuestForgiveBetrayer;
import Quests.Quests.QuestRescuetheForgottenPrisoner;
import SharedData.LocationType;


public abstract class DungeonLevel {
    protected static final int mapWidth = 128;
    protected static final int mapHeight = 128;
    protected int[][] map;
    protected Map<Point, LocationType> specialLocations = new HashMap<>();
    protected int dungeonLevelNumber;

    // Quest-door mapping
    protected Map<Point, Quest> doorQuests = new HashMap<>();

    public abstract List<? extends MapEntity> getEntities();

    public int[][] getMap() { return map; }
    public static int getMapWidth() { return mapWidth; }
    public static int getMapHeight() { return mapHeight; }
    public abstract int getDungeonLevelNumber();

    public LocationType getLocationType(int x, int y) {
        return specialLocations.getOrDefault(new Point(x, y), LocationType.EMPTY);
    }

    public DungeonLevel goDown() throws IOException, InterruptedException, ParseException { return null; }
    public DungeonLevel goUp() throws IOException, InterruptedException, ParseException { return null; }

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

    public int getCurrentDungeonLevel() {
        return this.dungeonLevelNumber;
    }

    public void setCurrentDungeonLevel(int level) {
        this.dungeonLevelNumber = level;
    }

    public abstract LocationType getSpecialLocation(int x, int y);

    // --- Quest-door linking methods ---

    public void assignRandomQuestsToDoors(List<Point> doorLocations, List<Quest> availableQuests) {
        Random rand = new Random();
        for (Point door : doorLocations) {
            if (rand.nextBoolean() && !availableQuests.isEmpty()) {
                Quest quest = availableQuests.get(rand.nextInt(availableQuests.size()));
                doorQuests.put(door, quest);
            }
        }
    }

    public Quest getQuestForDoor(Point doorLocation) {
        return doorQuests.get(doorLocation);
    }
    


protected List<Quest> getAvailableQuests() throws IOException, InterruptedException, ParseException {
    List<Quest> availableQuests = new ArrayList<>();
    availableQuests.add(new QuestForgiveBetrayer(MainGameScreen.getInstance()));
    availableQuests.add(new QuestCleanseCursedShrine(MainGameScreen.getInstance()));
    availableQuests.add(new QuestRescuetheForgottenPrisoner(MainGameScreen.getInstance()));
    return availableQuests;
}

    
    public List<Point> getDoorLocations() {
        List<Point> doors = new ArrayList<>();
        for (var entry : specialLocations.entrySet()) {
            if (entry.getValue() == LocationType.DOOR) {
                doors.add(entry.getKey());
            }
        }
        return doors;
    }

}
