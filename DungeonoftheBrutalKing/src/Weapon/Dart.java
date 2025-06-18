
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.WeaponManager;

public class Dart extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;
    private static double weight = 20.0;
	private static int damage = 20;
	private static String weaponName = "Dart";

    public Dart(double attackDamage, String effect) {
        super("Dart", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Dart(int requiredStrength, double damage, String effect) {
        super("Dart", requiredStrength, damage, effect, weight);
    }

    public static Dart createDart(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Dart(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    // Getters and Setters
    @Override
	public String getName() {
        return name;
    }

    public int getRequiredStrength() {
        // Retrieve the strength from the Character class's CharInfo ArrayList at index 8 and parse as an int
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
