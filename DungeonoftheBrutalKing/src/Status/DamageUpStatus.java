
// `src/Status/DamageUpStatus.java`
// Ensure this class extends Status so it can be used where a Status is required.
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class DamageUpStatus extends Status {

    private final int bonusDamage;

    public DamageUpStatus(int duration, int bonusDamage) {
        super("Damage Up", duration, false, StatusType.DAMAGE_UP_STATUS);
        this.bonusDamage = Math.max(0, bonusDamage);
    }

    @Override
    public void applyEffect(Charecter target) {
        if (target == null) return;
        target.addDamageBonus(bonusDamage);
    }

    @Override
    public void removeEffect(Charecter target) {
        if (target == null) return;
        target.addDamageBonus(-bonusDamage);
    }

    @Override
    public boolean isNegative() {
        return false;
    }
}
