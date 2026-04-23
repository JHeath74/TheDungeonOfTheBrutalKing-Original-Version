
// src/Guild/DawnwardPaladins/Spells/Shield.java
package DungeonoftheBrutalKing.Guild.DawnwardPaladins.Spells;

import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Shield implements Spell {

    private static final Guild SPELL_GUILD = Guild.NON_GUILD;
    private static final int REQUIRED_MAGIC_POINTS = 8;
    private static final int SHIELD_DURATION_MS = 30_000; // 30 seconds
    private static final String SPELL_NAME = "Shield";

    public Shield() {}

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

    @Override
    public String getName() {
        return SPELL_NAME;
    }

    // Main spell effect for singleton character
    public void cast() {
        Character character = Character.getInstance();
        applyShieldEffect(character);
    }

    @Override
    public void cast(Character caster) {
        if (caster != null) {
            applyShieldEffect(caster);
        }
    }

    @Override
    public void cast(Character caster, Character target) {
        if (target != null) {
            applyShieldEffect(target);
        }
    }

    @Override
    public void cast(Character caster, List<Character> targets) {
        if (targets != null) {
            for (Character target : targets) {
                if (target != null) {
                    applyShieldEffect(target);
                }
            }
        }
    }

    @Override
    public void castWithIntelligence(int intelligence) {
        // Shield spell does not use intelligence for its effect.
    }

    @Override
    public void cast(int toonWisdom) {
        // Shield spell does not use wisdom for its effect.
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // Shield spell does not use wisdom or intelligence for its effect.
    }

    // Helper: applies shield effect and schedules removal
    private void applyShieldEffect(Character character) {
        int totalDefense = calculateDefense(character, true);
        character.setDefense(totalDefense);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeShieldEffect(character);
                timer.cancel();
            }
        }, SHIELD_DURATION_MS);
    }

    // Helper: removes shield effect
    private void removeShieldEffect(Character character) {
        int totalDefense = calculateDefense(character, false);
        character.setDefense(totalDefense);
    }

    // Helper: calculates defense, with or without shield bonus
    private int calculateDefense(Character character, boolean withShieldBonus) {
        int baseDefense = 10;
        int agility = parseIntSafe(character.getCharInfo().get(10));
        int dexterityModifier = (agility - 10) / 2;
        int armorBonus = parseIntSafe(character.getArmour());
        int shieldBonus = parseIntSafe(character.getShield());
        int extraDefense = withShieldBonus ? 10 : 0;
        return baseDefense + dexterityModifier + armorBonus + shieldBonus + extraDefense;
    }

    // Helper: safely parses integer, returns 0 if null or invalid
    private int parseIntSafe(String value) {
        try {
            return value != null ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void castWithStrength(Character enemy, double d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
