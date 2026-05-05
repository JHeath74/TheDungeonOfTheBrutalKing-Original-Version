
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class WarNet extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 30;
    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 35;

    // Constructor using DEFAULT_DAMAGE
    public WarNet(Character owner, String effect) {
        super("WarNet", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
    }

    public WarNet(int requiredStrength, int damage, String effect) {
        super("WarNet", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    // Constructor using DEFAULT_DAMAGE
    public WarNet(String effect) {
        super("WarNet", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
    }

    public static WarNet createWarNet(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new WarNet(REQUIRED_STRENGTH, damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the WarNet.");
    }

    // Factory for default WarNet
    public static WarNet createDefaultWarNet(Character character, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new WarNet(effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the WarNet.");
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
