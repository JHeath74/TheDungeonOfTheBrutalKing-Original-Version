
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
   SpellList.put("Cold Blast", new Spells());
   SpellList.put("Conjure Food", new Spells());
   SpellList.put("Fireball", new Spells());
   SpellList.put("Heal", new Spells());
   SpellList.put("Light", new Spells());
   SpellList.put("Location", new Spells());
   SpellList.put("Port", new Spells());

   SpellList.put("Shield", new Spells());
  }

  public static Spells getSpells(String Spellname)
  {
   return SpellList.get(Spellname);
  }
}
