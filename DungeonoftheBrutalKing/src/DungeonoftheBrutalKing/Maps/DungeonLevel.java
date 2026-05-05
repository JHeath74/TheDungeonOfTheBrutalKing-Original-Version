// src/Maps/DungeonLevel.java
package DungeonoftheBrutalKing.Maps;

import java.awt.Point;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.Quests.Quest;
import DungeonoftheBrutalKing.Quests.Quests.QuestCleanseCursedShrine;
import DungeonoftheBrutalKing.Quests.Quests.QuestFeedHungryBeast;
import DungeonoftheBrutalKing.Quests.Quests.QuestForgiveBetrayer;
import DungeonoftheBrutalKing.Quests.Quests.QuestGuideTheLostSoul;
import DungeonoftheBrutalKing.Quests.Quests.QuestLieToTheLost;
import DungeonoftheBrutalKing.Quests.Quests.QuestRescuetheForgottenPrisoner;
import DungeonoftheBrutalKing.Quests.Quests.QuestSlayTheHelpLess;
import DungeonoftheBrutalKing.SharedData.LocationType;


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

    // Keep this for backwards compatibility but delegate to getSpecialLocation
    public LocationType getLocationType(int x, int y) {
        return getSpecialLocation(x, y);
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

    /**
     * Return the special location type at the given coordinates.
     * Subclasses no longer need to implement this unless they have custom behavior.
     */
    public LocationType getSpecialLocation(int x, int y) {
        return specialLocations.getOrDefault(new Point(x, y), LocationType.EMPTY);
    }

    /**
     * Helper for subclasses to register special locations.
     */
    protected void setSpecialLocation(int x, int y, LocationType type) {
        specialLocations.put(new Point(x, y), type);
    }

    // --- Quest-door linking methods ---

    public void assignRandomQuestsToDoors(List<Point> doorLocations, List<Quest> availableQuests) {
        if (doorLocations == null || availableQuests == null) return;
        Random rand = new Random();
        for (Point door : doorLocations) {
            if (door == null) continue;
            if (rand.nextBoolean() && !availableQuests.isEmpty()) {
                Quest quest = availableQuests.get(rand.nextInt(availableQuests.size()));
                doorQuests.put(door, quest);
            }
        }
    }

    public Quest getQuestForDoor(Point doorLocation) {
        if (doorLocation == null) return null;
        return doorQuests.get(doorLocation);
    }
    


    protected List<Quest> getAvailableQuests() throws IOException, InterruptedException, ParseException {
        List<Quest> availableQuests = new ArrayList<>();
        MainGameScreen screen = MainGameScreen.getInstance();
        availableQuests.add(new QuestCleanseCursedShrine(screen));
        availableQuests.add(new QuestFeedHungryBeast(screen));
        availableQuests.add(new QuestForgiveBetrayer(screen));
        availableQuests.add(new QuestGuideTheLostSoul(screen));
        availableQuests.add(new QuestLieToTheLost(screen));
        availableQuests.add(new QuestRescuetheForgottenPrisoner(screen));
        availableQuests.add(new QuestSlayTheHelpLess(screen));
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