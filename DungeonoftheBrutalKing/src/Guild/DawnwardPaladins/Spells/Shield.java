
// src/Spells/Shield.java
package Guild.DawnwardPaladins.Spells;

import SharedData.Guild;
import Spells.Spell;
import DungeonoftheBrutalKing.Character;
import java.util.Timer;
import java.util.TimerTask;

public class Shield implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final int SHIELD_DURATION_MS = 30_000; // 30 seconds

    public Shield() {}

    public void cast() {
        Character character = Character.getInstance();

        int baseDefense = 10;
        int agility = Integer.parseInt(character.getCharInfo().get(10));
        int dexterityModifier = (agility - 10) / 2;

        int armorBonus = Integer.parseInt(character.getArmour());
        int shieldBonus = Integer.parseInt(character.getShield());
        int extraDefense = 10;

        int totalDefense = baseDefense + dexterityModifier + armorBonus + shieldBonus + extraDefense;
        character.setDefense(totalDefense);

        // Schedule removal after 30 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeSpellEffect();
            }
        }, SHIELD_DURATION_MS);
    }

    protected void removeSpellEffect() {
        Character character = Character.getInstance();

        int baseDefense = 10;
        int agility = Integer.parseInt(character.getCharInfo().get(10));
        int dexterityModifier = (agility - 10) / 2;

        int armorBonus = Integer.parseInt(character.getArmour());
        int shieldBonus = Integer.parseInt(character.getShield());
        int extraDefense = 0;

        int totalDefense = baseDefense + dexterityModifier + armorBonus + shieldBonus + extraDefense;
        character.setDefense(totalDefense);
    }

    @Override
    public boolean isGuildSpell() {
        return SPELL_GUILD != Guild.NON_GUILD;
    }

    public Guild getSpellGuild() {
        return SPELL_GUILD;
    }


    public void cast(int attackerWisdom) {
        // Not used for this spell
    }

    @Override
    public int getRequiredMagicPoints() {
        return REQUIRED_MAGIC_POINTS;
    }

    public void cast(int toonWisdom, int toonIntelligence) {
        // Not used for this spell
    }
}
