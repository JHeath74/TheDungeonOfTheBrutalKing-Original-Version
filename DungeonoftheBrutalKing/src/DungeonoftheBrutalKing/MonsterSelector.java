package DungeonoftheBrutalKing;

import java.util.Random;
import Monsters.Assassin;
import Monsters.Brigands;
import Monsters.Cutthroats;
import Monsters.Devourer;
import Monsters.Dragon;
import Monsters.Dwarves;
import Monsters.Flame_Demons;
import Monsters.Ghost;
import Monsters.Ghoul;
import Monsters.Giant_Bat;
import Monsters.Gladiator;
import Monsters.Gnoll;
import Monsters.Gnome;
import Monsters.Goblin;
import Monsters.Gremlin;
import Monsters.Guard;
import Monsters.Homunculi;
import Monsters.Horned_Devil;
import Monsters.Ice_Demon;
import Monsters.Imp;
import Monsters.Knight;

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
            case 3:
            	return new Devourer();
            case 4:
                return new Dragon();
            case 5:
                return new Dwarves();
            case 6:
                return new Flame_Demons();
            case 7:
            	return new Ghost();
            case 8:
                return new Ghoul();
            case 9:
                return new Giant_Bat();
            case 10:
                return new Gladiator();
            case 11:
            	return new Gnoll();
            case 12:
                return new Gnome();
            case 13:
                return new Goblin();
            case 14:
                return new Gremlin();
            case 15:
            	return new Guard();
            case 16:
            	return new Homunculi();
            case 17:
                return new Horned_Devil();
            case 18:
                return new Ice_Demon();
            case 19:
                return new Imp();
            case 20:
            	return new Knight();
            default:
                return null;
        }
    }
}