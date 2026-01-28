package Guild.SilverwardSentinels.Spells;

import java.util.List;
import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;

public class Location implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final int MIN_WISDOM = 30;
    private static final int MIN_INTELLIGENCE = 30;
    private final String name;

    public Location() {
        this.name = "Location";
    }

    @Override
    public String getName() {
        return name;
    }

    // Custom method for dungeon teleportation
    public void cast(int[][][] dungeon, int targetX, int targetY, int targetZ) {
        Charecter myChar = Singleton.myCharSingleton();
        int wisdom = Integer.parseInt(myChar.getCharInfo().get(10));
        int intelligence = Integer.parseInt(myChar.getCharInfo().get(9));
        int magicPoints = Integer.parseInt(myChar.getCharInfo().get(5));

        if ((wisdom >= MIN_WISDOM || intelligence >= MIN_INTELLIGENCE) && magicPoints >= REQUIRED_MAGIC_POINTS) {
            int[] position = new int[3];
            myChar.getPosition(position);
            int currentX = position[0];
            int currentY = position[1];
            int currentZ = position[2];

            dungeon[currentZ][currentY][currentX] = 0; // Clear current position
            dungeon[targetZ][targetY][targetX] = 1; // Set new position

            myChar.setPosition(targetX, targetY, targetZ);

            System.out.println("You teleport from (" + currentX + ", " + currentY + ", " + currentZ + ") to (" + targetX + ", " + targetY + ", " + targetZ + ")!");
        } else {
            System.out.println("You don't have enough Magic Points, Wisdom, or Intelligence to cast this spell.");
        }
    }

    @Override
    public void cast() {
        System.out.println("You sense your current location, but need a target to teleport.");
    }

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom >= MIN_WISDOM) {
            System.out.println("You focus your wisdom to sense your location.");
        } else {
            System.out.println("You lack the wisdom to use this spell.");
        }
    }

    @Override
    public void cast(Charecter caster) {
        if (caster != null) {
            cast(caster.getWisdom());
        } else {
            cast();
        }
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster != null) {
            cast(caster.getWisdom());
            // Optionally: teleport target or reveal their location
        } else {
            cast();
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> targets) {
        if (targets != null) {
            for (Charecter target : targets) {
                cast(caster, target);
            }
        }
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        if (intelligence >= MIN_INTELLIGENCE) {
            System.out.println("You use your intelligence to calculate your position.");
        } else {
            System.out.println("You lack the intelligence to use this spell.");
        }
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom >= MIN_WISDOM || toonIntelligence >= MIN_INTELLIGENCE) {
            System.out.println("You combine wisdom and intelligence to sense or change your location.");
        } else {
            System.out.println("You lack the wisdom or intelligence to use this spell.");
        }
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    @Override
    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }
}
