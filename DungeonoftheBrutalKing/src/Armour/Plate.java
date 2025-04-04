
// src/Armour/BreastPlate.java
package Armour;

import DungeonoftheBrutalKing.Armour;
import DungeonoftheBrutalKing.Charecter;


public class Plate extends Armour {

    private static Charecter myChar = Charecter.Singleton();
    private static int armourDefense;
    private static final int REQUIRED_STRENGTH = 15;

    public Plate(String effect) {
        super("Plate", REQUIRED_STRENGTH, Plate.armourDefense, effect);
        Plate.armourDefense = 15;
    }

    public Plate(int requiredStrength, int armourDefense, String effect) {
        super("Plate", requiredStrength, Plate.armourDefense, effect);
        Plate.armourDefense = 15;
    }

    public static Plate createPlate(Character character, int REQUIRED_STRENGTH, int armourDefense, String effect) throws NumberFormatException {
    	 int requiredStrength = REQUIRED_STRENGTH;
         try {
             int strength = Integer.parseInt(myChar.CharInfo.get(8));
             if (strength >= requiredStrength) {
                 return new Plate(armourDefense, requiredStrength, effect);
             }
         } catch (NumberFormatException e) {
             e.printStackTrace();
         }
         throw new IllegalArgumentException("Character does not have the required strength to wield the Battle Axe.");
    }

    public String getName() {
        return super.getName();
    }

    public int getRequiredStrength() {
        return Integer.parseInt(myChar.CharInfo.get(8));
    }

    public int getArmourDefense() {
        return super.getArmourDefense();
    }

    public StatusEffect getEffect() {
        return super.getStatusEffect();
    }

    public void setEffect(String effect) {
        super.setEffect(effect);
    }
}
