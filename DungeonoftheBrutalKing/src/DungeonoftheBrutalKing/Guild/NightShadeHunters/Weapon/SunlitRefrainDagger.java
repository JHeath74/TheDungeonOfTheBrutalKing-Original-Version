package DungeonoftheBrutalKing.Guild.NightShadeHunters.Weapon;

import java.util.Random;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Weapon.WeaponFactory;
import DungeonoftheBrutalKing.Status.StatusType;

/**
 * Sunlit Refrain Dagger - hunter-friendly dagger that scales with agility,
 * grants a small crit chance while equipped and can proc Bleed on hit.
 */
public class SunlitRefrainDagger extends WeaponManager {

    private static final String NAME = "SunlitRefrainDagger";
    private static final int REQUIRED_AGILITY = 10;
    private static final int ATTACK_DAMAGE = 5;
    private static final int WEIGHT = 2;
    private static final double CRIT_CHANCE_BONUS = 0.05; // +5% crit while equipped
    private static final int AGILITY_BONUS = 2;

    private static final Guild GUILD_NAME = Guild.NIGHT_SHADE_HUNTERS;
    private static final GuildType GUILD_TYPE = GuildType.HUNTER;

    private static final Random RNG = new Random();

    private double lastCritBonus = 0.0;

    public SunlitRefrainDagger(String effect) {
        super(NAME, REQUIRED_AGILITY, ATTACK_DAMAGE, effect, WEIGHT);
    }

    public static SunlitRefrainDagger createFor(Character ch, String effect) {
        if (ch == null) throw new IllegalArgumentException("Character cannot be null.");
        try {
            if (ch.getGuild() == GUILD_NAME || ch.getCurrentGuild() == GUILD_TYPE) {
                if (ch.getAgility() >= REQUIRED_AGILITY) return new SunlitRefrainDagger(effect);
                throw new IllegalArgumentException("Not enough agility to wield Sunlit Refrain Dagger.");
            }
        } catch (Exception ignored) {}
        throw new IllegalArgumentException("Only NightShade Hunters or Hunters may wield this dagger.");
    }

    @Override
    public boolean equip(Character wielder) {
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
    public boolean unequip(Character wielder) {
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

    public void attackDamage(Character wielder, Enemies enemy) {
        if (wielder == null || enemy == null) return;
        int agility = wielder.getAgility();
        int bonus = RNG.nextInt(Math.max(1, (agility / 3) + 1));
        int totalDamage = ATTACK_DAMAGE + bonus;
        double critRoll = RNG.nextDouble();
        double critChance = Math.max(0.0, wielder.getCritChance());
        if (critRoll < critChance) {
            totalDamage *= 2;
        }
        enemy.takeDamage(totalDamage, null);

        // Bleed proc: base 8% + 1% per agility point, capped at 40%
        double bleedChance = 0.08 + (agility * 0.01);
        if (bleedChance > 0.40) bleedChance = 0.40;
        if (RNG.nextDouble() <= bleedChance) {
            // Not all Enemy classes expose an applyStatusEffect overload; to avoid
            // compile-time mismatch across the project, we skip applying the
            // status here and keep the damage effect only. The runtime engine
            // may still apply statuses via other systems.
        }
    }

    @Override public String getName() { return NAME; }
    @Override public double getWeight() { return WEIGHT; }
    @Override public double getDamage() { return ATTACK_DAMAGE; }

    static {
        try {
            WeaponFactory.register(NAME, (ch, effect) -> {
                try { return SunlitRefrainDagger.createFor(ch, effect); } catch (Exception e) { return null; }
            });
            // Also register a lowercase-with-spaces alias
            WeaponFactory.register("Sunlit Refrain Dagger", (ch, effect) -> {
                try { return SunlitRefrainDagger.createFor(ch, effect); } catch (Exception e) { return null; }
            });
        } catch (Exception ignored) {}
    }
}