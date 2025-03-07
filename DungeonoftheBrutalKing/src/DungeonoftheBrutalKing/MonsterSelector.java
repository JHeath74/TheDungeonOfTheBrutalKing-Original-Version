package DungeonoftheBrutalKing;

import java.util.Random;
import Monsters.Assassin;
import Monsters.Brigands;
import Monsters.Cutthroats;

class MonsterSelector {

    // Method to select a random monster
    public static Object selectRandomMonster() {
        Random random = new Random();
        int index = random.nextInt(3);
        switch (index) {
            case 0:
                return new Assassin();
            case 1:
                return new Brigands();
            case 2:
                return new Cutthroats();
            default:
                return null;
        }
    }
}