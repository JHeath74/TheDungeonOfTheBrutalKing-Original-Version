
package Guild.DawnwardPaladins.Armour;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import SharedData.GuildType;
import Armour.ArmourManager;

public class RadiantChainmail extends ArmourManager {

    private static final int REQUIRED_STRENGTH = 10;
    private static final int DEFENSE_BONUS = 2;
    private static final int AGILITY_BONUS = 1;
    private static final int WEIGHT = 6;

    private static final Guild GUILDname = Guild.DAWNWARD_PALADINS;
    private static final GuildType GUILDtype = GuildType.PALADIN;

    private boolean isEquipped = false;

    public RadiantChainmail(int requiredStrength, int defense, int weight, String effect) {
        super("Radiant Chainmail", requiredStrength, defense, weight, effect);
    }

    public static RadiantChainmail createRadiantChainmail(Charecter character, int defense, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(3));
        if (strength >= REQUIRED_STRENGTH) {
            return new RadiantChainmail(REQUIRED_STRENGTH, defense, WEIGHT, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wear the Radiant Chainmail.");
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
        return "Radiant Chainmail: Lightweight chainmail infused with holy light, offering protection and agility to paladins.";
    }
}
