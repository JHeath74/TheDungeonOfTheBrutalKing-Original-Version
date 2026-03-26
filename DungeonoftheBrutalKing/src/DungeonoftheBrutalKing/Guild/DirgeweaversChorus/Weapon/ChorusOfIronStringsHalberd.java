
// src/Guild/DirgeweaversChorus/Weapon/ChorusOfIronStringsHalberd.java
package Guild.DirgeweaversChorus.Weapon;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;
import SharedData.Guild;
import SharedData.GuildType;
import Weapon.WeaponManager;

import java.util.Random;

public class ChorusOfIronStringsHalberd extends WeaponManager {

    private static final int REQUIRED_CHARISMA = 15;
    private static final int CHARISMA_BONUS = 3;
    private static final int ATTACK_DAMAGE = 7;
    private static final int WEIGHT = 6;

    private static final int STATUS_CHANCE_PERCENT = 25;

    private static final Guild GUILD_NAME = Guild.DIRGEWEAVERS_CHORUS;
    private static final GuildType GUILD_TYPE = GuildType.BARD;

    private static final String WEAPON_NAME = "ChorusOfIronStringsHalberd";
    private static final String DESCRIPTION =
            "ChorusOfIronStringsHalberd: A halberd strung with iron cords, reserved for the Dirgeweavers Chorus. Grants charisma and may afflict foes with a resonant status on hit.";

    private int lastCharismaBonus = 0;

    public ChorusOfIronStringsHalberd(String effect) {
        super(WEAPON_NAME, REQUIRED_CHARISMA, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static ChorusOfIronStringsHalberd createChorusOfIronStringsHalberd(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != GUILD_NAME)
            throw new IllegalArgumentException("Only Dirgeweavers Chorus members can wield the ChorusOfIronStringsHalberd.");
        if (character.getCharisma() >= REQUIRED_CHARISMA) {
            return new ChorusOfIronStringsHalberd(effect);
        }
        throw new IllegalArgumentException("Character does not have the required charisma to wield the ChorusOfIronStringsHalberd.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (wielder == null) return false;
        if (wielder.getGuild() != GUILD_NAME) return false;

        if (wielder.getWeapon() == null || !wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(getName());
            lastCharismaBonus = CHARISMA_BONUS;
            wielder.setCharisma(wielder.getCharisma() + lastCharismaBonus);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (wielder == null) return false;

        if (wielder.getWeapon() != null && wielder.getWeapon().equals(getName())) {
            wielder.setWeapon(null);
            wielder.setCharisma(wielder.getCharisma() - lastCharismaBonus);
            lastCharismaBonus = 0;
            return true;
        }
        return false;
    }

    public void attackDamage(Charecter wielder, Enemies enemy) {
        if (wielder == null || enemy == null) return;

        Random rand = new Random();

        int charisma = wielder.getCharisma();
        int bonus = rand.nextInt((charisma / 4) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;

        enemy.takeDamage(totalDamage);

        // Status application (StatusEffect is not a String, so no isBlank())
        WeaponManager.StatusEffect statusEffect = getStatusEffect();
        if (statusEffect != null && statusEffect != WeaponManager.StatusEffect.NONE) {
            int roll = rand.nextInt(100) + 1;
            if (roll <= STATUS_CHANCE_PERCENT) {
                // Hook into your enemy status system here using `statusEffect`.
                // Example (adjust to your API):
                // enemy.applyStatus(statusEffect);
            }
        }
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
