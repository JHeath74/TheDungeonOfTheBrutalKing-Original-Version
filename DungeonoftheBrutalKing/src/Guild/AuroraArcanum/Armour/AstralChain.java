package Guild.AuroraArcanum.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.EquipmentRequirement;
import SharedData.Guild;
import SharedData.GuildType;

public class AstralChain extends ArmourManager {

    private static final EquipmentRequirement REQUIREMENT = EquipmentRequirement.ASTRAL_CHAIN;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private boolean isEquipped = false;

    public AstralChain(String effect) {
        super("Astral Chain", REQUIREMENT.getStrength(), REQUIREMENT.getDefense(), effect);
    }

    public static AstralChain createAstralChain(Charecter character, String effect) {
        if (character.getStrength() >= REQUIREMENT.getStrength()) {
            return new AstralChain(effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Astral Chain.");
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped) {
            isEquipped = true;
        }
		return isEquipped;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            isEquipped = false;
        }
		return isEquipped;
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
        return (double) REQUIREMENT.getWeight();
    }

    @Override
    public String getDescription() {
        return "Astral Chain: Lightweight chainmail forged from silver or mithril, enchanted to avoid hindering spellcasting. Offers modest physical protection while remaining arcane-friendly.";
    }
}
