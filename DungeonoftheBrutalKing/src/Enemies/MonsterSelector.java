
// src/Enemies/MonsterSelector.java
package Enemies;

import SharedData.GameSettings;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MonsterSelector {
	private static final List<Supplier<Enemies>> MONSTER_SUPPLIERS = List.of(
	        () -> new Angel(),
	        () -> new Archon(),
	        () -> new Ascendant(),
	        () -> new Assassin(),
	        () -> new Banneret(),
	        () -> new Beacon(),
	        () -> new Brigand(),
	        () -> new Champion(),
	        () -> new Cleric(),
	        () -> new Crusader(),
	        () -> new Custodian(),
	        () -> new Cutthroat(),
	        () -> new Devourer(),
	        () -> new Dragon(),
	        () -> new Druid(),
	        () -> new Dwarves(),
	        () -> new Exemplar(),
	        () -> new Flame_Demon(),
	        () -> new Ghost(),
	        () -> new Ghoul(),
	        () -> new Giant_Bat(),
	        () -> new Gladiator(),
	        () -> new Gnoll(),
	        () -> new Gnome(),
	        () -> new Goblin(),
	        () -> new Gremlin(),
	        () -> new Guard(),
	        () -> new Guardian(),
	        () -> new Healer(),
	        () -> new Herald(),
	        () -> new Homunculi(),
	        () -> new Horned_Devil(),
	        () -> new Ice_Demon(),
	        () -> new Imp(),
	        () -> new Justicar(),
	        () -> new Knight(),
	        () -> new Liches(),
	        () -> new Lightbearer(),
	        () -> new Luminary(),
	        () -> new Mage(),
	        () -> new Master_Thief(),
	        () -> new Mold(),
	        () -> new Monk(),
	        () -> new Mystic(),
	        () -> new Necromancer(),
	        () -> new Night_Stalker(),
	        () -> new Noblemen(),
	        () -> new Oracle(),
	        () -> new Orc(),
	        () -> new Paladin(),
	        () -> new Paragon(),
	        () -> new Phoenix(),
	        () -> new Priest(),
	        () -> new Protector(),
	        () -> new Rat(),
	        () -> new Redeemer(),
	        () -> new Sage(),
	        () -> new Saint(),
	        () -> new Salamander(),
	        () -> new Seer(),
	        () -> new Sentinel(),
	        () -> new Seraph(),
	        () -> new Serpentmen(),
	        () -> new Skeleton(),
	        () -> new Slime(),
	        () -> new Spectre(),
	        () -> new Spider(),
	        () -> new Storm_Devil(),
	        () -> new Sunblade(),
	        () -> new Templar(),
	        () -> new Thief(),
	        () -> new Troll(),
	        () -> new Valkyrie(),
	        () -> new Vampire(),
	        () -> new Vindicator(),
	        () -> new Virtuoso(),
	        () -> new Warden(),
	        () -> new Whirlwind(),
	        () -> new Wizard(),
	        () -> new Wolf(),
	        () -> new Wraith()
	    );

    public static Enemies selectRandomMonster() {
        Random random = new Random();
        return MONSTER_SUPPLIERS.get(random.nextInt(MONSTER_SUPPLIERS.size())).get();
    }
}
