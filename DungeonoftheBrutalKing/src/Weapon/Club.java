
// src/Weapon/Club.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Club extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;
    private static int weight = 20;
    private static int damage = 20;

    public Club(int damage, String effect) {
        super("Club", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Club(int requiredStrength, int damage, String effect) {
        super("Club", requiredStrength, damage, effect, weight);
    }

    public static Club createClub(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new Club(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Club.");
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
        return damage;
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
