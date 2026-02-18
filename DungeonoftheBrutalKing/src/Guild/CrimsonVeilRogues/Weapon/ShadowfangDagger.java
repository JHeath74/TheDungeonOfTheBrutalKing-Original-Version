
package Guild.CrimsonVeilRogues.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class ShadowfangDagger extends WeaponManager {

    private static final int REQUIRED_AGILITY = 12;
    private static final int AGILITY_BONUS = 5;
    private static final double CRIT_CHANCE_BONUS = 0.04;
    private static final int ATTACK_DAMAGE = 4;
    private static final int WEIGHT = 2;
    private static final Guild GUILD_NAME = Guild.CRIMSON_VEIL_ROGUES;
    private static final GuildType GUILD_TYPE = GuildType.ROGUE;
    private static final String WEAPON_NAME = "Shadowfang Dagger";
    private static final String DESCRIPTION = "Shadowfang Dagger: A razor-edged blade favored by the Crimson Veil Rogues, increasing agility and critical strike chance in the shadows.";

    private double lastCritBonus = 0;

    public ShadowfangDagger(String effect) {
        super(WEAPON_NAME, REQUIRED_AGILITY, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static ShadowfangDagger createShadowfangDagger(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != Guild.CRIMSON_VEIL_ROGUES)
            throw new IllegalArgumentException("Only Crimson Veil Rogues members can wield the Shadowfang Dagger.");
        if (character.getAgility() >= REQUIRED_AGILITY) {
            return new ShadowfangDagger(effect);
        }
        throw new IllegalArgumentException("Character does not have the required agility to wield the Shadowfang Dagger.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (wielder == null) return false;
        if (wielder.getGuild() != Guild.CRIMSON_VEIL_ROGUES) return false;
        if (wielder.getWeapon() == null || !wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(getName());
            wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
            lastCritBonus = CRIT_CHANCE_BONUS;
            wielder.setCritChance(wielder.getCritChance() + lastCritBonus);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (wielder == null) return false;
        if (wielder.getWeapon() != null && wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(null);
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            wielder.setCritChance(wielder.getCritChance() - lastCritBonus);
            lastCritBonus = 0;
            return true;
        }
        return false;
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        if (wielder == null || enemy == null) return;
        int agility = wielder.getAgility();
        Random rand = new Random();
        int bonus = rand.nextInt((agility / 4) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;
        if (rand.nextDouble() < (wielder.getCritChance())) {
            totalDamage *= 2;
        }
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
