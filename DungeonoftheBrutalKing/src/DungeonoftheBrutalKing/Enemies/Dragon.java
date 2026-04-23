
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.MainGameScreen;
import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.GameSettings;
import DungeonoftheBrutalKing.SharedData.RandomFactory;
import DungeonoftheBrutalKing.Status.FireStatus;

public class Dragon extends Enemies {

    private int level;
    private final Alignment alignment = Alignment.EVIL;
    private final int alignmentImpact = 5;

    public Dragon() {
        super(
            "Dragon",
            9,
            30,
            8,
            5,
            7,
            6,
            3,
            GameSettings.MonsterImagePath + "Dragon.png",
            false,
            8
        );
        this.level = 9;
    }

    @Override
    public void takeDamage(int damage, MainGameScreen mainGameScreen) {
        int dodgeChancePercent = 10;
        if (RandomFactory.gameplayDouble() * 100 < dodgeChancePercent) {
            mainGameScreen.appendToMessageTextPane(getName() + " dodged the attack!");
            return;
        }
        setHitPoints(getHitPoints() - defend(damage, mainGameScreen));
        if (isDead()) {
            mainGameScreen.appendToMessageTextPane(getName() + " has died.");
        }
    }

    public int attack(Character target, MainGameScreen mainGameScreen) {
        int damage = getAttackDamage();
        double baseBurnChance = 0.30;
        double defenseFactor = Math.max(0.0, 1.0 - (target.getDefense() / 100.0));
        double finalBurnChance = baseBurnChance * defenseFactor;

        mainGameScreen.appendToMessageTextPane(getName() + " attacks for " + damage + " damage.");
        target.takeDamage(damage);

        if (RandomFactory.gameplayDouble() < finalBurnChance) {
            mainGameScreen.appendToMessageTextPane(getName() + " breathes fire! The target is burned.");
            FireStatus fireStatus = new FireStatus();
            target.getStatusManager().addStatus(fireStatus, target);
        }

        return damage;
    }

    @Override
    public int attack(MainGameScreen mainGameScreen) {
        boolean critical = RandomFactory.gameplayDouble() < 0.15;
        int base = getAttackDamage();
        int damage = critical ? base * 2 : base;
        mainGameScreen.appendToMessageTextPane(getName() + " unleashes a mighty attack, dealing " + damage + " damage!" + (critical ? " Critical hit!" : ""));
        return damage;
    }

    @Override
    public int defend(int incomingDamage, MainGameScreen mainGameScreen) {
        int baseDefense = 10;
        int reductionPercent = (baseDefense + getAgility()) / 2;
        if (reductionPercent > 80) reductionPercent = 80;
        int reducedDamage = incomingDamage * (100 - reductionPercent) / 100;
        mainGameScreen.appendToMessageTextPane(getName() + " defends and reduces damage to " + reducedDamage + ".");
        return reducedDamage;
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
        if (getHitPoints() < 10) {
            return GameSettings.MonsterImagePath + "Dragon_injured.png";
        }
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

    public int getLevel() {
        return level;
    }

    @Override
    public int getExperienceReward() {
        int base = level * 10;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getGoldReward() {
        int base = level * 5;
        int offset = (int) ((RandomFactory.gameplayDouble() * (2 * level * 7 + 1)) - (level * 7));
        return Math.max(base + offset, 0);
    }

    @Override
    public int getAlignmentImpact() {
        return alignmentImpact;
    }

    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Override
    public String getClassName() {
        return getName();
    }
}
