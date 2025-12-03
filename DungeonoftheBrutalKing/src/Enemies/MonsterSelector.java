
package Enemies;

import SharedData.GameSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class MonsterSelector {
    private static final List<Supplier<Enemies>> MONSTER_SUPPLIERS = Arrays.asList(
        (Supplier<Enemies>) () -> new Angel(),
        (Supplier<Enemies>) () -> new Archon(),
        (Supplier<Enemies>) () -> new Ascendant(),
        (Supplier<Enemies>) () -> new Assassin(),
        (Supplier<Enemies>) () -> new Banneret(),
        (Supplier<Enemies>) () -> new Beacon(),
        (Supplier<Enemies>) () -> new Brigand(),
        (Supplier<Enemies>) () -> new Champion(),
        (Supplier<Enemies>) () -> new Cleric(),
        (Supplier<Enemies>) () -> new Crusader(),
        (Supplier<Enemies>) () -> new Custodian(),
        (Supplier<Enemies>) () -> new Cutthroat(),
        (Supplier<Enemies>) () -> new Devourer(),
        (Supplier<Enemies>) () -> new Dragon(),
        (Supplier<Enemies>) () -> new Druid(),
        (Supplier<Enemies>) () -> new Dwarves(),
        (Supplier<Enemies>) () -> new Exemplar(),
        (Supplier<Enemies>) () -> new Flame_Demon(),
        (Supplier<Enemies>) () -> new Ghost(),
        (Supplier<Enemies>) () -> new Ghoul(),
        (Supplier<Enemies>) () -> new Giant_Bat(),
        (Supplier<Enemies>) () -> new Gladiator(),
        (Supplier<Enemies>) () -> new Gnoll(),
        (Supplier<Enemies>) () -> new Gnome(),
        (Supplier<Enemies>) () -> new Goblin(),
        (Supplier<Enemies>) () -> new Gremlin(),
        (Supplier<Enemies>) () -> new Guard(),
        (Supplier<Enemies>) () -> new Guardian(),
        (Supplier<Enemies>) () -> new Healer(),
        (Supplier<Enemies>) () -> new Herald(),
        (Supplier<Enemies>) () -> new Homunculi(),
        (Supplier<Enemies>) () -> new Horned_Devil(),
        (Supplier<Enemies>) () -> new Ice_Demon(),
        (Supplier<Enemies>) () -> new Imp(),
        (Supplier<Enemies>) () -> new Justicar(),
        (Supplier<Enemies>) () -> new Knight(),
        (Supplier<Enemies>) () -> new Liches(),
        (Supplier<Enemies>) () -> new Lightbearer(),
        (Supplier<Enemies>) () -> new Luminary(),
        (Supplier<Enemies>) () -> new Mage(),
        (Supplier<Enemies>) () -> new Master_Thief(),
        (Supplier<Enemies>) () -> new Mold(),
        (Supplier<Enemies>) () -> new Monk(),
        (Supplier<Enemies>) () -> new Mystic(),
        (Supplier<Enemies>) () -> new Necromancer(),
        (Supplier<Enemies>) () -> new Night_Stalker(),
        (Supplier<Enemies>) () -> new Noblemen(),
        (Supplier<Enemies>) () -> new Oracle(),
        (Supplier<Enemies>) () -> new Orc(),
        (Supplier<Enemies>) () -> new Paladin(),
        (Supplier<Enemies>) () -> new Paragon(),
        (Supplier<Enemies>) () -> new Phoenix(),
        (Supplier<Enemies>) () -> new Priest(),
        (Supplier<Enemies>) () -> new Protector(),
        (Supplier<Enemies>) () -> new Rat(),
        (Supplier<Enemies>) () -> new Redeemer(),
        (Supplier<Enemies>) () -> new Sage(),
        (Supplier<Enemies>) () -> new Saint(),
        (Supplier<Enemies>) () -> new Salamander(),
        (Supplier<Enemies>) () -> new Seer(),
        (Supplier<Enemies>) () -> new Sentinel(),
        (Supplier<Enemies>) () -> new Seraph(),
        (Supplier<Enemies>) () -> new Serpentmen(),
        (Supplier<Enemies>) () -> new Skeleton(),
        (Supplier<Enemies>) () -> new Slime(),
        (Supplier<Enemies>) () -> new Spectre(),
        (Supplier<Enemies>) () -> new Spider(),
        (Supplier<Enemies>) () -> new Storm_Devil(),
        (Supplier<Enemies>) () -> new Sunblade(),
        (Supplier<Enemies>) () -> new Templar(),
        (Supplier<Enemies>) () -> new Thief(),
        (Supplier<Enemies>) () -> new Troll(),
        (Supplier<Enemies>) () -> new Valkyrie(),
        (Supplier<Enemies>) () -> new Vampire(),
        (Supplier<Enemies>) () -> new Vindicator(),
        (Supplier<Enemies>) () -> new Virtuoso(),
        (Supplier<Enemies>) () -> new Warden(),
        (Supplier<Enemies>) () -> new Whirlwind(),
        (Supplier<Enemies>) () -> new Wizard(),
        (Supplier<Enemies>) () -> new Wolf(),
        (Supplier<Enemies>) () -> new Wraith()
    );

    public static Enemies selectRandomMonster() {
        Random random = new Random();
        return MONSTER_SUPPLIERS.get(random.nextInt(MONSTER_SUPPLIERS.size())).get();
    }

    public static List<Enemies> generateEnemies() {
        List<Enemies> enemies = new ArrayList<>();
        Random rand = new Random();

        List<Supplier<Enemies>> goodSuppliers = Arrays.asList(
            (Supplier<Enemies>) () -> new Angel(),
            (Supplier<Enemies>) () -> new Archon(),
            (Supplier<Enemies>) () -> new Saint(),
            (Supplier<Enemies>) () -> new Seraph()
        );
        List<Supplier<Enemies>> evilSuppliers = Arrays.asList(
            (Supplier<Enemies>) () -> new Dragon(),
            (Supplier<Enemies>) () -> new Vampire(),
            (Supplier<Enemies>) () -> new Ghoul(),
            (Supplier<Enemies>) () -> new Flame_Demon()
        );

        for (int bracket = 0; bracket < 10; bracket++) {
            int minLevel = bracket * 5 + 1;
            int maxLevel = minLevel + 4;

            for (int i = 0; i < 4; i++) {
                Supplier<Enemies> goodSupplier = goodSuppliers.get(rand.nextInt(goodSuppliers.size()));
                Enemies goodEnemy = goodSupplier.get();
                goodEnemy.setLevel(rand.nextInt(maxLevel - minLevel + 1) + minLevel);
                enemies.add(goodEnemy);
            }
            for (int i = 0; i < 4; i++) {
                Supplier<Enemies> evilSupplier = evilSuppliers.get(rand.nextInt(evilSuppliers.size()));
                Enemies evilEnemy = evilSupplier.get();
                evilEnemy.setLevel(rand.nextInt(maxLevel - minLevel + 1) + minLevel);
                enemies.add(evilEnemy);
            }
        }
        return enemies;
    }
}
