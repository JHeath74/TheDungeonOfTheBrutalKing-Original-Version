
// Longbow.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.WeaponManager;

public class Longbow extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private String weaponname = "Longbow";
    private static double weight = 25.0;
    private static double damage = 35;
    private static final int REQUIRED_STRENGTH = 30;

    public Longbow(double damage, String effect) {
        super("Long bow", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Longbow(int requiredStrength, double damage, String effect) {
        super("Long bow", requiredStrength, damage, effect, weight);
    }

    public static Longbow createLongbow(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Longbow(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Longbow.");
    }

    public String getName() {
        return name;
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }


    public double getAttackDamage() {
        return damage;
    }


    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
