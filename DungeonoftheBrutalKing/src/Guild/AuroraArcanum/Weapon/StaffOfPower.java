
// src/Guild/AuroraArcanum/Weapon/StaffOfPower.java
package Guild.AuroraArcanum.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class StaffOfPower extends WeaponManager {

    private static final int REQUIRED_INTELLIGENCE = 30;
    private static final int INTELLIGENCE_BONUS = 2;
    private static final int WISDOM_BONUS = 1;
    private static final double DEFENSE_BONUS = 0.10;
    private static final int ATTACK_DAMAGE = 5;
    private static final int WEIGHT = 10;

    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;
    private int lastDefenseBonus = 0;

    public StaffOfPower(int requiredIntelligence, int damage, String effect, int weight) {
        super("Staff of Power", requiredIntelligence, damage, effect, weight);
    }

    public static StaffOfPower createStaffOfPower(Charecter character, int damage, String effect) {
        int intelligence = character.getIntelligence();
        if (intelligence >= REQUIRED_INTELLIGENCE) {
            return new StaffOfPower(REQUIRED_INTELLIGENCE, damage, effect, WEIGHT);
        }
        throw new IllegalArgumentException("Character does not have the required intelligence to wield the Staff of Power.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (!isEquipped && wielder.getGuild() == GUILDname) {
            wielder.setIntelligence(wielder.getIntelligence() + INTELLIGENCE_BONUS);
            wielder.setWisdom(wielder.getWisdom() + WISDOM_BONUS);
            lastDefenseBonus = (int) Math.round(wielder.getDefense() * DEFENSE_BONUS);
            wielder.setDefense(wielder.getDefense() + lastDefenseBonus);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (isEquipped) {
            wielder.setIntelligence(wielder.getIntelligence() - INTELLIGENCE_BONUS);
            wielder.setWisdom(wielder.getWisdom() - WISDOM_BONUS);
            wielder.setDefense(wielder.getDefense() - lastDefenseBonus);
            isEquipped = false;
        }
		return isEquipped;
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        int intelligence = wielder.getIntelligence();
        Random rand = new Random();
        int bonus = rand.nextInt((intelligence / 2) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;
        enemy.takeDamage(totalDamage);
    }

    public Guild getGuild() {
        return GUILDname;
    }

    public GuildType getGuildType() {
        return GUILDtype;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return (double) WEIGHT;
    }

    @Override
    public double getDamage() {
        return (double) ATTACK_DAMAGE;
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    @Override
    public String getDescription() {
        return "Staff of Power: A rune-carved staff that channels spells, stores magical energy, and boosts wizardly prowess.";
    }
}
