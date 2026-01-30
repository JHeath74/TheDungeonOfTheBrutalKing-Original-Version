
// src/Status/DazeStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class DazeStatus extends Status {
    private final int penalty;
    private int originalDefense;
    private int originalAccuracy;

    public DazeStatus(int penalty, int duration) {
        super("Dazed", duration, true);
        this.penalty = penalty;
    }

    @Override
    public void applyEffect(Charecter character) {
        originalDefense = character.getDefense();
        originalAccuracy = character.getAccuracy();
        character.setDefense(Math.max(0, originalDefense - penalty));
        character.setAccuracy(Math.max(0, originalAccuracy - penalty));
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setDefense(originalDefense);
        character.setAccuracy(originalAccuracy);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setDefense(originalDefense);
        character.setAccuracy(originalAccuracy);
    }
}
