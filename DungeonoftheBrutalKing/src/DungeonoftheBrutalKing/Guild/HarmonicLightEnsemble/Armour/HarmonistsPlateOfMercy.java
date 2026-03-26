
package Guild.HarmonicLightEnsemble.Armour;

import Armour.ArmourManager;
import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;

public class HarmonistsPlateOfMercy extends ArmourManager {

    private static final Guild REQUIRED_GUILD = Guild.HARMONILIC_LIGHT_ENSEMBLE;

    private static final String ARMOUR_NAME = "HarmonistsPlateOfMercy";
    private static final String DESCRIPTION =
            "HarmonistsPlateOfMercy: Sanctified performance plate reserved for the Harmonic Light Ensemble. " +
            "Balances protection with presence; bolsters charisma, vitality, and defense.";

    private static final int WEIGHT = 8;

    private static final int REQUIRED_STRENGTH = 0;
    private static final int REQUIRED_VITALITY = 12;
    private static final int REQUIRED_CHARISMA = 12;

    private static final int ARMOUR_DEFENSE = 7;
    private static final int DEFENSE_BONUS = 7;
    private static final int VITALITY_BONUS = 3;
    private static final int CHARISMA_BONUS = 3;

    private int lastDefBonus = 0;
    private int lastVitBonus = 0;
    private int lastChaBonus = 0;

    public HarmonistsPlateOfMercy() {
        super(ARMOUR_NAME, REQUIRED_STRENGTH, ARMOUR_DEFENSE, WEIGHT, DESCRIPTION);
    }

    public static HarmonistsPlateOfMercy create(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Harmonic Light Ensemble members can wear the HarmonistsPlateOfMercy.");
        if (character.getVitality() < REQUIRED_VITALITY)
            throw new IllegalArgumentException("Character does not have the required vitality to wear the HarmonistsPlateOfMercy.");
        if (character.getCharisma() < REQUIRED_CHARISMA)
            throw new IllegalArgumentException("Character does not have the required charisma to wear the HarmonistsPlateOfMercy.");

        return new HarmonistsPlateOfMercy();
    }

    public boolean canEquip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getVitality() < REQUIRED_VITALITY) return false;
        if (wearer.getCharisma() < REQUIRED_CHARISMA) return false;
        return true;
    }

    public boolean equip(Charecter wearer) {
        if (!canEquip(wearer)) return false;

        lastDefBonus = DEFENSE_BONUS;
        lastVitBonus = VITALITY_BONUS;
        lastChaBonus = CHARISMA_BONUS;

        wearer.setDefense(wearer.getDefense() + lastDefBonus);
        wearer.setVitality(wearer.getVitality() + lastVitBonus);
        wearer.setCharisma(wearer.getCharisma() + lastChaBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastDefBonus != 0) {
            wearer.setDefense(wearer.getDefense() - lastDefBonus);
        }
        if (lastVitBonus != 0) {
            wearer.setVitality(Math.max(0, wearer.getVitality() - lastVitBonus));
        }
        if (lastChaBonus != 0) {
            wearer.setCharisma(Math.max(0, wearer.getCharisma() - lastChaBonus));
        }

        lastDefBonus = 0;
        lastVitBonus = 0;
        lastChaBonus = 0;

        return true;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    public double getWeight() {
        return WEIGHT;
    }

    public int getRequiredVitality() {
        return REQUIRED_VITALITY;
    }

    public int getRequiredCharisma() {
        return REQUIRED_CHARISMA;
    }

    public int getDefenseBonus() {
        return DEFENSE_BONUS;
    }

    public int getVitalityBonus() {
        return VITALITY_BONUS;
    }

    public int getCharismaBonus() {
        return CHARISMA_BONUS;
    }
}
