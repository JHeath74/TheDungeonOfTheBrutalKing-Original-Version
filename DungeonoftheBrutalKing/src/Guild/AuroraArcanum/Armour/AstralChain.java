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

    private static final int INTELLIGENCE_BONUS = 5;
    private static final double DEFENSE_BONUS_PERCENT = 0.15; // 15% defense bonus

    private boolean isEquipped = false;
    private int lastDefenseBonus = 0;

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
        if (!isEquipped && wearer.getGuild() == GUILDname) {
            wearer.setIntelligence(wearer.getIntelligence() + INTELLIGENCE_BONUS);
            lastDefenseBonus = (int) Math.round(wearer.getDefense() * DEFENSE_BONUS_PERCENT);
            wearer.setDefense(wearer.getDefense() + lastDefenseBonus);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.setIntelligence(wearer.getIntelligence() - INTELLIGENCE_BONUS);
            wearer.setDefense(wearer.getDefense() - lastDefenseBonus);
            isEquipped = false;
            lastDefenseBonus = 0;
        }
        return !isEquipped;
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
