// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Armour\EbonVest.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

/**
 * EbonVest - reinforced leather vest offering solid defense while remaining stealthy.
 * Scales with Agility; grants minor defense-up while equipped.
 */
public class ShadowStalkerLeathers extends ArmourManager {

    private static final String NAME = "ShadowStalkerLeathers";
    private static final int REQUIRED_STRENGTH = 2;
    private static final int REQUIRED_AGILITY = 14;
    private static final int REQUIRED_INTELLIGENCE = 11;
    private static final int BASE_DEFENSE = 18;
    private static final int WEIGHT = 14;

    private static Charecter myChar = Charecter.getInstance();

    public ShadowStalkerLeathers(String effect) {
        super(NAME, REQUIRED_STRENGTH, BASE_DEFENSE, WEIGHT, effect);
    }

    public static ShadowStalkerLeathers createEbonVest(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Obsidian Shadow Syndicate to obtain ShadowStalkerLeathers.");
        if (character.getAgility() < REQUIRED_AGILITY) throw new IllegalArgumentException("Insufficient Agility to wear ShadowStalkerLeathers.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE) throw new IllegalArgumentException("Insufficient Intelligence to wear ShadowStalkerLeathers.");
        return new ShadowStalkerLeathers(effect);
    }

    @Override
    public String getName() { return NAME; }

    public static boolean isGuildMember(Charecter c) { if (c == null) return false; try { return c.getCurrentGuild() == GuildType.THIEF && c.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER; } catch (Exception e) { return false; } }
    public static boolean isPurchasableBy(Charecter c) { return isGuildMember(c); }

    @Override
    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (!isGuildMember(wearer)) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;
        if (wearer.getIntelligence() < REQUIRED_INTELLIGENCE) return false;
        try { 
            wearer.setEquippedArmour(this.getName()); 
            setEffect("DEFENSE_UP_STATUS"); 
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
            wearer.setEquippedArmour("");
            int newDef = Math.max(0, wearer.getDefense() - this.getArmourDefense());
            wearer.setDefense(newDef);
            setEffect("NONE");
            return true;
        } catch (Exception e) { return false; }
    }

    @Override
    public int getArmourDefense() {
        int agi = (myChar != null) ? myChar.getAgility() : 0;
        return BASE_DEFENSE + (int)(agi * 0.4);
    }
}