
package Spells;

import java.util.HashMap;
import java.util.Map;

public class Spells {

    private static Spells single_instance_spells;
    private Map<String, Integer> spellDamageMap = new HashMap<>();
    private static String spellName;

    public Spells() {
        // Default constructor for compatibility
    }

    public Spells(String spellName) {
        initializeSpells();
        Spells.spellName = spellName;
    }

    public static Spells Singleton() {
        if (single_instance_spells == null) {
            single_instance_spells = new Spells(spellName);
        }
        return single_instance_spells;
    }


private void initializeSpells() {
    spellDamageMap.put("Cold Blast", 25);
    spellDamageMap.put("Conjure Food", 0);
    spellDamageMap.put("Cure", 0);
    spellDamageMap.put("Fireball", 50);
    spellDamageMap.put("Heal", 0);
    spellDamageMap.put("Light", 0);
    spellDamageMap.put("Location", 0);
    spellDamageMap.put("Port", 0);
    spellDamageMap.put("Shield", 0);
}



public void useSpell(String spellName, int level, int intelligence, int wisdom) {
    Integer baseDamage = spellDamageMap.get(spellName);
    if (baseDamage != null) {
        int finalDamage = calculateDamage(baseDamage, level, intelligence, wisdom);
        System.out.println("Using spell: " + spellName + " with damage: " + finalDamage);
    } else {
        System.out.println("Spell not found: " + spellName);
    }
}

private int calculateDamage(int baseDamage, int level, int intelligence, int wisdom) {
    // Example formula: baseDamage + (level * 2) + (intelligence * 1) + (wisdom * 1)
    return baseDamage + (level * 2) + intelligence + wisdom;
}


    public String getName() {
        return spellName;
    }

    public int getDamage() {
        return spellDamageMap.getOrDefault(spellName, 0);
    }

}
