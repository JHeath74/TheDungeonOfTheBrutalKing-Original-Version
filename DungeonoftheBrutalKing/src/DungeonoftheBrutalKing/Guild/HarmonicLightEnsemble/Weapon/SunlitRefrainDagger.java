package DungeonoftheBrutalKing.Guild.HarmonicLightEnsemble.Weapon;

import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Weapon.WeaponFactory;
import DungeonoftheBrutalKing.Status.StatusType;

/**
 * Sunlit Refrain Dagger - a light, precise dagger tailored for hunters.
 * Scales with Agility, grants a small crit bonus while equipped and has a chance
 * to cause Bleed on hit.
 */
public class SunlitRefrainDagger extends WeaponManager {

    private static final String NAME = "Sunlit Refrain Dagger";
    private static final int REQUIRED_AGILITY = 10;
    private static final int ATTACK_DAMAGE = 6;
    private static final int WEIGHT = 3;
    private static final double CRIT_CHANCE_BONUS = 0.03; // +3% crit while equipped
    private static final int AGILITY_BONUS = 3; // temporary agility buff when equipped

    private static final Guild GUILD_NAME = Guild.NIGHT_SHADE_HUNTERS; // primary guild
    private static final GuildType GUILD_TYPE = GuildType.HUNTER;

    private static final String DESCRIPTION = "Sunlit Refrain Dagger: a lightweight hunter's blade tuned for precision — increases agility and critical strike chance, with a chance to cause bleeding.";

    private static final Random RNG = new Random();
    private static Charecter myChar = Charecter.getInstance();

    private double lastCritBonus = 0.0;

    public SunlitRefrainDagger(String effect) {
        super(NAME, REQUIRED_AGILITY, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static SunlitRefrainDagger createSunlitRefrainDagger(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        // Allow either explicit Night Shade Hunters membership or anyone currently in the Hunter guild type
        try {
            if (character.getGuild() == GUILD_NAME || character.getCurrentGuild() == GUILD_TYPE) {
                if (character.getAgility() >= REQUIRED_AGILITY) {
                    return new SunlitRefrainDagger(effect);
                }
                throw new IllegalArgumentException("Character does not have the required agility to wield the Sunlit Refrain Dagger.");
            }
        } catch (Exception e) {
            // fall through and throw below to keep behavior consistent
        }
        throw new IllegalArgumentException("Only members of the Night Shade Hunters (or characters with Hunter class) can wield the Sunlit Refrain Dagger.");
    }

    @Override
    public boolean equip(Charecter wielder) {
        if (wielder == null) return false;
        try {
            if (wielder.getGuild() != GUILD_NAME && wielder.getCurrentGuild() != GUILD_TYPE) return false;
            if (wielder.getEquippedWeapon() == null || !wielder.getEquippedWeapon().equals(getName())) {
                wielder.setEquippedWeapon(getName());
                wielder.setAgility(wielder.getAgility() + AGILITY_BONUS);
                lastCritBonus = CRIT_CHANCE_BONUS;
                wielder.setCritChance(wielder.getCritChance() + lastCritBonus);
                return true;
            }
        } catch (Exception e) { return false; }
        return false;
    }

    @Override
    public boolean unequip(Charecter wielder) {
        if (wielder == null) return false;
        try {
            if (wielder.getEquippedWeapon() != null && wielder.getEquippedWeapon().equals(getName())) {
                wielder.setEquippedWeapon(null);
                wielder.setAgility(wielder.getAgility() - AGILITY_BONUS);
                wielder.setCritChance(wielder.getCritChance() - lastCritBonus);
                lastCritBonus = 0.0;
                return true;
            }
        } catch (Exception e) { return false; }
        return false;
    }

    /**
     * Performs attack: base damage + agility-based bonus. May crit (uses wielder crit chance)
     * and can apply BLEED status on hit (chance scales with agility).
     */
    public void attackDamage(Charecter wielder, Enemies enemy) {
        if (wielder == null || enemy == null) return;
        int agility = wielder.getAgility();
        int bonus = RNG.nextInt(Math.max(1, (agility / 3) + 1));
        int totalDamage = ATTACK_DAMAGE + bonus;
        double critRoll = RNG.nextDouble();
        double critChance = Math.max(0.0, wielder.getCritChance());
        if (critRoll < critChance) {
            totalDamage *= 2;
        }
        enemy.takeDamage(totalDamage);

        // Bleed proc: base 8% + 1% per agility point, capped at 40%
        double bleedChance = 0.08 + (agility * 0.01);
        if (bleedChance > 0.40) bleedChance = 0.40;
        if (RNG.nextDouble() <= bleedChance) {
            int duration = 1 + Math.max(0, agility / 12);
            enemy.applyStatusEffect(StatusType.BLEED_STATUS, duration, 1, wielder);
        }
    }

    public Guild getGuild() { return GUILD_NAME; }
    public GuildType getGuildType() { return GUILD_TYPE; }

    @Override
    public String getName() { return NAME; }

    @Override
    public double getWeight() { return WEIGHT; }

    @Override
    public double getDamage() { return ATTACK_DAMAGE; }

    @Override
    public void setEffect(String effect) { super.setEffect(effect); }

    @Override
    public String getDescription() { return DESCRIPTION; }

    // Static registration with WeaponFactory
    static {
        try {
            WeaponFactory.register("SunlitRefrainDagger", (ch, effect) -> {
                try { return SunlitRefrainDagger.createSunlitRefrainDagger(ch, effect); } catch (Exception e) { return null; }
            });
        } catch (Exception ignored) {}
    }
}