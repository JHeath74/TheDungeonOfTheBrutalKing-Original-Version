
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Dart extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 15;
    private static final int DEFAULT_WEIGHT = 20;
    private static final int DEFAULT_DAMAGE = 20;

    public Dart(int damage, String effect) {
        super("Dart", REQUIRED_STRENGTH, damage, effect, DEFAULT_WEIGHT);
    }

    public Dart(int requiredStrength, int damage, String effect) {
        super("Dart", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static Dart createDart(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Dart(damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Dart.");
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
