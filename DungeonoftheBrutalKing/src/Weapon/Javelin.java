package Weapon;

import DungeonoftheBrutalKing.Character;

public class Javelin extends WeaponManager {

	private static Character myChar = Character.getInstance();

    private static int weight = 25;
    private static int damage = 35;
    private static final int REQUIRED_STRENGTH = 30;

    public Javelin(int damage, String effect) {
        super("Javelin", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Javelin(int requiredStrength, int damage, String effect) {
        super("Javelin", requiredStrength, damage, effect, weight);
    }

    public static Javelin createLongbow(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
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
