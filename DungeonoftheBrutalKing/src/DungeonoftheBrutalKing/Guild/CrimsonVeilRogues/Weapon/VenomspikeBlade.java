
package Guild.CrimsonVeilRogues.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;
import java.util.Random;

public class VenomspikeBlade extends WeaponManager {

    private static final int REQUIRED_AGILITY = 16;
    private static final int AGILITY_BONUS = 4;
    private static final double CRIT_CHANCE_BONUS = 0.08;
    private static final int ATTACK_DAMAGE = 5;
    private static final int WEIGHT = 2;
    private static final Guild GUILD_NAME = Guild.CRIMSON_VEIL_ROGUES;
    private static final GuildType GUILD_TYPE = GuildType.ROGUE;
    private static final String WEAPON_NAME = "Venomspike Blade";
    private static final String DESCRIPTION = "Venomspike Blade: A slender dagger laced with potent toxins, favored by the Crimson Veil Rogues for its agility boost and venomous critical strikes.";

    private double lastCritBonus = 0;

    public VenomspikeBlade(String effect) {
        super(WEAPON_NAME, REQUIRED_AGILITY, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static VenomspikeBlade createVenomspikeBlade(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != Guild.CRIMSON_VEIL_ROGUES)
            throw new IllegalArgumentException("Only Crimson Veil Rogues members can wield the Venomspike Blade.");
        if (character.getAgility() >= REQUIRED_AGILITY) {
            return new VenomspikeBlade(effect);
        }
        throw new IllegalArgumentException("Character does not have the required agility to wield the Venomspike Blade.");
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
        // Critical hit chance
        if (rand.nextDouble() < (wielder.getCritChance())) {
            totalDamage *= 2;
            // Optionally, apply a venom effect here
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
