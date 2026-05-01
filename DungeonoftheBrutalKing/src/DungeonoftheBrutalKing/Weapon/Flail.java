
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Flail extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 15;
    private static final int DEFAULT_WEIGHT = 20;
    private static final int DEFAULT_DAMAGE = 20;

    public Flail(int damage, String effect) {
        super("Flail", REQUIRED_STRENGTH, damage, effect, DEFAULT_WEIGHT);
    }

    public Flail(int requiredStrength, int damage, String effect) {
        super("Flail", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static Flail createFlail(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Flail(damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Flail.");
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
