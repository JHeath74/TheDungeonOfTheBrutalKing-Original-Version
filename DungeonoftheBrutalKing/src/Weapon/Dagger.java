
package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Dagger extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;
    private static double weight = 20.0;
	private static int damage = 20;
	private static String weaponName = "Dagger";

    public Dagger(double damage, String effect) {
        super("Dagger", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Dagger(int requiredStrength, double damage, String effect) {
        super("Dagger", requiredStrength, damage, effect, weight);
    }

    public static Dagger createDagger(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Dagger(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    // Getters and Setters
    @Override
	public String getName() {
        return super.getName();
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
