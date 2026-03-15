// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Armour\ShadowCloak.java
package Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Charecter;
import Armour.ArmourManager;
import Status.StatusType;
import SharedData.GuildType;
import SharedData.GuildMembershipStatus;

/**
 * ShadowCloak - light cloak granting stealth and modest defense. Scales with Agility.
 */
public class VipercoilHarness extends ArmourManager {

    private static final String NAME = "Shadow Cloak Ensemble";
    private static final int REQUIRED_STRENGTH = 0; // engine compatibility
    private static final int REQUIRED_AGILITY = 13;
    private static final int REQUIRED_INTELLIGENCE = 12;
    private static final int BASE_DEFENSE = 8;
    private static final int WEIGHT = 6;

    private static Charecter myChar = Charecter.getInstance();

    public VipercoilHarness(String effect) {
        super(NAME, REQUIRED_STRENGTH, BASE_DEFENSE, WEIGHT, effect);
    }

    public static VipercoilHarness createShadowCloak(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Obsidian Shadow Syndicate to obtain the Shadow Cloak.");
        if (character.getAgility() < REQUIRED_AGILITY) throw new IllegalArgumentException("Insufficient Agility to wear Shadow Cloak.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE) throw new IllegalArgumentException("Insufficient Intelligence to wear Shadow Cloak.");
        return new VipercoilHarness(effect);
    }

    @Override
    public String getName() { return NAME; }

    public static boolean isGuildMember(Charecter c) {
        if (c == null) return false;
        try { return c.getCurrentGuild() == GuildType.THIEF && c.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER; } catch (Exception e) { return false; }
    }

    public static boolean isPurchasableBy(Charecter c) { return isGuildMember(c); }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;
        if (wearer.getIntelligence() < REQUIRED_INTELLIGENCE) return false;
        try {
            wearer.setArmour(this.getName());
            // grant hidden status while equipped? leave to combat system; just set effect slot
            setEffect("HIDDEN_STATUS");
            // increase the wearer's defense while equipped
            int newDef = wearer.getDefense() + this.getArmourDefense();
            wearer.setDefense(newDef);
            return true;
        } catch (Exception e) { return false; }
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;
        try {
            wearer.setArmour("");
            int newDef = Math.max(0, wearer.getDefense() - this.getArmourDefense());
            wearer.setDefense(newDef);
            setEffect("NONE");
            return true;
        } catch (Exception e) { return false; }
    }

    @Override
    public int getArmourDefense() {
        int agi = (myChar != null) ? myChar.getAgility() : 0;
        return BASE_DEFENSE + (int)(agi * 0.3);
    }
}