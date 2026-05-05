
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class Javelin extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 30;
    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 35;

    // Default constructor using DEFAULT_DAMAGE
    public Javelin(String effect) {
        super("Javelin", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
    }

    public Javelin(Character owner, String effect) {
        super("Javelin", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
        // If you need to store the owner, add: this.owner = owner;
    }

    // Added constructor for (int damage, String effect)
    public Javelin(int damage, String effect) {
        super("Javelin", REQUIRED_STRENGTH, damage, effect, DEFAULT_WEIGHT);
    }

    public Javelin(int requiredStrength, int damage, String effect) {
        super("Javelin", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static Javelin createJavelin(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Javelin(damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Javelin.");
    }

    public static Javelin createDefaultJavelin(Character character, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new Javelin(effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Javelin.");
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
