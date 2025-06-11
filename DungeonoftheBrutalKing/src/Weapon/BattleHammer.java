
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.WeaponManager;

public class BattleHammer extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

	private static double weight = 25.0;
	private static double damage = 25.0;
	private static String weaponName = "Battle Hammer";

    private static final int REQUIRED_STRENGTH = 15;

    public BattleHammer(double damage, String effect) {
        super("Battle Axe", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public BattleHammer(int requiredStrength, double damage, String effect) {
        super("BattleHammer", requiredStrength, damage, effect, weight );
    }

    public static BattleHammer createBattleHammer(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new BattleHammer(damage, effect);
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
