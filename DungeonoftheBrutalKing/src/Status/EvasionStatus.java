
// src/Status/EvasionStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class EvasionStatus extends Status {

    private static final int DEFAULT_DURATION_TURNS = 3;
    private static final int DEFAULT_EVASION_BONUS = 3;

    private final int evasionBonus;

    public EvasionStatus() {
        this(DEFAULT_DURATION_TURNS, DEFAULT_EVASION_BONUS);
    }

    public EvasionStatus(int duration, int evasionBonus) {
        super("Evasion", duration, true, StatusType.EVASION_STATUS);
        this.evasionBonus = evasionBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;

        // Prefer a dedicated evasion stat if your character supports it.
        // If your project uses a different API name, replace these calls accordingly.
        try {
            character.setEvasion(character.getEvasion() + evasionBonus);
        } catch (Exception ignored) {
            // If there is no evasion stat on Charecter, leave as a no-op.
        }
    }

    @Override
    public void expireEffect(Charecter character) {
        removeEffect(character);
    }

    @Override
    public void removeEffect(Charecter character) {
        if (character == null) return;

        try {
            character.setEvasion(character.getEvasion() - evasionBonus);
        } catch (Exception ignored) {
            // No-op if evasion stat is not present.
        }
    }
}
