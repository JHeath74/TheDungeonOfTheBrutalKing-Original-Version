
package Guild.DawnwardPaladins.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;

public class BlessedPlateArmor extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 12;
    private static final int DEFENSE_BONUS = 3;
    private static final int STRENGTH_BONUS = 1;
    private static final int WEIGHT = 8;

    private static final Guild GUILDname = Guild.DAWNWARD_PALADINS;
    private static final GuildType GUILDtype = GuildType.PALADIN;

    private boolean isEquipped = false;

    public BlessedPlateArmor(int requiredStrength, int defense, int weight, String effect) {
        super("Blessed Plate Armor", requiredStrength, defense, weight, effect);
    }

    public static BlessedPlateArmor createBlessedPlateArmor(Charecter character, int defense, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(3));
        if (strength >= REQUIRED_STRENGTH) {
            return new BlessedPlateArmor(REQUIRED_STRENGTH, defense, WEIGHT, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Blessed Plate Armor.");
    }

    @Override
    public boolean equip(Charecter wearer) {
        if (!isEquipped && wearer.getGuild() == GUILDname) {
            wearer.setDefense(wearer.getDefense() + DEFENSE_BONUS);
            wearer.setStrength(wearer.getStrength() + STRENGTH_BONUS);
            isEquipped = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unequip(Charecter wearer) {
        if (isEquipped) {
            wearer.setDefense(wearer.getDefense() - DEFENSE_BONUS);
            wearer.setStrength(wearer.getStrength() - STRENGTH_BONUS);
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
        return (double) WEIGHT;
    }

    @Override
    public double getDefense() {
        return (double) DEFENSE_BONUS;
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }

    @Override
    public String getDescription() {
        return "Blessed Plate Armor: Heavy armor blessed by the light, offering great protection and strength to paladins.";
    }
}
