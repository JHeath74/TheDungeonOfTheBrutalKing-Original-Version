
// src/Weapon/WarNet.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class WarNet extends WeaponManager {

    private static final int WEIGHT = 25;
    private static final int REQUIRED_STRENGTH = 30;

    public WarNet(int damage, String effect) {
        super("WarNet", REQUIRED_STRENGTH, damage, effect, WEIGHT);
    }

    public WarNet(int requiredStrength, int damage, String effect) {
        super("WarNet", requiredStrength, damage, effect, WEIGHT);
    }

    public static WarNet createWarNet(Charecter character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(character.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new WarNet(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
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

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
