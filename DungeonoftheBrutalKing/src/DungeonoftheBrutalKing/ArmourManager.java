
// src/Armour/Armour.java
package DungeonoftheBrutalKing;

import java.util.ArrayList;
import java.util.List;

public class ArmourManager {

    protected Singleton myChar = new Singleton();

    public String name;
    protected int requiredStrength;

	private String effect;
    public static String charStrength;
    private static StatusEffect statusEffect;
    private static int armourDefense;
    
 // Static list to store all shields
    protected static List<ArmourManager> allArmour = new ArrayList<>();

    public ArmourManager(String name, int requiredStrength,int armourDefense, String effect) {
        this.name = name;
        this.requiredStrength = requiredStrength;
        this.armourDefense = armourDefense;
        this.effect = effect;
        allArmour.add(this);
        

        if (effect != null) {
            ArmourManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            ArmourManager.statusEffect = StatusEffect.NONE;
        }
    }

    public enum StatusEffect {
        NONE,
        POISON,
        STUN,
        BLEED,
        FIRE,
        COLD,
    }

    public String getName() {
        return name;
    }

    public StatusEffect getStatusEffect() {
        return statusEffect;
    }

    public void setRequiredStrength(int requiredStrength) {
        this.requiredStrength = requiredStrength;
    }
    
    public static List<ArmourManager> getAllShields() {
        return allArmour;
    }

    public void setEffect(String effect) {
        if (effect != null) {
            ArmourManager.statusEffect = StatusEffect.valueOf(effect.toUpperCase());
        } else {
            ArmourManager.statusEffect = StatusEffect.NONE;
        }
    }


    public int getArmourDefense() {
        return armourDefense;
    }

    public static ArmourManager Singleton() {
        // TODO Auto-generated method stub
        return null;
    }
}
