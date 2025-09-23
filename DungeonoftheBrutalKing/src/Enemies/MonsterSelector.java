
// src/Enemies/MonsterSelector.java
package Enemies;

import SharedData.GameSettings;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MonsterSelector {
    private static final List<Supplier<Enemies>> MONSTER_SUPPLIERS = List.of(
        () -> new Assassin(),
        () -> new Brigand(),
        () -> new Cutthroat(),
        () -> new Devourer(),
        () -> new Dragon(),
        () -> new Dwarves(),
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
        () -> new Homunculi(),
        () -> new Horned_Devil(),
        () -> new Ice_Demon(),
        () -> new Imp(),
        () -> new Knight(),
        () -> new Liches(),
        () -> new Mage(),
        () -> new Master_Thief(),
        () -> new Mold(),
        () -> new Night_Stalker(),
        () -> new Noblemen(),
        () -> new Orc(),
        () -> new Phoenix(),
        () -> new Rat(),
        () -> new Salamander(),
        () -> new Serpentmen(),
        () -> new Skeleton(),
        () -> new Slime(),
        () -> new Spectre(),
        () -> new Spider(),
        () -> new Storm_Devil(),
        () -> new Thief(),
        () -> new Troll(),
        () -> new Valkyrie(),
        () -> new Vampire(),
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
