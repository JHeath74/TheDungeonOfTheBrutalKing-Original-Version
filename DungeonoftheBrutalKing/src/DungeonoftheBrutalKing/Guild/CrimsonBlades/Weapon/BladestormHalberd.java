package DungeonoftheBrutalKing.Guild.CrimsonBlades.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import java.util.Random;

public class BladestormHalberd extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 16;
    private static final int STRENGTH_BONUS = 3;
    private static final int AGILITY_BONUS = 1;
    private static final double DEFENSE_BONUS_PERCENT = 0.07;
    private static final int ATTACK_DAMAGE = 6;
    private static final int WEIGHT = 4;
    private static final Guild GUILD_NAME = Guild.CRIMSON_BLADES;
    private static final GuildType GUILD_TYPE = GuildType.WARRIOR;
    private static final String WEAPON_NAME = "Bladestorm Halberd";
    private static final String DESCRIPTION = "Bladestorm Halberd: A mighty halberd forged for the Crimson Blades, unleashing storm-like fury and granting great strength, defense, and agility.";

    // Store the last defense bonus applied for correct removal
    private int lastDefenseBonus = 0;

    public BladestormHalberd(String effect) {
        super(WEAPON_NAME, REQUIRED_STRENGTH, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static BladestormHalberd createBladestormHalberd(Character character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != Guild.CRIMSON_BLADES)
            throw new IllegalArgumentException("Only Crimson Blades members can wield the Bladestorm Halberd.");
        if (character.getStrength() >= REQUIRED_STRENGTH) {
            return new BladestormHalberd(effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Bladestorm Halberd.");
    }

    @Override
    public boolean equip(Character wielder) {
        if (wielder == null) return false;
        if (wielder.getGuild() != Guild.CRIMSON_BLADES) return false;
        if (wielder.getEquippedWeapon() == null || !wielder.getEquippedWeapon().equals(getName())) {
            wielder.setEquippedWeapon(getName());
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
    public boolean unequip(Character wielder) {
        if (wielder == null) return false;
        if (wielder.getEquippedWeapon() != null && wielder.getEquippedWeapon().equals(getName())) {
            wielder.setEquippedWeapon(null);
            wielder.setStrength(wielder.getStrength() - STRENGTH_BONUS);
            wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
            wielder.setDefense(wielder.getDefense() - lastDefenseBonus);
            lastDefenseBonus = 0;
            return true;
        }
        return false;
    }

    public void attackDamage(Character wielder, Enemies enemy) {
        if (wielder == null || enemy == null) return;
        int strength = wielder.getStrength();
        Random rand = new Random();
        int bonus = rand.nextInt((strength / 3) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;
        enemy.takeDamage(totalDamage, null);
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
