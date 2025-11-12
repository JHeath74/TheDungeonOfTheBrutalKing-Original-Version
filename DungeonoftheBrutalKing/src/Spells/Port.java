package Spells;

import Alignment.Alignment;
import DungeonoftheBrutalKing.Singleton;

public abstract class Port implements Spell {

    private static final Alignment SPELL_ALIGNMENT = Alignment.NOT_ALIGNED; // Alignment of the spell
    String name = null;
    int Wisdom = Integer.parseInt(Singleton.myCharSingleton().getCharInfo().get(10));
    int Intelligence = Integer.parseInt(Singleton.myCharSingleton().getCharInfo().get(9));
    int MagicPoints = Integer.parseInt(Singleton.myCharSingleton().getCharInfo().get(5));

    int minWisdom = 30;
    int minIntelligence = 30;

    public Port() {
        this.name = "Teleport";
    }

    public void cast(int[][][] dungeon, int targetX, int targetY, int targetZ) {
        if (Wisdom > minWisdom || Intelligence > minIntelligence && MagicPoints > 5) {

            int[] position = new int[3];
            Singleton.myCharSingleton().getPosition(position);

            int currentX = position[0];
            int currentY = position[1];
            int currentZ = position[2];

            dungeon[currentZ][currentY][currentX] = 0; // Clear current position
            dungeon[targetZ][targetY][targetX] = 1; // Set new position

            Singleton.myCharSingleton().setPosition(targetX, targetY, targetZ);

            System.out.println("Teleported to (" + targetX + ", " + targetY + ", 0)!");
        } else {
            System.out.println("You Don't have enough Magic Points, or either Wisdom or Intelligence to Cast this spell");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }

    @Override
    public Alignment getSpellAlignment() {
        return SPELL_ALIGNMENT;
    }
}
