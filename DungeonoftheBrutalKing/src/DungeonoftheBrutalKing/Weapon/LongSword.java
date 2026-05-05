package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class LongSword extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 30;
    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 35;

    // Default constructor using DEFAULT_DAMAGE
    public LongSword(String effect) {
        super("Long Sword", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
    }


public LongSword(Character character, String effect) {
    super("Long Sword", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
    // Optionally, check character's strength here if needed
}


    public LongSword(int requiredStrength, int damage, String effect) {
        super("Long Sword", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static LongSword createLongSword(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new LongSword(character,effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Long Sword.");
    }

    // Factory for default LongSword
    public static LongSword createDefaultLongSword(Character character, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new LongSword(effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Long Sword.");
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
