
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.WeaponManager;

public class Shortbow extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private String weaponname = "Shortbow";
    private static double weight = 25.0;
    private static double damage = 35;
    private static final int REQUIRED_STRENGTH = 30;


    public Shortbow(double damage, String effect) {
        super("Shortbow", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Shortbow(int requiredStrength, double damage, String effect) {
        super("Shortbow", requiredStrength, damage, effect, weight);
    }

    public static Shortbow createShortbow(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Shortbow(damage, effect);
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
