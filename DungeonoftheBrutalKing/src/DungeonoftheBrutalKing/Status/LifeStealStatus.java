
// src/Status/LifeStealStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;

public final class LifeStealStatus extends Status {
    private static final int DEFAULT_AMOUNT = 2; // tune as needed
    private final int amount;

    // Store target so applyEffect(caster) can lifesteal from it
    private HasHitPoints storedTarget;

    public LifeStealStatus(int durationMinutes) {
        this(DEFAULT_AMOUNT, durationMinutes);
    }

    public LifeStealStatus(int amount, int durationMinutes) {
        super("LifeSteal", durationMinutes, StatusPolarity.POSITIVE, StatusType.LIFE_STEAL_STATUS);
        this.amount = Math.max(0, amount);
    }

    // Call this on-hit, then add/apply the status to the caster in your pipeline
    public LifeStealStatus withTarget(HasHitPoints target) {
        this.storedTarget = target;
        return this;
    }

    public void apply(HasHitPoints caster, HasHitPoints target) {
        if (caster == null || target == null) return;

        int stealAmount = Math.min(amount, Math.max(0, target.getHitPoints()));
        target.setHitPoints(target.getHitPoints() - stealAmount);

        int casterNewHP = Math.min(caster.getHitPoints() + stealAmount, caster.getMaxHitPoints());
        caster.setHitPoints(casterNewHP);
    }

    @Override
    public void applyEffect(Charecter caster) {
        if (!(caster instanceof HasHitPoints)) return;
        if (storedTarget == null) return;

        apply((HasHitPoints) caster, storedTarget);
    }

    @Override
    public void removeEffect(Charecter entity) {
        // No-op (instant/on-hit effect; nothing to undo)
    }

    @Override
    public String getDescription() {
        return "LifeSteal: on trigger, steals HP from the stored target and heals the caster.";
    }
}
