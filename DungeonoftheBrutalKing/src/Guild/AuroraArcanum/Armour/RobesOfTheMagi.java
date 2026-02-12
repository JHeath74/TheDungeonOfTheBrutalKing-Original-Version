
// src/Guild/AuroraArcanum/Armour/RobesOfTheMagi.java
package Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;

public class RobesOfTheMagi extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 20;
    private static final int INTELLIGENCE_BONUS = 2;
    private static final int SPELL_RESISTANCE_BONUS = 10; // percent
    private static final int WEIGHT = 2;

    private boolean isEquipped = false;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    public RobesOfTheMagi(int requiredIntelligence, String effect, int weight) {
        super("Robes of the Magi", requiredIntelligence, weight, effect);
    }

    public static RobesOfTheMagi createRobes(Charecter character, String effect) {
        int intelligence = character.getIntelligence();
        if (intelligence >= REQUIRED_INTELLIGENCE) {
            return new RobesOfTheMagi(REQUIRED_INTELLIGENCE, effect, WEIGHT);
        }
        throw new IllegalArgumentException("Character does not have the required intelligence to wear the Robes of the Magi.");
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped && wearer.getGuild() == GUILDname) {
            wearer.setIntelligence(wearer.getIntelligence() + INTELLIGENCE_BONUS);
            // wearer.setSpellResistance(wearer.getSpellResistance() + SPELL_RESISTANCE_BONUS); // Uncomment if supported
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.setIntelligence(wearer.getIntelligence() - INTELLIGENCE_BONUS);
            // wearer.setSpellResistance(wearer.getSpellResistance() - SPELL_RESISTANCE_BONUS); // Uncomment if supported
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
        return (double) SPELL_RESISTANCE_BONUS;
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    @Override
    public String getDescription() {
        return "Robes of the Magi: Flowing enchanted robes that boost spell resistance and enhance magical focus. Embroidered with runes or sigils that shimmer when spells are cast.";
    }
}
