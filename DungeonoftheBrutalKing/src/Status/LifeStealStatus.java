
// src/Statuses/LifeStealStatus.java
package Status;

import Enemies.Enemies;

public class LifeStealStatus {
    private final int amount;

    public LifeStealStatus(int amount) {
        this.amount = amount;
    }

    // Applies life steal: takes hit points from target, gives to caster (up to caster's max HP)
    public void apply(Enemies caster, Enemies target) {
        int stealAmount = Math.min(amount, target.getHitPoints());
        target.setHitPoints(target.getHitPoints() - stealAmount);

        int casterNewHP = Math.min(caster.getHitPoints() + stealAmount, caster.getMaxHitPoints());
        caster.setHitPoints(casterNewHP);
    }
}
