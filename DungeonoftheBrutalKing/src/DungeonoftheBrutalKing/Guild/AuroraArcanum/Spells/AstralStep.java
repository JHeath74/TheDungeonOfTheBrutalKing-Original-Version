package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;

import java.util.List;
import java.util.Random;

public class AstralStep implements Spell {
    private static final int MAX_DISTANCE = 3;
    private static final int DUNGEON_WIDTH = 128;
    private static final int DUNGEON_HEIGHT = 128;
    private static final int REQUIRED_MAGIC_POINTS = 7;
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private boolean canUseSpell(Charecter caster) {
        return caster != null && caster.getGuild() == Guild.AURORA_ARCANUM;
    }

    @Override
    public void cast(Charecter caster) {
        if (!canUseSpell(caster)) return;
        int[] pos = new int[3];
        caster.getPosition(pos);
        Random rand = new Random();

        int attempts = 0;
        int newX = pos[0], newY = pos[1];
        boolean found = false;

        while (attempts < 10) {
            int dx = 0, dy = 0;
            while (dx == 0 && dy == 0) {
                dx = rand.nextInt(2 * MAX_DISTANCE + 1) - MAX_DISTANCE;
                dy = rand.nextInt(2 * MAX_DISTANCE + 1) - MAX_DISTANCE;
            }
            newX = pos[0] + dx;
            newY = pos[1] + dy;
            if (newX >= 0 && newX < DUNGEON_WIDTH && newY >= 0 && newY < DUNGEON_HEIGHT) {
                found = true;
                break;
            }
            attempts++;
        }

        if (found) {
            caster.setPosition(newX, newY, pos[2]);
        }
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        cast(caster);
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.AURORA_ARCANUM;
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    @Override
    public void cast(Charecter caster, Charecter target) {
        cast(caster);
    }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public String getName() {
        return "Astral Step";
    }

    @Override
    public void cast() { }

    @Override
    public String getDescription() {
        return "Astral Step: Teleports the caster a short distance within the dungeon. Only available to AuroraArcanum guild members.";
    }

    @Override
    public void castWithStrength(Charecter enemy, double d) { }
}
