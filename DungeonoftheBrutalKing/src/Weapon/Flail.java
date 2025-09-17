
// Longbow.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Flail extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;
    private static double weight = 20.0;
	private static int damage = 20;
	private static String weaponName = "Flail";

    public Flail(double damage, String effect) {
        super("Flail", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Flail(int requiredStrength, double damage, String effect) {
        super("Flail", requiredStrength, damage, effect, weight);
    }

    public static Flail createLongbow(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Flail(damage, effect);
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



    public int getAttackDamage() {
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
