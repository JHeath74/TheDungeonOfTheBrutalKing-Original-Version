
// src/Weapon/Longbow.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Longbow extends WeaponManager {

	private static Charecter myChar = Charecter.getInstance();

    private static int weight = 25;
    private static int damage = 35;
    private static final int REQUIRED_STRENGTH = 30;

    public Longbow(int damage, String effect) {
        super("Long bow", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Longbow(int requiredStrength, int damage, String effect) {
        super("Long bow", requiredStrength, damage, effect, weight);
    }

    public static Longbow createLongbow(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new Longbow(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Longbow.");
    }

    @Override
    public String getName() {
        return name;
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }

    public double getAttackDamage() {
        return (double) damage;
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
