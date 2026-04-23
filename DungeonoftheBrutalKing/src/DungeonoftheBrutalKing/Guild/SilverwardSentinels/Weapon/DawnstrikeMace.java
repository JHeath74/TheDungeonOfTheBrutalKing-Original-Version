package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Weapon;

import java.util.Random;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

/**
 * DawnstrikeMace - mace that can stun foes. Scales with Wisdom (primary) and Strength (secondary).
 */
public class DawnstrikeMace extends WeaponManager {

    private static final String NAME = "Dawnstrike Mace";
    private static final int REQUIRED_STRENGTH = 8; // engine compatibility
    private static final int REQUIRED_WISDOM = 14;
    private static final int REQUIRED_STRENGTH_STAT = 12;
    private static final int BASE_DAMAGE = 28;
    private static final int WEIGHT = 20;

    private static final Random RNG = new Random();
    private static Character myChar = Character.getInstance();

    public DawnstrikeMace(int damage, String effect) {
        super(NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public static DawnstrikeMace createDawnstrikeMace(Character character, int damage, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Silverward Sentinels to obtain Dawnstrike Mace.");
        if (character.getWisdom() < REQUIRED_WISDOM) throw new IllegalArgumentException("Insufficient Wisdom to wield Dawnstrike Mace.");
        if (character.getStrength() < REQUIRED_STRENGTH_STAT) throw new IllegalArgumentException("Insufficient Strength to wield Dawnstrike Mace.");
        return new DawnstrikeMace(damage, effect);
    }

    @Override
    public String getName() { return NAME; }

    public static boolean isGuildMember(Character c) { if (c == null) return false; try { return c.getCurrentGuild() == GuildType.PALADIN && c.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER; } catch (Exception e) { return false; } }
    public static boolean isPurchasableBy(Character c) { return isGuildMember(c); }

    @Override
    public double getDamage() {
        int base = Math.max(1, BASE_DAMAGE);
        try {
            int wis = (myChar != null) ? myChar.getWisdom() : 0;
            int str = (myChar != null) ? myChar.getStrength() : 0;
            return base + (wis * 0.45) + (str * 0.25);
        } catch (Exception e) {
            return base;
        }
    }

    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.04;
        try {
            int wis = (myChar != null) ? myChar.getWisdom() : 0;
            int str = (myChar != null) ? myChar.getStrength() : 0;
            return baseCrit + (wis * 0.007) + (str * 0.004);
        } catch (Exception e) { return baseCrit; }
    }

    @Override
    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getWisdom() < REQUIRED_WISDOM) return false;
        if (wearer.getStrength() < REQUIRED_STRENGTH_STAT) return false;
        try {
            wearer.setWeapon(this.getName());
            return true;
        } catch (Exception e) { return false; }
    }

    /**
     * Chance to STUN on hit. chance = min(0.60, 0.10 + WIS*0.02). duration = 1 + WIS/15
     */
    @Override
    public void applyCombatEffect(Character target) {
        if (target == null) return;
        Character attacker = myChar != null ? myChar : Character.getInstance();
        int wis = (attacker != null) ? attacker.getWisdom() : 0;
        double chance = 0.10 + (wis * 0.02);
        chance = Math.min(0.60, chance);
        if (RNG.nextDouble() <= chance) {
            int duration = 1 + Math.max(0, wis / 15);
            try { target.applyStatusEffect(StatusType.STUN_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
        }
    }
}
