package DungeonoftheBrutalKing.Guild.SilverwardSentinels.Weapon;

import java.util.Random;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Weapon.WeaponManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

/**
 * VigilantFlail - versatile flail that can drain the enemy (life/mana drain).
 * Scales with Wisdom (primary) and Strength (secondary).
 */
public class VigilantFlail extends WeaponManager {

    private static final String NAME = "Vigilant Flail";
    private static final int REQUIRED_STRENGTH = 6; // engine compatibility
    private static final int REQUIRED_WISDOM = 13;
    private static final int REQUIRED_STRENGTH_STAT = 11;
    private static final int BASE_DAMAGE = 20;
    private static final int WEIGHT = 16;

    private static final Random RNG = new Random();
    private static Character myChar = Character.getInstance();

    public VigilantFlail(int damage, String effect) {
        super(NAME, REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public static VigilantFlail createVigilantFlail(Character character, int damage, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Silverward Sentinels to obtain Vigilant Flail.");
        if (character.getWisdom() < REQUIRED_WISDOM) throw new IllegalArgumentException("Insufficient Wisdom to wield Vigilant Flail.");
        if (character.getStrength() < REQUIRED_STRENGTH_STAT) throw new IllegalArgumentException("Insufficient Strength to wield Vigilant Flail.");
        return new VigilantFlail(damage, effect);
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
            return base + (wis * 0.38) + (str * 0.22);
        } catch (Exception e) { return base; }
    }

    @Override
    public double getCriticalHitChance() {
        double baseCrit = 0.03;
        try {
            int wis = (myChar != null) ? myChar.getWisdom() : 0;
            int str = (myChar != null) ? myChar.getStrength() : 0;
            return baseCrit + (wis * 0.005) + (str * 0.003);
        } catch (Exception e) { return baseCrit; }
    }

    @Override
    public boolean equip(Character wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getWisdom() < REQUIRED_WISDOM) return false;
        if (wearer.getStrength() < REQUIRED_STRENGTH_STAT) return false;
        try { wearer.setEquippedWeapon(this.getName()); return true; } catch (Exception e) { return false; }
    }

    /**
     * On hit: chance to apply DRAIN_STATUS (life/mana drain). chance = min(0.6, 0.12 + WIS*0.02).
     * If applied, duration = 1 + WIS/12.
     */
    @Override
    public void applyCombatEffect(Character target) {
        if (target == null) return;
        Character attacker = myChar != null ? myChar : Character.getInstance();
        int wis = (attacker != null) ? attacker.getWisdom() : 0;
        double chance = 0.12 + (wis * 0.02);
        chance = Math.min(0.60, chance);
        if (RNG.nextDouble() <= chance) {
            int duration = 1 + Math.max(0, wis / 12);
            try { target.applyStatusEffect(StatusType.DRAIN_STATUS, duration, 0, attacker); } catch (Exception ignored) {}
        }
    }
}
