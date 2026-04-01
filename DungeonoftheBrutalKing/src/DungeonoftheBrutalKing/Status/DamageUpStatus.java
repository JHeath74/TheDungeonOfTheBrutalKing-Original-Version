
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;

public final class DamageUpStatus extends Status {

    private final int bonusDamage;

    public DamageUpStatus(int durationMinutes, int bonusDamage) {
        super("Damage Up", durationMinutes, StatusPolarity.POSITIVE, StatusType.DAMAGE_UP_STATUS);
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
}
