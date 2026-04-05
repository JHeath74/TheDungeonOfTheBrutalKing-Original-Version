// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Armour\GloomHood.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

/**
 * GloomHood - a lightweight hood that increases stealth and critical potential.
 * Grants HIDDEN_STATUS while equipped (via setEffect) and scales modestly with Intelligence.
 */
public class WhisperweaveGarb extends ArmourManager {

    private static final String NAME = "Gloom Hood Ensemble";
    private static final int REQUIRED_STRENGTH = 0;
    private static final int REQUIRED_AGILITY = 12;
    private static final int REQUIRED_INTELLIGENCE = 13;
    private static final int BASE_DEFENSE = 5;
    private static final int WEIGHT = 3;

    private static Charecter myChar = Charecter.getInstance();

    public WhisperweaveGarb(String effect) {
        super(NAME, REQUIRED_STRENGTH, BASE_DEFENSE, WEIGHT, effect);
    }

    public static WhisperweaveGarb createGloomHood(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Obsidian Shadow Syndicate to obtain Gloom Hood Ensemble.");
        if (character.getAgility() < REQUIRED_AGILITY) throw new IllegalArgumentException("Insufficient Agility to wear Gloom Hood Ensemble.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE) throw new IllegalArgumentException("Insufficient Intelligence to wear Gloom Hood Ensemble.");
        WhisperweaveGarb hood = new WhisperweaveGarb(effect);
        hood.setEffect("HIDDEN_STATUS");
        return hood;
    }

    @Override
    public String getName() { return NAME; }

    public int getRequiredAgility() { return REQUIRED_AGILITY; }
    public int getRequiredIntelligence() { return REQUIRED_INTELLIGENCE; }

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
            wearer.setEquippedArmour("");
            int newDef = Math.max(0, wearer.getDefense() - this.getArmourDefense());
            wearer.setDefense(newDef);
            setEffect("NONE");
            return true;
        } catch (Exception e) { return false; }
    }

    @Override
    public int getArmourDefense() {
        int inti = (myChar != null) ? myChar.getIntelligence() : 0;
        return BASE_DEFENSE + (int)(inti * 0.2);
    }
}