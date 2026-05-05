package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Status.StatusType;

public class Hand extends WeaponManager {

    private static final int DEFAULT_WEIGHT = 1;
    private static final int DEFAULT_DAMAGE = 3;
    private static final int REQUIRED_STRENGTH = 5;

    // Default constructor for starting weapon
    public Hand(DungeonoftheBrutalKing.Character owner, String effect) {
        super("Hand", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
        // Optionally, check owner's strength here and throw if not enough
    }

    // Constructor for custom hand weapons
    public Hand(int damage, String effect) {
        super("Hand", REQUIRED_STRENGTH, damage, effect, DEFAULT_WEIGHT);
    }

    @Override
    public String getName() {
        return super.getName();
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
