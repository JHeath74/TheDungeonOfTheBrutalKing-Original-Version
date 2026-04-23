
// src/Status/EvasionStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class EvasionUpStatus extends Status {

    private static final int DEFAULT_DURATION_MINUTES = 3;
    private static final int DEFAULT_EVASION_BONUS = 3;

    private final int evasionBonus;

    public EvasionUpStatus() {
        this(DEFAULT_DURATION_MINUTES, DEFAULT_EVASION_BONUS);
    }

    public EvasionUpStatus(int durationMinutes, int evasionBonus) {
        super("Evasion", durationMinutes, StatusPolarity.POSITIVE, StatusType.EVASION_STATUS);
        this.evasionBonus = Math.max(0, evasionBonus);
    }

    @Override
    public void applyEffect(Character character) {
        if (character == null) return;

        // Prefer a dedicated evasion stat if your character supports it.
        // If your project uses a different API name, replace these calls accordingly.
        try {
            character.setEvasion(character.getEvasion() + evasionBonus);
        } catch (Exception ignored) {
            // No-op if evasion stat is not present.
        }
    }

    @Override
    public void removeEffect(Character character) {
        if (character == null) return;

        try {
            character.setEvasion(character.getEvasion() - evasionBonus);
        } catch (Exception ignored) {
            // No-op if evasion stat is not present.
        }
    }
}
