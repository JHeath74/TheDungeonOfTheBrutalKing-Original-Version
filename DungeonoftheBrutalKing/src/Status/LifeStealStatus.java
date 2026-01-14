
// src/Status/LifeStealStatus.java
package Status;

public class LifeStealStatus extends Status {
    private final int amount;

    public LifeStealStatus(int amount) {
        super("LifeSteal", amount);
        this.amount = amount;
    }

    public void apply(HasHitPoints caster, HasHitPoints target) {
        int stealAmount = Math.min(amount, target.getHitPoints());
        target.setHitPoints(target.getHitPoints() - stealAmount);

        int casterNewHP = Math.min(caster.getHitPoints() + stealAmount, caster.getMaxHitPoints());
        caster.setHitPoints(casterNewHP);
    }

    @Override
    public void applyEffect(HasHitPoints entity) {
        int stealAmount = Math.min(amount, entity.getHitPoints());
        entity.setHitPoints(entity.getHitPoints() - stealAmount);
    }
}
