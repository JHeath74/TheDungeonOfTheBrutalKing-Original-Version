
package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Crossbow extends WeaponManager {

	private static Charecter myChar = Charecter.getInstance();

    private static final int REQUIRED_STRENGTH = 15;
    private static int weight = 20;
    private static int damage = 20;

    public void setWeight(double weight) {
        Crossbow.weight = (int) weight;
    }

    @Override
    public double getWeight() {
        return (double) weight;
    }

    @Override
    public double getDamage() {
        return (double) damage;
    }

    public Crossbow(int damage, String effect) {
        super("Crossbow", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Crossbow(int requiredStrength, int damage, String effect) {
        super("Crossbow", requiredStrength, damage, effect, weight);
    }

    public static Crossbow createCrossbow(Charecter character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new Crossbow(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Crossbow.");
    }

    @Override
    public String getName() {
        return name;
    }

    public int getRequiredStrength() {
        return REQUIRED_STRENGTH;
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
