
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Shortbow extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 30;
    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 35;

    public Shortbow(Character character, String effect) {
        super("Shortbow", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
        // Optionally, check character's strength here if needed
    }

    public Shortbow(int requiredStrength, int damage, String effect) {
        super("Shortbow", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static Shortbow createShortbow(Character character, Character damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Shortbow(damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Shortbow.");
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
