
// src/Spells/SpellsManager.java
package Spells;

import java.util.HashMap;
import java.util.Map;

public class SpellsManager {
    private final Map<String, Spell> spells = new HashMap<>();

    public void registerSpell(Spell spell) {
        spells.put(spell.getName().toLowerCase(), spell);
    }

    public Spell getSpell(String name) {
        return spells.get(name.toLowerCase());
    }

    public Map<String, Spell> getAllSpells() {
        return new HashMap<>(spells);
    }

    public void castSpell(String name, int toonWisdom) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(toonWisdom);
        }
    }

    public void castSpellWithIntelligence(String name, int toonIntelligence) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.castWithIntelligence(toonIntelligence);
        }
    }

    public void castSpell(String name, int toonWisdom, int toonIntelligence) {
        Spell spell = getSpell(name);
        if (spell != null) {
            spell.cast(toonWisdom, toonIntelligence);
        }
    }
}
