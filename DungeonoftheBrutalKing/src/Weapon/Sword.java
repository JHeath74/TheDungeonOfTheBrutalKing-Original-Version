
// src/Weapon/Sword.java
package Weapon;

import DungeonoftheBrutalKing.Character;

public class Sword extends WeaponManager {

	private static Character myChar = Character.getInstance();

    private static int weight = 25;
    private static int damage = 35;
    private static final int REQUIRED_STRENGTH = 30;

    public Sword(int damage, String effect) {
        super("Sword", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Sword(int requiredStrength, int damage, String effect) {
        super("Sword", requiredStrength, damage, effect, weight);
    }

    public static Sword createSword(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new Sword(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Sword.");
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
