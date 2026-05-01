package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Sling extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 30;
    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 35;

    public Sling(int damage, String effect) {
        super("Sling", REQUIRED_STRENGTH, damage, effect, DEFAULT_WEIGHT);
    }

    public Sling(int requiredStrength, int damage, String effect) {
        super("Sling", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static Sling createSling(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Sling(damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Sling.");
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
