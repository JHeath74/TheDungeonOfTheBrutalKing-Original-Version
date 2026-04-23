
// src/Status/DazeStatus.java
package DungeonoftheBrutalKing.Status;

import java.lang.reflect.Method;

import DungeonoftheBrutalKing.Character;

public class DazeStatus extends Status {
    private static final int DEFAULT_PENALTY = 0;

    private final int penalty;

    private int originalDefense;
    private Integer originalAccuracy; // null when accuracy isn't supported on Charecter

    // Added: matches calls like new DazeStatus(DURATION_TURNS)
    public DazeStatus(int duration) {
        this(DEFAULT_PENALTY, duration);
    }

    public DazeStatus(int penalty, int duration) {
        super("Dazed", duration, StatusPolarity.NEGATIVE, StatusType.DAZE_STATUS);
        this.penalty = Math.max(0, penalty);
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;

        originalDefense = character.getDefense();
        character.setDefense(Math.max(0, originalDefense - penalty));

        originalAccuracy = tryGetAccuracy(character);
        if (originalAccuracy != null) {
            trySetAccuracy(character, Math.max(0, originalAccuracy - penalty));
        }
    }

    @Override
    public void removeEffect(Character character) {
        restore(character);
    }

    private void restore(Character character) {
        if (character == null) return;

        character.setDefense(originalDefense);

        if (originalAccuracy != null) {
            trySetAccuracy(character, originalAccuracy);
        }
    }

    private static Integer tryGetAccuracy(Character character) {
        try {
            Method m = character.getClass().getMethod("getAccuracy");
            Object v = m.invoke(character);
            if (v instanceof Integer) return (Integer) v;
            if (v instanceof Number) return ((Number) v).intValue();
            return null;
        } catch (ReflectiveOperationException ex) {
            return null;
        }
    }

    private static void trySetAccuracy(Character character, int value) {
        try {
            Method m = character.getClass().getMethod("setAccuracy", int.class);
            m.invoke(character, value);
        } catch (ReflectiveOperationException ex) {
            // No-op when accuracy isn't supported.
        }
    }

    public static int calculateAccuracyFromStats(int agility, int wisdom, int intelligence) {
        int agi = Math.max(0, agility);
        int wis = Math.max(0, wisdom);
        int intel = Math.max(0, intelligence);

        final int BASE_ACCURACY = 5;
        int mental = Math.max(wis, intel);

        return Math.max(0, BASE_ACCURACY + (agi * 2) + mental);
    }
}
