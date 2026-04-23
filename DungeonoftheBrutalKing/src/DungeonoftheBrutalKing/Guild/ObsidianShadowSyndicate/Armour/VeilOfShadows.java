// filepath: g:\Programs\Github\Java\TheDungeonOfTheBrutalKing-Original-Version\DungeonoftheBrutalKing\src\Guild\ObsidianShadowSyndicate\Armour\VeilOfShadows.java
package DungeonoftheBrutalKing.Guild.ObsidianShadowSyndicate.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Armour.ArmourManager;
import DungeonoftheBrutalKing.Status.StatusType;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.SharedData.GuildMembershipStatus;

/**
 * VeilOfShadows - lightweight torso armor that boosts stealth, evasion and crit chance.
 * Primary stat: Agility. Secondary: Intelligence. Requires full guild membership.
 */
public class VeilOfShadows extends ArmourManager {

    private static final String NAME = "VeilOfShadows";
    private static final int REQUIRED_STRENGTH = 0;
    private static final int REQUIRED_AGILITY = 14;
    private static final int REQUIRED_INTELLIGENCE = 13;
    private static final int BASE_DEFENSE = 12;
    private static final int WEIGHT = 7;

    private static Character myChar = Character.getInstance();

    public VeilOfShadows(String effect) {
        super(NAME, REQUIRED_STRENGTH, BASE_DEFENSE, WEIGHT, effect);
    }

    public static VeilOfShadows createVeilOfShadows(Character character, String effect) {
        if (character == null) throw new IllegalArgumentException("Character is null");
        if (!isGuildMember(character)) throw new IllegalArgumentException("Must be a full member of Obsidian Shadow Syndicate to obtain VeilOfShadows.");
        if (character.getAgility() < REQUIRED_AGILITY) throw new IllegalArgumentException("Insufficient Agility to wear VeilOfShadows.");
        if (character.getIntelligence() < REQUIRED_INTELLIGENCE) throw new IllegalArgumentException("Insufficient Intelligence to wear VeilOfShadows.");
        VeilOfShadows v = new VeilOfShadows(effect);
        v.setEffect("HIDDEN_STATUS");
        return v;
    }

    @Override
    public String getName() { return NAME; }

    public int getRequiredAgility() { return REQUIRED_AGILITY; }
    public int getRequiredIntelligence() { return REQUIRED_INTELLIGENCE; }

    public static boolean isGuildMember(Character c) { if (c == null) return false; try { return c.getCurrentGuild() == GuildType.THIEF && c.getCurrentGuildStatus() == GuildMembershipStatus.FULL_MEMBER; } catch (Exception e) { return false; } }
    public static boolean isPurchasableBy(Character c) { return isGuildMember(c); }

    @Override
    public boolean equip(Character wearer) {
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
    public boolean unequip(Character wearer) {
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
        int inti = (myChar != null) ? myChar.getIntelligence() : 0;
        return BASE_DEFENSE + (int)(agi * 0.25) + (int)(inti * 0.1);
    }
}