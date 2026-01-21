
// src/Guild/AuroraArcanum/Armour/AstralChain.java
package Guild.AuroraArcanum.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;

public class AstralChain extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 10;
    private static final int ARMOUR_DEFENSE = 3;
    private static final int WEIGHT = 2;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;

    public AstralChain(String effect) {
        super("Astral Chain", REQUIRED_STRENGTH, ARMOUR_DEFENSE, effect);
    }
@Override
    public void equip(Charecter wearer) {
        if (!isEquipped) {
            // No penalty to spellcasting; just mark as equipped
            isEquipped = true;
        }
    }
@Override
    public void unequip(Charecter wearer) {
        if (isEquipped) {
            isEquipped = false;
        }
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
    public String getDescription() {
        return "Astral Chain: Lightweight chainmail forged from silver or mithril, enchanted to avoid hindering spellcasting. Offers modest physical protection while remaining arcane-friendly.";
    }
}
