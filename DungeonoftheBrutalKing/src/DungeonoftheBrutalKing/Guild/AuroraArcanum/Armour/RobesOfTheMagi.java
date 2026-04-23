
package DungeonoftheBrutalKing.Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.SharedData.GuildType;
import DungeonoftheBrutalKing.Armour.ArmourManager;

public class RobesOfTheMagi extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 20;
    private static final double SPELL_RESISTANCE_BONUS_PERCENT = 0.10; // 10%
    private static final int ARMOUR_DEFENSE = 1; // light base defence
    private static final int WEIGHT = 2;

    private boolean isEquipped = false;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    public RobesOfTheMagi(String effect) {
        // requiredStrength is unused for wizards, so pass 0
        super("Robes of the Magi", 0, ARMOUR_DEFENSE, WEIGHT, effect);
    }

    public static RobesOfTheMagi createRobes(Character character, String effect) {
        int intelligence = character.getIntelligence();
        if (intelligence >= REQUIRED_INTELLIGENCE) {
            return new RobesOfTheMagi(effect);
        }
        throw new IllegalArgumentException(
                "Character does not have the required intelligence to wear the Robes of the Magi.");
    }

    @Override
    public boolean equip(Character wearer) {
        if (!isEquipped && wearer.getGuild() == GUILDname && wearer.getIntelligence() >= REQUIRED_INTELLIGENCE) {
            int baseSpellResistance = wearer.getIntelligence() + wearer.getWisdom();
            int bonus = (int) Math.round(baseSpellResistance * SPELL_RESISTANCE_BONUS_PERCENT);
            wearer.setSpellResistanceBonus(wearer.getSpellResistanceBonus() + bonus);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Character wearer) {
        if (isEquipped) {
            int baseSpellResistance = wearer.getIntelligence() + wearer.getWisdom();
            int bonus = (int) Math.round(baseSpellResistance * SPELL_RESISTANCE_BONUS_PERCENT);
            wearer.setSpellResistanceBonus(Math.max(0, wearer.getSpellResistanceBonus() - bonus));
            isEquipped = false;
            return true;
        }
        return false;
    }

    public Guild getGuild() {
        return GUILDname;
    }

    public GuildType getGuildType() {
        return GUILDtype;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return (double) WEIGHT;
    }

    @Override
    public double getDefense() {
        return (double) ARMOUR_DEFENSE;
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    @Override
    public String getDescription() {
        return "Robes of the Magi: Flowing enchanted robes that boost spell resistance and enhance magical focus. "
                + "Embroidered with runes or sigils that shimmer when spells are cast.";
    }
}
