package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Weapon;

import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

/**
 * SolarLance - a pole weapon that can reduce enemy defense (holy breach).
 * Scales with Wisdom (primary) and Strength (secondary).
 */
public class SolarLance extends WeaponManager {

    private static final String NAME = "Solar Lance";
    private static final int REQUIRED_STRENGTH = 6; // engine compatibility
    private static final int REQUIRED_WISDOM = 14;
    private static final int REQUIRED_STRENGTH_STAT = 11;
    private static final int BASE_DAMAGE = 26;
    private static final int WEIGHT = 18;

    private static final Random RNG = new Random();
    private static Charecter myChar = Charecter.getInstance();

    public SolarLance(int damage, String effect) {
        super(NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public static SolarLance createSolarLance(Charecter character, int damage, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Silverward Sentinels to obtain Solar Lance.");
        if (character.getWisdom() < REQUIRED_WISDOM) throw new IllegalArgumentException("Insufficient Wisdom to wield Solar Lance.");
        if (character.getStrength() < REQUIRED_STRENGTH_STAT) throw new IllegalArgumentException("Insufficient Strength to wield Solar Lance.");
        return new SolarLance(damage, effect);
    }

    @Override
    public String getName() { return NAME; }

    public static boolean isGuildMember(Charecter c) { if (c == null) return false; try { return c.getCurrentGuild() == GuildType.PALADIN && c.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER; } catch (Exception e) { return false; } }
    public static boolean isPurchasableBy(Charecter c) { return isGuildMember(c); }

    @Override
    public double getDamage() {
        int base = Math.max(1, BASE_DAMAGE);
        try {
            int wis = (myChar != null) ? myChar.getWisdom() : 0;
            int str = (myChar != null) ? myChar.getStrength() : 0;
            return base + (wis * 0.4) + (str * 0.25);
        } catch (Exception e) { return base; }
    }

    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.03;
        try {
            int wis = (myChar != null) ? myChar.getWisdom() : 0;
            int str = (myChar != null) ? myChar.getStrength() : 0;
            return baseCrit + (wis * 0.006) + (str * 0.003);
        } catch (Exception e) { return baseCrit; }
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getWisdom() < REQUIRED_WISDOM) return false;
        if (wearer.getStrength() < REQUIRED_STRENGTH_STAT) return false;
        try { wearer.setWeapon(this.getName()); return true; } catch (Exception e) { return false; }
    }

    /**
     * Chance to apply REDUCE_DEFENSE_STATUS. chance = min(0.60, 0.12 + WIS*0.015).
     * Duration = 2 turns.
     */
    @Override
    public void applyCombatEffect(Charecter target) {
        if (target == null) return;
        Charecter attacker = myChar != null ? myChar : Charecter.getInstance();
        int wis = (attacker != null) ? attacker.getWisdom() : 0;
        double chance = 0.12 + (wis * 0.015);
        chance = Math.min(0.60, chance);
        if (RNG.nextDouble() <= chance) {
            try { target.applyStatusEffect(StatusType.REDUCE_DEFENSE_STATUS, 2, 5, attacker); } catch (Exception ignored) {}
        }
    }
}
