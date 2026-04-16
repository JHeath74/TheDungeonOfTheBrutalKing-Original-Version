
// File: `src/DungeonoftheBrutalKing/Enemies/MonsterSelector.java`
package DungeonoftheBrutalKing.Enemies;

import DungeonoftheBrutalKing.SharedData.Alignment;
import DungeonoftheBrutalKing.SharedData.RandomFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public class MonsterSelector {

    private static final List<Supplier<Enemies>> MONSTER_SUPPLIERS = Arrays.asList(
        Angel::new,
        Archon::new,
        Ascendant::new,
        Assassin::new,
        Banneret::new,
        Beacon::new,
        Brigand::new,
        Champion::new,
        Cleric::new,
        Crusader::new,
        Custodian::new,
        Cutthroat::new,
        Devourer::new,
        Dragon::new,
        Druid::new,
        Dwarves::new,
        Exemplar::new,
        Flame_Demon::new,
        Ghost::new,
        Ghoul::new,
        Giant_Bat::new,
        Gladiator::new,
        Gnoll::new,
        Gnome::new,
        Goblin::new,
        Gremlin::new,
        Guard::new,
        Guardian::new,
        Healer::new,
        Herald::new,
        Homunculi::new,
        Horned_Devil::new,
        Ice_Demon::new,
        Imp::new,
        Justicar::new,
        Knight::new,
        Liches::new,
        Lightbearer::new,
        Luminary::new,
        Mage::new,
        Master_Thief::new,
        Mold::new,
        Monk::new,
        Mystic::new,
        Necromancer::new,
        Night_Stalker::new,
        Noblemen::new,
        Oracle::new,
        Orc::new,
        Paladin::new,
        Paragon::new,
        Phoenix::new,
        Priest::new,
        Protector::new,
        Rat::new,
        Redeemer::new,
        Sage::new,
        Saint::new,
        Salamander::new,
        Seer::new,
        Sentinel::new,
        Seraph::new,
        Serpentmen::new,
        Skeleton::new,
        Slime::new,
        Spectre::new,
        Spider::new,
        Storm_Devil::new,
        Sunblade::new,
        Templar::new,
        Thief::new,
        Troll::new,
        Valkyrie::new,
        Vampire::new,
        Vindicator::new,
        Virtuoso::new,
        Warden::new,
        Whirlwind::new,
        Wizard::new,
        Wolf::new,
        Wraith::new
    );

    private static final List<Supplier<Enemies>> GOOD_SUPPLIERS = filterByAlignment(Alignment.GOOD);
    private static final List<Supplier<Enemies>> EVIL_SUPPLIERS = filterByAlignment(Alignment.EVIL);

    public static Enemies selectRandomMonster() {
        return MONSTER_SUPPLIERS.get(RandomFactory.gameplayInt(MONSTER_SUPPLIERS.size())).get();
    }

    public static List<Enemies> generateEnemies() {
        List<Enemies> enemies = new ArrayList<>();

        List<IntFunction<Enemies>> goodFactories = wrapLevel(GOOD_SUPPLIERS);
        List<IntFunction<Enemies>> evilFactories = wrapLevel(EVIL_SUPPLIERS);

        for (int bracket = 0; bracket < 10; bracket++) {
            int minLevel = bracket * 5 + 1;
            int maxLevel = minLevel + 4;

            for (int i = 0; i < 4; i++) {
                int level = minLevel + RandomFactory.gameplayInt(maxLevel - minLevel + 1);
                Enemies goodEnemy = goodFactories.get(RandomFactory.gameplayInt(goodFactories.size())).apply(level);
                enemies.add(goodEnemy);
            }

            for (int i = 0; i < 4; i++) {
                int level = minLevel + RandomFactory.gameplayInt(maxLevel - minLevel + 1);
                Enemies evilEnemy = evilFactories.get(RandomFactory.gameplayInt(evilFactories.size())).apply(level);
                enemies.add(evilEnemy);
            }
        }

        return enemies;
    }

    private static List<Supplier<Enemies>> filterByAlignment(Alignment alignment) {
        List<Supplier<Enemies>> result = new ArrayList<>();
        for (Supplier<Enemies> supplier : MONSTER_SUPPLIERS) {
            Enemies sample = supplier.get();
            if (sample != null && sample.getAlignment() == alignment) {
                result.add(supplier);
            }
        }
        return result;
    }

    private static List<IntFunction<Enemies>> wrapLevel(List<Supplier<Enemies>> suppliers) {
        List<IntFunction<Enemies>> factories = new ArrayList<>(suppliers.size());
        for (Supplier<Enemies> supplier : suppliers) {
            factories.add(level -> {
                Enemies e = supplier.get();
                e.setLevel(level);
                return e;
            });
        }
        return factories;
    }
}
