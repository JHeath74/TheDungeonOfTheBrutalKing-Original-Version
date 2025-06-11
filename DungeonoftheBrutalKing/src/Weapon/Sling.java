
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.WeaponManager;

public class Sling extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private String weaponname = "Sling";
    private static double weight = 25.0;
    private static double damage = 35;
    private static final int REQUIRED_STRENGTH = 30;


    public Sling(double damage, String effect) {
        super("Sling", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Sling(int requiredStrength, double damage, String effect) {
        super("Sling", requiredStrength, damage, effect, weight);
    }

    public static Sling createSling(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Sling(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public int getRequiredStrength() {
        // Retrieve the strength from the Character class's CharInfo ArrayList at index 8 and parse as an int
        return REQUIRED_STRENGTH;
    }



    public double getDamage() {
        return damage;
    }


    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
