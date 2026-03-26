
package Guild.NightShadeHunters.Weapon;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Weapon.WeaponManager;

public class ShadowthornLongbow extends WeaponManager {

    private static final Guild REQUIRED_GUILD = Guild.NIGHT_SHADE_HUNTERS;

    private static final String WEAPON_NAME = "ShadowthornLongbow";
    private static final String EFFECT =
            "ShadowthornLongbow: A longbow bound with shadowthorn twine, reserved for the Night Shade Hunters. " +
            "Increases attack and bolsters agility while wielded.";

    private static final int WEIGHT = 4;

    private static final int REQUIRED_STRENGTH = 7;
    private static final int REQUIRED_AGILITY = 13;

    private static final int ATTACK_INCREASE = 6;
    private static final int AGILITY_BONUS = 3;

    private int lastAttackBonus = 0;
    private int lastAgiBonus = 0;

    private ShadowthornLongbow() {
        super(WEAPON_NAME, REQUIRED_STRENGTH, ATTACK_INCREASE, EFFECT, WEIGHT);
    }

    public static ShadowthornLongbow createShadowthornLongbow(Charecter character) {
        if (character == null) throw new IllegalArgumentException("Character cannot be null.");
        if (character.getGuild() != REQUIRED_GUILD)
            throw new IllegalArgumentException("Only Night Shade Hunters members can wield the ShadowthornLongbow.");
        if (character.getStrength() < REQUIRED_STRENGTH)
            throw new IllegalArgumentException("Character does not have the required strength to wield the ShadowthornLongbow.");
        if (character.getAgility() < REQUIRED_AGILITY)
            throw new IllegalArgumentException("Character does not have the required agility to wield the ShadowthornLongbow.");

        return new ShadowthornLongbow();
    }

    public boolean equip(Charecter wearer) {
        if (wearer == null) return false;
        if (wearer.getGuild() != REQUIRED_GUILD) return false;
        if (wearer.getStrength() < REQUIRED_STRENGTH) return false;
        if (wearer.getAgility() < REQUIRED_AGILITY) return false;

        lastAttackBonus = ATTACK_INCREASE;
        lastAgiBonus = AGILITY_BONUS;

        wearer.setAttack(wearer.getAttack() + lastAttackBonus);
        wearer.setAgility(wearer.getAgility() + lastAgiBonus);

        return true;
    }

    public boolean unequip(Charecter wearer) {
        if (wearer == null) return false;

        if (lastAttackBonus != 0) wearer.setAttack(Math.max(0, wearer.getAttack() - lastAttackBonus));
        if (lastAgiBonus != 0) wearer.setAgility(Math.max(0, wearer.getAgility() - lastAgiBonus));

        lastAttackBonus = 0;
        lastAgiBonus = 0;

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

    public int getRequiredAgility() {
        return REQUIRED_AGILITY;
    }

    public int getAttackIncrease() {
        return ATTACK_INCREASE;
    }

    public int getAgilityBonus() {
        return AGILITY_BONUS;
    }
}
