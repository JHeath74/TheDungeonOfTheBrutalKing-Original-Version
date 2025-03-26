
package DungeonoftheBrutalKing;

import java.util.Hashtable;

import Spells.Cold_Blast;
import Spells.Conjure_Food;
import Spells.Fireball;
import Spells.Heal;
import Spells.Light;
import Spells.Location;
import Spells.Port;
import Spells.Shield;

public class SpellList {

 static Hashtable<String, Spells> SpellList = new Hashtable<>();

  public SpellList()
  {
   SpellList.put("Cold Blast", new Cold_Blast());
   SpellList.put("Conjure Food", new Conjure_Food());
   SpellList.put("Fireball", new Fireball());
   SpellList.put("Heal", new Heal());
   SpellList.put("Light", new Light());
   SpellList.put("Location", new Location());
   SpellList.put("Port", new Port());

   SpellList.put("Shield", new Shield());
  }

  public static Spells getSpells(String Spellname)
  {
   return SpellList.get(Spellname);
  }
}
