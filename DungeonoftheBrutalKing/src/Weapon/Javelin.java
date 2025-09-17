
// Longbow.java
package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Javelin extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private String weaponname = "Javelin";
    private static double weight = 25.0;
    private static double damage = 35;
    private static final int REQUIRED_STRENGTH = 30;

    public Javelin(double damage, String effect) {
        super("Javelin", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Javelin(int requiredStrength, double damage, String effect) {
        super("Javelin", requiredStrength, damage, effect, weight);
    }

    public static Javelin createLongbow(Character character, double damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Javelin(damage, effect);
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
