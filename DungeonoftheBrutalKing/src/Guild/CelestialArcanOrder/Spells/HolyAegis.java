package Guild.CelestialArcanOrder.Spells;

import DungeonoftheBrutalKing.Charecter;
import Spells.Spell;

public class HolyAegis extends Spell {

    private static final String NAME = "Holy Aegis";
    private static final String DESCRIPTION = "A radiant shield surrounds the cleric, deflecting arrows and reducing incoming damage.";
    private static final int MANA_COST = 15;
    private static final int DURATION = 5; // turns

    public HolyAegis() {
        super(NAME, DESCRIPTION, MANA_COST);
    }

    @Override
    public void cast(Charecter caster) {
        if (caster.getMana() < MANA_COST) {
            caster.sendMessage("Not enough mana to cast " + NAME + "!");
            return;
        }
        caster.reduceMana(MANA_COST);
        caster.applyEffect("HolyAegis", DURATION);
        caster.sendMessage("A radiant shield surrounds you, deflecting arrows and reducing damage for " + DURATION + " turns!");
    }
}
