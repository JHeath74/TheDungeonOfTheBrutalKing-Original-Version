package Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.EquipmentRequirement;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;
import java.util.Set;

public class MysticCloak extends ArmourManager {

    private static final EquipmentRequirement REQUIREMENT = EquipmentRequirement.MYSTIC_CLOAK;
    private static final int CONCEALMENT_BONUS = 15; // percent
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private final Set<String> resistanceTypes;
    private boolean isEquipped = false;

    public MysticCloak(Set<String> resistanceTypes, String effect) {
        // Adjust constructor to match ArmourManager's signature
        super("Mystic Cloak", REQUIREMENT.getIntelligence(), effect, REQUIREMENT.weight); // Use public field if getter is not visible
        this.resistanceTypes = resistanceTypes;
    }

    @Override
    public void equip(Charecter wearer) {
        if (!isEquipped) {
            wearer.setEvadeChance(
                Math.min(wearer.getEvadeChance() + (CONCEALMENT_BONUS / 100.0), 0.9)
            );
            for (String resistance : resistanceTypes) {
                wearer.addResistance(resistance);
            }
            isEquipped = true;
        }
    }

    @Override
    public void unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.setEvadeChance(
                Math.max(wearer.getEvadeChance() - (CONCEALMENT_BONUS / 100.0), 0.0)
            );
            // Remove resistances from wearer if needed
            isEquipped = false;
        }
    }

    public Set<String> getResistanceTypes() {
        return resistanceTypes;
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
        // Use public field or parse if necessary
        return REQUIREMENT.weight;
    }

    @Override
    public String getDescription() {
        return "Mystic Cloak: A cloak woven from shadow, starlight, or elemental threads. Provides concealment and resistance to certain damage types.";
    }
}
