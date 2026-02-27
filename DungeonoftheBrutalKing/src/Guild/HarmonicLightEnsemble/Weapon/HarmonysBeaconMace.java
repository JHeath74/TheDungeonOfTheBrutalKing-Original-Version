
package Guild.HarmonicLightEnsemble.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Weapon.WeaponManager;

public class HarmonysBeaconMace extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.HARMONILIC_LIGHT_ENSEMBLE;

    private static final String WEAPON_NAME = "HarmonysBeaconMace";
    private static final String EFFECT =
            "HarmonysBeaconMace: A luminous mace reserved for the Harmonic Light Ensemble. " +
            "Increases attack and bolsters vitality while wielded.";

    private static final int WEIGHT = 9;

    private static final int REQUIRED_STRENGTH = 12;
    private static final int REQUIRED_VITALITY = 10;

    private static final int ATTACK_INCREASE = 7;
    private static final int VITALITY_BONUS = 3;

    // Track last applied bonuses for correct removal
    private int lastAttackBonus = 0;
    private int lastVitBonus = 0;

    private HarmonysBeaconMace() {
        super(WEAPON_NAME, REQUIRED_STRENGTH, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static HarmonysBeaconMace createHarmonysBeaconMace(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Harmonic Light Ensemble members can wield the HarmonysBeaconMace.");
        if (character.getStrength() < REQUIRED_STRENGTH)
            throw new IllegalArgumentException("Character does not have the required strength to wield the HarmonysBeaconMace.");
        if (character.getVitality() < REQUIRED_VITALITY)
            throw new IllegalArgumentException("Character does not have the required vitality to wield the HarmonysBeaconMace.");

        return new HarmonysBeaconMace();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getStrength() < REQUIRED_STRENGTH) return false;
        if (wearer.getVitality() < REQUIRED_VITALITY) return false;

        lastAttackBonus = ATTACK_INCREASE;
        lastVitBonus = VITALITY_BONUS;

        wearer.setAttack(wearer.getAttack() + lastAttackBonus);
        wearer.setVitality(wearer.getVitality() + lastVitBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastAttackBonus != 0) wearer.setAttack(Math.max(0, wearer.getAttack() - lastAttackBonus));
        if (lastVitBonus != 0) wearer.setVitality(Math.max(0, wearer.getVitality() - lastVitBonus));

        lastAttackBonus = 0;
        lastVitBonus = 0;

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

    public int getRequiredVitality() {
        return REQUIRED_VITALITY;
    }

    public int getAttackIncrease() {
        return ATTACK_INCREASE;
    }

    public int getVitalityBonus() {
        return VITALITY_BONUS;
    }
}
