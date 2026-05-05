
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Whip extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 30;
    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 35;

    // Corrected: uses "Whip" instead of "WarNet"
    public Whip(Character owner, String effect) {
        super("Whip", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
    }

    // Added: constructor for Whip(String effect)
    public Whip(String effect) {
        super("Whip", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
    }

    public Whip(int requiredStrength, int damage, String effect) {
        super("Whip", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static Whip createWhip(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Whip(REQUIRED_STRENGTH, damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Whip.");
    }

    public static Whip createDefaultWhip(Character character, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Whip(effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Whip.");
    }

    @Override
    public String getName() {
        return name;
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }

    @Override
    public double getDamage() {
        return super.getDamage();
    }

    @Override
    public double getWeight() {
        return super.getWeight();
    }

    public StatusType getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
