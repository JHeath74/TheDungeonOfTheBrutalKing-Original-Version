
package Spells;

import java.util.Hashtable;

public class SpellList {

    static Hashtable<String, Spells> SpellList = new Hashtable<>();

    public SpellList() {
        SpellList.put("Cold Blast", new Spells());
        SpellList.put("Conjure Food", new Spells());
        SpellList.put("Fireball", new Spells());
        SpellList.put("Heal", new Spells());
        SpellList.put("Light", new Spells());
        SpellList.put("Location", new Spells());
        SpellList.put("Port", new Spells());
        SpellList.put("Shield", new Spells());
    }

    public static Spells getSpells(String Spellname) {
        return SpellList.get(Spellname);
    }
}
