package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.WeaponManager;

public class Club extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;
    private static double weight = 20.0;
    private static int damage = 20;
    private static String weaponName = "Club";

    public Club(double damage, String effect) {
        super("Club", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Club(int requiredStrength, double damage, String effect) {
        super("Club", requiredStrength, damage, effect, weight);
    }

    public static Club createClub(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Club(damage, effect);
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
