package DungeonoftheBrutalKing;

import java.util.ArrayList;

public class Armour { 
	
public ArrayList myArmour = new ArrayList<>();
private static Armour single_instance_armour;

protected Singleton myChar = new Singleton();

public String name;
public static int armourDefense;
public int requiredStrength;
public static String charStrength;
public ArrayList<StatusEffect> effects = new ArrayList<>();

public enum StatusEffect {
    NONE,
    POISON,
    STUN,
    BLEED,
    FIRE,
    COLD,
    DEFENSE,
}

public static Armour Singleton() {
    if (single_instance_armour == null) {
        single_instance_armour = new Armour(charStrength, armourDefense, armourDefense);
    }
    return single_instance_armour;
}

public Armour(String name, int armourDefense, int requiredStrength) {
    this.name = name;
    this.armourDefense = armourDefense;
    this.requiredStrength = requiredStrength;
}

public void addEffect(StatusEffect effect) {
    if (!effects.contains(effect)) {
        effects.add(effect);
    }
}

public void removeEffect(StatusEffect effect) {
    effects.remove(effect);
}

public ArrayList<StatusEffect> getEffects() {
    return effects;
}
}