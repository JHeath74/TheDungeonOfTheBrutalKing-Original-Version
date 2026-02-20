
package Guild.DawnwardPaladins.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;

public class AegisOfVirtue extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 18;
    private static final int DEFENSE_BONUS = 6;
    private static final int AGILITY_BONUS = -2;
    private static final int WEIGHT = 14;

    private static final Guild GUILDname = Guild.DAWNWARD_PALADINS;
    private static final GuildType GUILDtype = GuildType.PALADIN;

    private boolean isEquipped = false;

    public AegisOfVirtue(int requiredStrength, int defense, int weight, String effect) {
        super("Aegis of Virtue", requiredStrength, defense, weight, effect);
    }

    public static AegisOfVirtue createAegisOfVirtue(Charecter character, int defense, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(3));
        if (strength >= REQUIRED_STRENGTH) {
            return new AegisOfVirtue(REQUIRED_STRENGTH, defense, WEIGHT, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Aegis of Virtue.");
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped && wearer.getGuild() == GUILDname) {
            wearer.setDefense(wearer.getDefense() + DEFENSE_BONUS);
            wearer.setAgility(wearer.getAgility() + AGILITY_BONUS);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.setDefense(wearer.getDefense() - DEFENSE_BONUS);
            wearer.setAgility(wearer.getAgility() - AGILITY_BONUS);
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
    public String getDescription() {
        return "Aegis of Virtue: Legendary armor symbolizing the unwavering resolve of the Dawnward Paladins.";
    }
}
