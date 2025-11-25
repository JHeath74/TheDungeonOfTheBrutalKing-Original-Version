
package Spells;

import SharedData.Alignment;
import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.TimeClock;

public abstract class Shield implements Spell {

    private static final Alignment SPELL_ALIGNMENT = Alignment.NEUTRAL;
    private int duration = 12;
    private TimeClock timeClock = TimeClock.Singleton();

    public Shield() {}

    public void cast() {
        int startTime = timeClock.getCurrentHour();
        Character character = Character.getInstance();

        int baseDefense = 10;
        int agility = Integer.parseInt(character.getCharInfo().get(10));
        int dexterityModifier = (agility - 10) / 2;

        int armorBonus = Integer.parseInt(character.getCharInfo().get(19));
        int shieldBonus = Integer.parseInt(character.getCharInfo().get(20));
        int extraDefense = 10;

        int totalDefense = baseDefense + dexterityModifier + armorBonus + shieldBonus + extraDefense;
        character.setDefense(totalDefense);

        // TODO: Implement effect duration tracking and removal if needed
    }

    // Call this method when the shield effect should end
    protected void removeSpellEffect() {
        Character character = Character.getInstance();

        int baseDefense = 10;
        int agility = Integer.parseInt(character.getCharInfo().get(10));
        int dexterityModifier = (agility - 10) / 2;

        int armorBonus = Integer.parseInt(character.getCharInfo().get(19));
        int shieldBonus = Integer.parseInt(character.getCharInfo().get(20));
        int extraDefense = 0;

        int totalDefense = baseDefense + dexterityModifier + armorBonus + shieldBonus + extraDefense;
        character.setDefense(totalDefense);
    }

    @Override
    public boolean isGuildSpell() {
        return false;
    }

    public Alignment getSpellAlignment() {
        return SPELL_ALIGNMENT;
    }
}
