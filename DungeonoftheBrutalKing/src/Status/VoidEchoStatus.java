
package Status;

import DungeonoftheBrutalKing.Charecter;

public class VoidEchoStatus extends Status {
    private final boolean extraDamage;

    public VoidEchoStatus(int durationSeconds, boolean extraDamage) {
        super("Void Echo", durationSeconds, true); // true: negative effect
        this.extraDamage = extraDamage;
    }

    @Override
    public void applyEffect(Charecter charecter) {
        // No periodic effect needed
    }

    @Override
    public boolean blocksSpellcasting() {
        return true;
    }

    @Override
    public double damageTakenMultiplier() {
        return extraDamage ? 1.2 : 1.0;
    }
}
