package Status;

import DungeonoftheBrutalKing.Charecter;

public class LifeStealStatus extends Status {
    private final int amount;

    public LifeStealStatus(int amount) {
        super("LifeSteal", 1, false); // 1 turn, not negative
        this.amount = amount;
    }

    // Custom method for lifesteal logic between two entities
    public void apply(HasHitPoints caster, HasHitPoints target) {
        int stealAmount = Math.min(amount, target.getHitPoints());
        target.setHitPoints(target.getHitPoints() - stealAmount);

        int casterNewHP = Math.min(caster.getHitPoints() + stealAmount, caster.getMaxHitPoints());
        caster.setHitPoints(casterNewHP);
    }

    @Override
    public void applyEffect(Charecter entity) {
        // No-op or implement if you want to apply lifesteal to a single character
    }

    @Override
    public void expireEffect(Charecter entity) {
        // No-op
    }

    @Override
    public void removeEffect(Charecter entity) {
        // No-op
    }
}
