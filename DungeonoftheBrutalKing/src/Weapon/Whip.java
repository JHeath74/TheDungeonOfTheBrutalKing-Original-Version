package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Whip extends WeaponManager {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 30;
    private static int weight = 25;
    private static int damage = 35;

    public Whip(int damage, String effect) {
        super("Whip", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Whip(int requiredStrength, int damage, String effect) {
        super("Whip", requiredStrength, damage, effect, weight);
    }

    public static Whip createWhip(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new Whip(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Whip.");
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
        return (double) damage;
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    @Override
    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
