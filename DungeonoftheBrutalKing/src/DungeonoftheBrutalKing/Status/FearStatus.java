
// src/Status/FearStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;

public final class FearStatus extends Status {
    private static final double DEFENSE_REDUCTION = 0.3; // 30% defense reduction

    private int originalDefense;

    public FearStatus(int duration) {
        super("Fear", Math.max(0, duration), StatusPolarity.NEGATIVE, StatusType.FEAR_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        originalDefense = character.getDefense();
        int reducedDefense = (int) Math.floor(originalDefense * (1.0 - DEFENSE_REDUCTION));
        character.setDefense(Math.max(0, reducedDefense));
    }

    @Override
    public void removeEffect(Charecter character) {
        if (character == null) return;
        character.setDefense(originalDefense);
    }

    public boolean canAttack() {
        return false;
    }

    public boolean canCastSpells() {
        return false;
    }

    public boolean canAct() {
        return false;
    }

    @Override
    public String getDescription() {
        int pct = (int) Math.round(DEFENSE_REDUCTION * 100.0);
        return "Fear: reduces defense by " + pct + "\\% and prevents acting while active\\.";
    }
}
