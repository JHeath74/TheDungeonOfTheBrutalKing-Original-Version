
// src/Status/VoidEchoStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public final class VoidEchoStatus extends Status {
    private final boolean extraDamage;

    public VoidEchoStatus(int durationMinutes, boolean extraDamage) {
        super("Void Echo", durationMinutes, StatusPolarity.POSITIVE, StatusType.VOID_ECHO_STATUS);
        this.extraDamage = extraDamage;
    }

    @Override
    public void applyEffect(Character character) {
        // No periodic effect needed
    }

    @Override
    public void removeEffect(Character character) {
        // No additional effect on remove
    }

    @Override
    public boolean blocksSpellcasting() {
        return true;
    }

    @Override
    public double damageTakenMultiplier() {
        return extraDamage ? 1.2 : 1.0;
    }

    @Override
    public String getDescription() {
        return "Void Echo: blocks spellcasting while active" +
                (extraDamage ? " and increases damage taken by 20\\%\\." : "\\.");
    }
}
