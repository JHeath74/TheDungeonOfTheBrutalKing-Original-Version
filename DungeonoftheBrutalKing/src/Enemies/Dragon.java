
package Enemies;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.MainGameScreen;
import Effect.EffectManager;
import SharedData.GameSettings;
import Status.FireStatus;

public class Dragon extends Enemies {

    public Dragon() {
        super(
            "Dragon",
            1,
            30,
            8,
            5,
            7,
            6,
            3,
            GameSettings.MonsterImagePath + "Dragon.png",
            false,
            0
        );
    }

    @Override
    public void takeDamage(int damage) {
        setHitPoints(getHitPoints() - damage);
        if (getHitPoints() < 0) setHitPoints(0);
        if (isDead()) MainGameScreen.appendToMessageTextPane(getName() + " has died.\n");
    }

    public int attack(Charecter target) {
        int damage = getAttackDamage();
        double baseBurnChance = 0.3;
        double defenseFactor = Math.max(0, 1.0 - (target.getDefense() / 100.0));
        double finalBurnChance = baseBurnChance * defenseFactor;
        MainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage.\n");
        target.takeDamage(damage);
        if (Math.random() < finalBurnChance) {
            MainGameScreen.appendToMessageTextPane(getName() + " breathes fire! The target is burned.\n");
            FireStatus fireStatus = new FireStatus();

((EffectManager) target.getEffectManager()).addStatus(fireStatus, target);

        }
        return damage;
    }

    @Override
    public boolean isDead() {
        return getHitPoints() <= 0;
    }

    @Override
    public int getAttackDamage() {
        return (int) ((getStrength() * 1.5) + (getAgility() * 0.5));
    }

    @Override
    public String getImagePath() {
        return super.getImagePath();
    }

    @Override
    public String toString() {
        return "Dragon{" +
                "name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", hitPoints=" + getHitPoints() +
                ", strength=" + getStrength() +
                ", charisma=" + getCharisma() +
                ", agility=" + getAgility() +
                ", intelligence=" + getIntelligence() +
                ", wisdom=" + getWisdom() +
                ", imagePath='" + getImagePath() + '\'' +
                '}';
    }

    @Override
    public int attack() {
        throw new UnsupportedOperationException("Use attack(Charecter target) instead.");
    }

    public int defend(int incomingDamage) {
        int baseDefense = 10;
        int agility = getAgility();
        int reductionPercent = (baseDefense + agility) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        MainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".\n");
        return reducedDamage;
    }
}
