// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\SilverwardSentinels\Armour\SentinelHelm.java
package Guild.SilverwardSentinels.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;
import Status.StatusType;
import SharedData.GuildType;
import SharedData.GuildMembershipStatus;

/**
 * SentinelHelm - protective helm that increases defence and resistance to status effects.
 * Primary: Wisdom, Secondary: Strength
 */
public class SentinelSuit extends ArmourManager {

    private static final String NAME = "SentinelHelm";
    private static final int REQUIRED_STRENGTH = 2; // engine compatibility
    private static final int REQUIRED_WISDOM = 13;
    private static final int REQUIRED_STRENGTH_STAT = 10;
    private static final int BASE_DEFENSE = 10;
    private static final int WEIGHT = 6;
    // Stat bonuses applied while equipped
    private static final int WISDOM_BONUS = 3;
    private static final int STRENGTH_BONUS = 2;

    private static Charecter myChar = Charecter.getInstance();

    public SentinelSuit(String effect) {
        super(NAME, REQUIRED_STRENGTH, BASE_DEFENSE, WEIGHT, effect);
    }

    public static SentinelSuit createSentinelHelm(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Silverward Sentinels to obtain SentinelHelm.");
        if (character.getWisdom() < REQUIRED_WISDOM) throw new IllegalArgumentException("Insufficient Wisdom to wear SentinelHelm.");
        if (character.getStrength() < REQUIRED_STRENGTH_STAT) throw new IllegalArgumentException("Insufficient Strength to wear SentinelHelm.");
        return new SentinelSuit(effect);
    }

    @Override
    public String getName() { return NAME; }

    public static boolean isGuildMember(Charecter c) { if (c == null) return false; try { return c.getCurrentGuild() == GuildType.PALADIN && c.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER; } catch (Exception e) { return false; } }
    public static boolean isPurchasableBy(Charecter c) { return isGuildMember(c); }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getWisdom() < REQUIRED_WISDOM) return false;
        if (wearer.getStrength() < REQUIRED_STRENGTH_STAT) return false;
        try {
            String current = wearer.getArmour();
            if (current != null && !current.isBlank() && !current.equals(this.getName())) return false;
            if (current != null && current.equals(this.getName())) return true;
            wearer.setArmour(this.getName());
            setEffect("RESILIENCE_STATUS");
            int newDef = wearer.getDefense() + this.getArmourDefense();
            wearer.setDefense(newDef);
            // apply stat bonuses
            wearer.setWisdom(wearer.getWisdom() + WISDOM_BONUS);
            wearer.setStrength(wearer.getStrength() + STRENGTH_BONUS);
            return true;
        } catch (Exception e) { return false; }
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;
        try {
            String current = wearer.getArmour();
            if (current == null || !current.equals(this.getName())) return false;
            wearer.setArmour("");
            int newDef = Math.max(0, wearer.getDefense() - this.getArmourDefense());
            wearer.setDefense(newDef);
            // remove stat bonuses
            wearer.setWisdom(Math.max(0, wearer.getWisdom() - WISDOM_BONUS));
            wearer.setStrength(Math.max(0, wearer.getStrength() - STRENGTH_BONUS));
            setEffect("NONE");
            return true;
        } catch (Exception e) { return false; }
    }

    @Override
    public int getArmourDefense() {
        int wis = (myChar != null) ? myChar.getWisdom() : 0;
        int str = (myChar != null) ? myChar.getStrength() : 0;
        return BASE_DEFENSE + (int)(wis * 0.35) + (int)(str * 0.15);
    }
}