
package DungeonoftheBrutalKing;

import java.util.Random;

// Importing all monster classes
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
        // Create a Random object to generate random numbers
        Random random = new Random();

        // Generate a random index between 0 and 21
        int index = random.nextInt(21);

        // Use a switch statement to return a monster based on the random index
        switch (index) {
            case 0:
                return new Assassin(); // Return an Assassin instance
            case 1:
                return new Brigand(); // Return a Brigand instance
            case 2:
                return new Cutthroat(); // Return a Cutthroat instance
            case 3:
                return new Devourer(); // Return a Devourer instance
            case 4:
                return new Dragon(); // Return a Dragon instance
            case 5:
                return new Dwarves(); // Return a Dwarves instance
            case 6:
                return new Flame_Demon(); // Return a Flame_Demon instance
            case 7:
                return new Ghost(); // Return a Ghost instance
            case 8:
                return new Ghoul(); // Return a Ghoul instance
            case 9:
                return new Giant_Bat(); // Return a Giant_Bat instance
            case 10:
                return new Gladiator(); // Return a Gladiator instance
            case 11:
                return new Gnoll(); // Return a Gnoll instance
            case 12:
                return new Gnome(); // Return a Gnome instance
            case 13:
                return new Goblin(); // Return a Goblin instance
            case 14:
                return new Gremlin(); // Return a Gremlin instance
            case 15:
                return new Guard(); // Return a Guard instance
            case 16:
                return new Homunculi(); // Return a Homunculi instance
            case 17:
                return new Horned_Devil(); // Return a Horned_Devil instance
            case 18:
                return new Ice_Demon(); // Return an Ice_Demon instance
            case 19:
                return new Imp(); // Return an Imp instance
            case 20:
                return new Knight(); // Return a Knight instance
            case 21:
                return new Liches(); // Return a Liches instance
            case 22:
                return new Mage(); // Return a Mage instance
            case 23:
                return new Master_Thief(); // Return a Master_Thief instance
            case 24:
                return new Mold(); // Return a Mold instance
            case 25:
                return new Night_Stalker(); // Return a Night_Stalker instance
            case 26:
                return new Noblemen(); // Return a Noblemen instance
            case 27:
                return new Orc(); // Return an Orc instance
            case 28:
                return new Phoenix(); // Return a Phoenix instance
            case 29:
                return new Rat(); // Return a Rat instance
            case 30:
                return new Salamander(); // Return a Salamander instance
            case 31:
                return new Serpentmen(); // Return a Serpentmen instance
            case 32:
                return new Skeleton(); // Return a Skeleton instance
            case 33:
                return new Slime(); // Return a Slime instance
            case 34:
                return new Spectre(); // Return a Spectre instance
            case 35:
                return new Spider(); // Return a Spider instance
            case 36:
                return new Storm_Devil(); // Return a Storm_Devil instance
            case 38:
                return new Thief(); // Return a Thief instance
            case 39:
                return new Troll(); // Return a Troll instance
            case 40:
                return new Valkyrie(); // Return a Valkyrie instance
            case 41:
                return new Vampire(); // Return a Vampire instance
            case 42:
                return new Whirlwind(); // Return a Whirlwind instance
            case 43:
                return new Wizard(); // Return a Wizard instance
            case 44:
                return new Wolf(); // Return a Wolf instance
            case 45:
                return new Wraith(); // Return a Wraith instance
            default:
                return null; // Return null if no case matches
        }
    }
}
