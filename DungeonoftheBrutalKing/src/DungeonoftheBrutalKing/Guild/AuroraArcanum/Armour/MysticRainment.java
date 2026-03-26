
// src/Guild/AuroraArcanum/Armour/MysticRainment.java
package Guild.AuroraArcanum.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;
import java.util.Set;

public class MysticRainment extends ArmourManager {

    private static final int REQUIRED_INTELLIGENCE = 15; // Example value
    private static final double CONCEALMENT_BONUS_PERCENT = 0.15; // 15%
    private static final int WEIGHT = 3;
    private static final Guild GUILDname = Guild.AURORA_ARCANUM;
    private static final GuildType GUILDtype = GuildType.WIZARD;

    private final Set<String> resistanceTypes;
    private boolean isEquipped = false;

    public MysticRainment(Set<String> resistanceTypes, String effect) {
        super("Mystic Raiment", REQUIRED_INTELLIGENCE, WEIGHT, effect);
        this.resistanceTypes = resistanceTypes;
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped && wearer.getGuild() == GUILDname && wearer.getIntelligence() >= REQUIRED_INTELLIGENCE) {
            double bonus = wearer.getEvadeChance() * CONCEALMENT_BONUS_PERCENT;
            wearer.setEvadeChance(Math.min(wearer.getEvadeChance() + bonus, 0.9));
            for (String resistance : resistanceTypes) {
                wearer.addResistance(resistance);
            }
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            double bonus = wearer.getEvadeChance() / (1 + CONCEALMENT_BONUS_PERCENT) * CONCEALMENT_BONUS_PERCENT;
            wearer.setEvadeChance(Math.max(wearer.getEvadeChance() - bonus, 0.0));
            for (String resistance : resistanceTypes) {
                wearer.removeResistance(resistance);
            }
            isEquipped = false;
            return true;
        }
        return false;
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
        return WEIGHT;
    }

    @Override
    public String getDescription() {
        return "Mystic Raiment: A cloak woven from shadow, starlight, or elemental threads. Provides concealment and resistance to certain damage types.";
    }
}
