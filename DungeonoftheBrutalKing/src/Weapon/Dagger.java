package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Dagger extends WeaponManager {

	private static Charecter myChar = Charecter.getInstance();

    private static final int REQUIRED_STRENGTH = 15;
    private static int weight = 20;
    private static int damage = 20;

    public Dagger(int damage, String effect) {
        super("Dagger", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Dagger(int requiredStrength, int damage, String effect) {
        super("Dagger", requiredStrength, damage, effect, weight);
    }

    public static Dagger createDagger(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new Dagger(damage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    @Override
    public String getName() {
        return super.getName();
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
