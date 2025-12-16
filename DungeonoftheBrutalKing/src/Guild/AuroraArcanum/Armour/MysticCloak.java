
// src/Guild/AuroraArcanum/Armour/MysticCloak.java
package Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;
import java.util.Set;

public class MysticCloak extends ArmourManager {

    private static final int CONCEALMENT_BONUS = 15; // percent
    private static final int WEIGHT = 1;
    private static final Guild GUILDname = Guild.AURORA_ARCNUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private final Set<String> resistanceTypes;
    private boolean isEquipped = false;

    public MysticCloak(Set<String> resistanceTypes, String effect) {
        super("Mystic Cloak", effect, WEIGHT);
        this.resistanceTypes = resistanceTypes;
    }

    public void equip(Charecter wearer) {
        if (!isEquipped) {
            wearer.setEvadeChance(
                Math.min(wearer.getEvadeChance() + (CONCEALMENT_BONUS / 100.0), 0.9)
            );
            // Add logic to apply resistances to wearer if needed
            isEquipped = true;
        }
    }

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
        return (double) WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Mystic Cloak: A cloak woven from shadow, starlight, or elemental threads. Provides concealment and resistance to certain damage types.";
    }
}
