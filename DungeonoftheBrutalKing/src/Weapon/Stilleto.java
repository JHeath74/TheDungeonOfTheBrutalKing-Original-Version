
package Weapon;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Weapons;

public class Stilleto extends Weapons {

    private static Charecter myChar = Charecter.Singleton();

    private static final int REQUIRED_STRENGTH = 15;

    public Stilleto(int attackDamage, String effect) {
        super("Stilleto", REQUIRED_STRENGTH, attackDamage, effect);
    }

    public Stilleto(int requiredStrength, int attackDamage, String effect) {
        super("Stilleto", requiredStrength, attackDamage, effect);
    }

    public static Stilleto createStilleto(Character character, int attackDamage, String effect) throws NumberFormatException {
        int requiredStrength = REQUIRED_STRENGTH;
        try {
            int strength = Integer.parseInt(myChar.CharInfo.get(8));
            if (strength >= requiredStrength) {
                return new Stilleto(attackDamage, effect);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    // Getters and Setters
    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        // Retrieve the strength from the Character class's CharInfo ArrayList at index 8 and parse as an int
        return REQUIRED_STRENGTH;
    }

    public void setRequiredStrength(int requiredStrength) {
        super.setRequiredStrength(requiredStrength);
    }

    public int getAttackDamage() {
        return super.getAttackDamage();
    }

    public void setAttackDamage(int attackDamage) {
        super.setAttackDamage(attackDamage);
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
