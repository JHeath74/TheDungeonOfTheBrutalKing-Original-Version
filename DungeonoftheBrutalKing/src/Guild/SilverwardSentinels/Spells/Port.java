package Guild.SilverwardSentinels.Spells;

import SharedData.Guild;
import Spells.Spell;
import java.util.List;
import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.Singleton;

public class Port implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 6;
    private static final int MIN_WISDOM = 30;
    private static final int MIN_INTELLIGENCE = 30;
    private static final String NAME = "Port";

    public Port() {}

    // Custom method for dungeon teleportation
    public void cast(int[][][] dungeon, int targetX, int targetY, int targetZ) {
        Charecter character = Singleton.myCharSingleton();
        int wisdom = character.getWisdom();
        int intelligence = character.getIntelligence();
        int magicPoints = character.getMagicPoints();

        if ((wisdom >= MIN_WISDOM || intelligence >= MIN_INTELLIGENCE) && magicPoints >= REQUIRED_MAGIC_POINTS) {
            int[] position = new int[3];
            character.getPosition(position);

            int currentX = position[0];
            int currentY = position[1];
            int currentZ = position[2];

            dungeon[currentZ][currentY][currentX] = 0; // Clear current position
            dungeon[targetZ][targetY][targetX] = 1; // Set new position

            character.setPosition(targetX, targetY, targetZ);

            System.out.println("Teleported to (" + targetX + ", " + targetY + ", " + targetZ + ")!");
        } else {
            System.out.println("You don't have enough Magic Points, or either Wisdom or Intelligence to cast this spell.");
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void cast() {
        System.out.println("You sense the possibility of teleportation, but need a target location.");
    }

    @Override
    public void cast(int attackerWisdom) {
        if (attackerWisdom >= MIN_WISDOM) {
            System.out.println("You focus your wisdom to attempt teleportation.");
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
            System.out.println("You use your intelligence to calculate a teleportation route.");
        } else {
            System.out.println("You lack the intelligence to use this spell.");
        }
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        if (toonWisdom >= MIN_WISDOM || toonIntelligence >= MIN_INTELLIGENCE) {
            System.out.println("You combine wisdom and intelligence to attempt teleportation.");
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
