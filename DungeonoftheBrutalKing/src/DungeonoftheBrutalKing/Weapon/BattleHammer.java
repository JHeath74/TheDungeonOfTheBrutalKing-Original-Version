
// src/DungeonoftheBrutalKing/Weapon/BattleHammer.java
package DungeonoftheBrutalKing.Weapon;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Status.StatusType;

public class BattleHammer extends WeaponManager {

    private static final int REQUIRED_STRENGTH = 15;
    private static final int DEFAULT_WEIGHT = 25;
    private static final int DEFAULT_DAMAGE = 25;

    public BattleHammer(int damage, String effect) {
        super("BattleHammer", REQUIRED_STRENGTH, damage, effect, DEFAULT_WEIGHT);
    }

    public BattleHammer(int requiredStrength, int damage, String effect) {
        super("BattleHammer", requiredStrength, damage, effect, DEFAULT_WEIGHT);
    }

    public static BattleHammer createBattleHammer(Character character, int damage, String effect) {
        int strength = Integer.parseInt(character.getCharInfo().get(8));
        if (strength >= REQUIRED_STRENGTH) {
            return new BattleHammer(damage, effect);
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the BattleHammer.");
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

    public StatusType getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
