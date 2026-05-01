
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class BattleAxe extends WeaponManager {

    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 35;
    private static final int REQUIRED_STRENGTH = 30;

    public BattleAxe(Character owner, String effect) {
        super("Battle Axe", REQUIRED_STRENGTH, DEFAULT_DAMAGE, effect, DEFAULT_WEIGHT);
        // Optionally, check owner's strength here and throw if not enough
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }

    @Override
    public double getWeight() {
        return DEFAULT_WEIGHT;
    }

    @Override
    public double getDamage() {
        return DEFAULT_DAMAGE;
    }

    public StatusType getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
