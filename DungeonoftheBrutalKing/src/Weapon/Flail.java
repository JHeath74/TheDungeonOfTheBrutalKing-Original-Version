
package Weapon;

import DungeonoftheBrutalKing.Character;

public class Flail extends WeaponManager {

	private static Character myChar = Character.getInstance();

    private static final int REQUIRED_STRENGTH = 15;
    private static int weight = 20;
    private static int damage = 20;

    public Flail(int damage, String effect) {
        super("Flail", REQUIRED_STRENGTH, damage, effect, weight);
    }

    public Flail(int requiredStrength, int damage, String effect) {
        super("Flail", requiredStrength, damage, effect, weight);
    }

    public static Flail createLongbow(Character character, int damage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.getCharInfo().get(8));
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
