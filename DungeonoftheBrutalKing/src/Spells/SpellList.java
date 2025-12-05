
// src/Spells/SpellList.java
package Spells;

import java.util.Hashtable;

public class SpellList {

    private static final Hashtable<String, Spell> spellList = new Hashtable<>();

    public SpellList() {
        spellList.put("Cold Blast", new Cold_Blast());
        spellList.put("Conjure Food", new Conjure_Food());
        spellList.put("Fireball", new Fireball());
        spellList.put("Heal", new Heal());
        spellList.put("Light", new Light());
        spellList.put("Location", new Location());
        spellList.put("Port", new Port());
        spellList.put("Shield", new Shield());
        // Add other spells here as needed
    }

    public static Spell getSpell(String spellName) {
        return spellList.get(spellName);
    }
}
