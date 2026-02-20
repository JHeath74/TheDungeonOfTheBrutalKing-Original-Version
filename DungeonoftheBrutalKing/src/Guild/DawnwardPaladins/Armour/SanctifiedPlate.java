
package Guild.DawnwardPaladins.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;

public class SanctifiedPlate extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 14;
    private static final int DEFENSE_BONUS = 4;
    private static final int AGILITY_BONUS = 0;
    private static final int WEIGHT = 10;

    private static final Guild GUILDname = Guild.DAWNWARD_PALADINS;
    private static final GuildType GUILDtype = GuildType.PALADIN;

    private boolean isEquipped = false;

    public SanctifiedPlate(int requiredStrength, int defense, int weight, String effect) {
        super("Sanctified Plate", requiredStrength, defense, weight, effect);
    }

    public static SanctifiedPlate createSanctifiedPlate(Charecter character, int defense, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(3));
        if (strength >= REQUIRED_STRENGTH) {
            return new SanctifiedPlate(REQUIRED_STRENGTH, defense, WEIGHT, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Sanctified Plate.");
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
        return "Sanctified Plate: Heavy plate armor blessed for the Dawnward Paladins, offering superior protection.";
    }
}
