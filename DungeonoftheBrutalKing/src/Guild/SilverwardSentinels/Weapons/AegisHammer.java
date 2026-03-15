package Guild.SilverwardSentinels.Weapons;

import java.util.Random;

import DungeonoftheBrutalKing.Charecter;
import Weapon.WeaponManager;
import Status.StatusType;
import SharedData.GuildType;
import SharedData.GuildMembershipStatus;

/**
 * AegisHammer - crushing hammer that can weaken enemy strength on hit.
 * Scales with Wisdom (primary) and Strength (secondary).
 */
public class AegisHammer extends WeaponManager {

    private static final String NAME = "Aegis Hammer";
    private static final int REQUIRED_STRENGTH = 9; // engine compatibility
    private static final int REQUIRED_WISDOM = 15;
    private static final int REQUIRED_STRENGTH_STAT = 13;
    private static final int BASE_DAMAGE = 32;
    private static final int WEIGHT = 24;

    private static final Random RNG = new Random();
    private static Charecter myChar = Charecter.getInstance();

    public AegisHammer(int damage, String effect) {
        super(NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public static AegisHammer createAegisHammer(Charecter character, int damage, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Silverward Sentinels to obtain Aegis Hammer.");
        if (character.getWisdom() < REQUIRED_WISDOM) throw new IllegalArgumentException("Insufficient Wisdom to wield Aegis Hammer.");
        if (character.getStrength() < REQUIRED_STRENGTH_STAT) throw new IllegalArgumentException("Insufficient Strength to wield Aegis Hammer.");
        return new AegisHammer(damage, effect);
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
            return base + (wis * 0.45) + (str * 0.30);
        } catch (Exception e) { return base; }
    }

    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.03;
        try {
            int wis = (myChar != null) ? myChar.getWisdom() : 0;
            int str = (myChar != null) ? myChar.getStrength() : 0;
            return baseCrit + (wis * 0.006) + (str * 0.004);
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
     * On hit: chance to apply REDUCE_STRENGTH_STATUS. chance = min(0.6, 0.10 + WIS*0.02). duration = 1 + WIS/15
     */
    @Override
    public void applyCombatEffect(Charecter target) {
        if (target == null) return;
        Charecter attacker = myChar != null ? myChar : Charecter.getInstance();
        int wis = (attacker != null) ? attacker.getWisdom() : 0;
        double chance = 0.10 + (wis * 0.02);
        chance = Math.min(0.60, chance);
        if (RNG.nextDouble() <= chance) {
            int duration = 1 + Math.max(0, wis / 15);
            try { target.applyStatusEffect(StatusType.REDUCE_STRENGTH_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
        }
    }
}
