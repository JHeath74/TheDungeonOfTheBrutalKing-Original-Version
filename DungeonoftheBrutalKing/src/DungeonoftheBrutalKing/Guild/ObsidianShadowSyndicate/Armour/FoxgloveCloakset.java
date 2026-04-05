// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Armour\SilentBoots.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

/**
 * SilentBoots - lightweight boots that increase evasion and grant a short stealth bonus.
 */
public class FoxgloveCloakset extends ArmourManager {
    private static final String NAME = "FoxgloveCloakset";
    private static final int REQUIRED_STRENGTH = 0;
    private static final int REQUIRED_AGILITY = 14;
    private static final int REQUIRED_INTELLIGENCE = 11;
    private static final int BASE_DEFENSE = 6;
    private static final int WEIGHT = 4;

    private static Charecter myChar = Charecter.getInstance();

    public FoxgloveCloakset(String effect) {
        super(NAME, REQUIRED_STRENGTH, BASE_DEFENSE, WEIGHT, effect);
        setEffect("EVASION_STATUS");
    }

    public static FoxgloveCloakset createSilentBoots(Charecter character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Obsidian Shadow Syndicate to obtain FoxgloveCloakset.");
        if (character.getAgility() < REQUIRED_AGILITY) throw new IllegalArgumentException("Insufficient Agility to wear FoxgloveCloakset.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE) throw new IllegalArgumentException("Insufficient Intelligence to wear FoxgloveCloakset.");
        return new FoxgloveCloakset(effect);
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
            // enforce single-armour rule: fail if wearer already has a different armour equipped
            String current = wearer.getArmour();
            if (current != null && !current.isBlank() && !current.equals(this.getName())) return false;
            if (current != null && current.equals(this.getName())) return true; // already equipped
             wearer.setArmour(this.getName());
             setEffect("EVASION_STATUS");
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
            // only unequip if this armour is currently equipped
            String current = wearer.getArmour();
            if (current == null || !current.equals(this.getName())) return false;
            // remove armour and subtract defense bonus
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
        return BASE_DEFENSE + (int)(agi * 0.25);
    }
}