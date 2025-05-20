
package DungeonoftheBrutalKing;


import java.util.HashMap;
import java.util.Map;

public class Spells {

    private static Spells single_instance_spells;
    private Map<String, String> spellMap = new HashMap<>(); // Use a placeholder (e.g., String) for spell names

    public Spells() {
        
        initializeSpells();
    }

    public static Spells Singleton() {
        if (single_instance_spells == null) {
            single_instance_spells = new Spells();
        }
        return single_instance_spells;
    }

    private void initializeSpells() {
        // Add spell names to the map without creating new Spells instances
        spellMap.put("Cold Blast", "Cold Blast");
        spellMap.put("Conjure Food", "Conjure Food");
        spellMap.put("Cure", "Cure");
        spellMap.put("Fireball", "Fireball");
    }

    public void useSpell(String spellName) {
        String spell = spellMap.get(spellName);
        if (spell != null) {
     //       spell.cast();
            
        } else {
            System.out.println("Spell not found: " + spellName);
        }
    }


    
    
    
}






	

