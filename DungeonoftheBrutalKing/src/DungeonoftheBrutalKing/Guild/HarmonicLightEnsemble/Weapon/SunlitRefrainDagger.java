
package DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.Weapon.WeaponManager;

import java.util.Random;

public class SunlitRefrainDagger extends WeaponManager {

    private static final int REQUIRED_CHARISMA = 12;
    private static final int CHARISMA_BONUS = 2;
    private static final int ATTACK_DAMAGE = 5;
    private static final int WEIGHT = 2;

    private static final int STATUS_CHANCE_PERCENT = 20;

    private static final Guild GUILD_NAME = Guild.DIRGEWEAVERS_CHORUS;
    private static final GuildType GUILD_TYPE = GuildType.BARD;

    private static final String WEAPON_NAME = "SunlitRefrainDagger";
    private static final String DESCRIPTION =
            "SunlitRefrainDagger: A dagger that sings with radiant chords.";

    private int lastCharismaBonus = 0;

    public SunlitRefrainDagger(String effect) {
        super(WEAPON_NAME, REQUIRED_CHARISMA, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static SunlitRefrainDagger create(Character character, String effect) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null.");
        }
        if (character.getGuild() != GUILD_NAME) {
            throw new IllegalArgumentException("Only Dirgeweavers Chorus members can wield this dagger.");
        }
        if (character.getCharisma() < REQUIRED_CHARISMA) {
            throw new IllegalArgumentException("Character lacks required charisma.");
        }
        return new SunlitRefrainDagger(effect);
    }

    @Override
    public boolean equip(Character wielder) {
        if (wielder == null) return false;
        if (wielder.getGuild() != GUILD_NAME) return false;

        if (wielder.getEquippedWeapon() == null || !wielder.getEquippedWeapon().equals(getName())) {
            wielder.setEquippedWeapon(getName());
            lastCharismaBonus = CHARISMA_BONUS;
            wielder.setCharisma(wielder.getCharisma() + lastCharismaBonus);
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wielder) {
        if (wielder == null) return false;

        if (wielder.getEquippedWeapon() != null && wielder.getEquippedWeapon().equals(getName())) {
            wielder.setEquippedWeapon(null);
            wielder.setCharisma(wielder.getCharisma() - lastCharismaBonus);
            lastCharismaBonus = 0;
            return true;
        }
        return false;
    }

    public void attackDamage(Character wielder, Enemies enemy) {
        if (wielder == null || enemy == null) return;

        Random rand = new Random();

        int charisma = wielder.getCharisma();
        int bonus = rand.nextInt((charisma / 4) + 1);
        int totalDamage = ATTACK_DAMAGE + bonus;

        enemy.takeDamage(totalDamage);

        StatusType statusEffect = getStatusEffect();
        if (statusEffect != null && statusEffect != StatusType.NONE) {
            int roll = rand.nextInt(100) + 1;
            if (roll <= STATUS_CHANCE_PERCENT) {
                // apply status effect to enemy here
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
    public String getDescription() {
        return DESCRIPTION;
    }
}
