
package Guild.CrimsonBlades.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class SanguineWaraxe extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 16;
    private static final int STRENGTH_BONUS = 3;
    private static final int AGILITY_BONUS = 1;
    private static final double DEFENSE_BONUS_PERCENT = 0.06;
    private static final int ATTACK_DAMAGE = 6;
    private static final int WEIGHT = 4;
    private static final Guild GUILD_NAME = Guild.CRIMSON_BLADES;
    private static final GuildType GUILD_TYPE = GuildType.WARRIOR;
    private static final String WEAPON_NAME = "Sanguine War Axe";
    private static final String DESCRIPTION = "Sanguine War Axe: A fierce axe favored by the Crimson Blades, granting its wielder increased strength and agility for devastating attacks.";

    // Track the last defense bonus applied for correct removal
    private int lastDefenseBonus = 0;

    public SanguineWaraxe(String effect) {
        super(WEAPON_NAME, REQUIRED_STRENGTH, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static SanguineWaraxe createSanguineWaraxe(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != Guild.CRIMSON_BLADES)
            throw new IllegalArgumentException("Only Crimson Blades members can wield the Sanguine War Axe.");
        int strength = character.getStrength();
        if (strength >= REQUIRED_STRENGTH) {
            return new SanguineWaraxe(effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Sanguine War Axe.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (wielder == null) return false;
        if (wielder.getGuild() != Guild.CRIMSON_BLADES) return false;
        if (wielder.getWeapon() == null || !wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(getName());
            wielder.setStrength(wielder.getStrength() + STRENGTH_BONUS);
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            int defenseIncrease = (int) Math.round(wielder.getDefense() * DEFENSE_BONUS_PERCENT);
            wielder.setDefense(wielder.getDefense() + defenseIncrease);
            lastDefenseBonus = defenseIncrease;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (wielder == null) return false;
        if (wielder.getWeapon() != null && wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(null);
            wielder.setStrength(wielder.getStrength() - STRENGTH_BONUS);
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() - lastDefenseBonus);
            lastDefenseBonus = 0;
            return true;
        }
        return false;
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        if (wielder == null || enemy == null) return;
        int strength = wielder.getStrength();
        Random rand = new Random();
        int bonus = rand.nextInt((strength / 4) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;
        enemy.takeDamage(totalDamage);
    }

    public Guild getGuild() {
        return GUILD_NAME;
    }

    public GuildType getGuildType() {
        return GUILD_TYPE;
    }

    @Override
    public String getName() {
        return WEAPON_NAME;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    @Override
    public double getDamage() {
        return ATTACK_DAMAGE;
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
