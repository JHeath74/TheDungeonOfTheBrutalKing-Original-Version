
package DungeonoftheBrutalKing;

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
    }

    public void useSpell(String spellName) {
        Integer damage = spellDamageMap.get(spellName);
        if (damage != null) {
            System.out.println("Using spell: " + spellName + " with damage: " + damage);
        } else {
            System.out.println("Spell not found: " + spellName);
        }
    }

    public String getName() {
        return spellName;
    }

    public int getDamage() {
        return spellDamageMap.getOrDefault(spellName, 0);
    }

	public boolean isGuildSpell() {
		// TODO Auto-generated method stub
		return false;
	}
}
