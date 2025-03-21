package DungeonoftheBrutalKing;

import java.util.Random;
import Monsters.Assassin;
import Monsters.Brigand;
import Monsters.Cutthroat;
import Monsters.Devourer;
import Monsters.Dragon;
import Monsters.Dwarves;
import Monsters.Flame_Demon;
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
import Monsters.Liches;
import Monsters.Mage;
import Monsters.Master_Thief;
import Monsters.Mold;
import Monsters.Night_Stalker;
import Monsters.Noblemen;
import Monsters.Orc;
import Monsters.Phoenix;
import Monsters.Rat;
import Monsters.Salamander;
import Monsters.Serpentmen;
import Monsters.Skeleton;
import Monsters.Slime;
import Monsters.Spectre;
import Monsters.Spider;
import Monsters.Storm_Devil;
import Monsters.Thief;
import Monsters.Troll;
import Monsters.Valkyrie;
import Monsters.Vampire;
import Monsters.Whirlwind;
import Monsters.Wizard;
import Monsters.Wolf;
import Monsters.Wraith;

class MonsterSelector {

    // Method to select a random monster
    public static Object selectRandomMonster() {
        Random random = new Random();
        int index = random.nextInt(21);
        switch (index) {
            case 0:
                return new Assassin();
            case 1:
                return new Brigand();
            case 2:
                return new Cutthroat();
            case 3:
            	return new Devourer();
            case 4:
                return new Dragon();
            case 5:
                return new Dwarves();
            case 6:
                return new Flame_Demon();
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
            case 21:
                return new Liches();
            case 22:
                return new Mage();
            case 23:
                return new Master_Thief();
            case 24:
            	return new Mold();
            case 25:
                return new Night_Stalker();
            case 26:
                return new Noblemen();
            case 27:
                return new Orc();
            case 28:
            	return new Phoenix(); 	
            case 29:
            	return new Rat(); 
            case 30:
                return new Salamander();
            case 31:
            	return new Serpentmen(); 	
            case 32:
            	return new Skeleton(); 
            case 33:
            	return new Slime(); 
            case 34:
                return new Spectre();
            case 35:
            	return new Spider(); 	
            case 36:
            	return new Storm_Devil(); 
            case 38:
            	return new Thief(); 	
            case 39:
            	return new Troll(); 
            case 40:
            	return new Valkyrie(); 
            case 41:
                return new Vampire();
            case 42:
            	return new Whirlwind(); 	
            case 43:
            	return new Wizard(); 
            case 44:
            	return new Wolf(); 	
            case 45:
            	return new Wraith(); 
            default:
                return null;
        }
    }
}