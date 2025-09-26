package Weapon;

import DungeonoftheBrutalKing.Charecter;

public class Hand extends WeaponManager {

	private static Charecter myChar = Charecter.getInstance();

    private static int weight = 25;
    private static int damage = 35;
    private static final int REQUIRED_STRENGTH = 30;

    public Hand(int damage, String effect) {
        super("Hand", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Hand(int requiredStrength, int damage, String effect) {
        super("Hand", requiredStrength, damage, effect, weight);
    }

    public static Hand createHand(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
            if (strength >= requiredStrength) {
                return new Hand(damage, effect);
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
