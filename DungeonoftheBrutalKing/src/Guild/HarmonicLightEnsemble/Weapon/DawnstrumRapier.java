
package Guild.HarmonicLightEnsemble.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Weapon.WeaponManager;

public class DawnstrumRapier extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.HARMONILIC_LIGHT_ENSEMBLE;

    private static final String WEAPON_NAME = "DawnstrumRapier";
    private static final String EFFECT =
            "DawnstrumRapier: A bright rapier reserved for the Harmonic Light Ensemble. " +
            "Increases attack and bolsters charisma while wielded.";

    private static final int WEIGHT = 4;

    private static final int REQUIRED_STRENGTH = 8;
    private static final int REQUIRED_CHARISMA = 10;

    private static final int ATTACK_INCREASE = 6;
    private static final int CHARISMA_BONUS = 3;

    // Track last applied bonuses for correct removal
    private int lastAttackBonus = 0;
    private int lastChaBonus = 0;

    private DawnstrumRapier() {
        super(WEAPON_NAME, REQUIRED_STRENGTH, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static DawnstrumRapier createDawnstrumRapier(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Harmonic Light Ensemble members can wield the DawnstrumRapier.");
        if (character.getStrength() < REQUIRED_STRENGTH)
            throw new IllegalArgumentException("Character does not have the required strength to wield the DawnstrumRapier.");
        if (character.getCharisma() < REQUIRED_CHARISMA)
            throw new IllegalArgumentException("Character does not have the required charisma to wield the DawnstrumRapier.");

        return new DawnstrumRapier();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getStrength() < REQUIRED_STRENGTH) return false;
        if (wearer.getCharisma() < REQUIRED_CHARISMA) return false;

        lastAttackBonus = ATTACK_INCREASE;
        lastChaBonus = CHARISMA_BONUS;

        wearer.setAttack(wearer.getAttack() + lastAttackBonus);
        wearer.setCharisma(wearer.getCharisma() + lastChaBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastAttackBonus != 0) wearer.setAttack(Math.max(0, wearer.getAttack() - lastAttackBonus));
        if (lastChaBonus != 0) wearer.setCharisma(Math.max(0, wearer.getCharisma() - lastChaBonus));

        lastAttackBonus = 0;
        lastChaBonus = 0;

        return true;
    }

    @Override
    public String getName() {
        return WEAPON_NAME;
    }

    public String getEffect() {
        return EFFECT;
    }

    @Override
    public double getWeight() {
        return WEIGHT;
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }

    public int getRequiredCharisma() {
        return REQUIRED_CHARISMA;
    }

    public int getAttackIncrease() {
        return ATTACK_INCREASE;
    }

    public int getCharismaBonus() {
        return CHARISMA_BONUS;
    }
}
