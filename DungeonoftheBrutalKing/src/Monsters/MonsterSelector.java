package Monsters;

import java.util.Random;

import DungeonoftheBrutalKing.Enemies;

public class MonsterSelector {

    public static Enemies selectRandomMonster() {
        Random random = new Random();
        int index = random.nextInt(45); // 0 to 44 inclusive

        switch (index) {
            case 0:  return new Assassin();
            case 1:  return new Brigand();
            case 2:  return new Cutthroat();
            case 3:  return new Devourer();
            case 4:  return new Dragon();
            case 5:  return new Dwarves();
            case 6:  return new Flame_Demon();
            case 7:  return new Ghost();
            case 8:  return new Ghoul();
            case 9:  return new Giant_Bat();
            case 10: return new Gladiator();
            case 11: return new Gnoll();
            case 12: return new Gnome();
            case 13: return new Goblin();
            case 14: return new Gremlin();
            case 15: return new Guard();
            case 16: return new Homunculi();
            case 17: return new Horned_Devil();
            case 18: return new Ice_Demon();
            case 19: return new Imp();
            case 20: return new Knight();
            case 21: return new Liches();
            case 22: return new Mage();
            case 23: return new Master_Thief();
            case 24: return new Mold();
            case 25: return new Night_Stalker();
            case 26: return new Noblemen();
            case 27: return new Orc();
            case 28: return new Phoenix();
            case 29: return new Rat();
            case 30: return new Salamander();
            case 31: return new Serpentmen();
            case 32: return new Skeleton();
            case 33: return new Slime();
            case 34: return new Spectre();
            case 35: return new Spider();
            case 36: return new Storm_Devil();
            case 37: return new Thief();
            case 38: return new Troll();
            case 39: return new Valkyrie();
            case 40: return new Vampire();
            case 41: return new Whirlwind();
            case 42: return new Wizard();
            case 43: return new Wolf();
            case 44: return new Wraith();
            default: return new Goblin();
        }
    }
}
