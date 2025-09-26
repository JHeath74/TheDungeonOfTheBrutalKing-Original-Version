package Spells;

import DungeonoftheBrutalKing.Charecter;
import DungeonoftheBrutalKing.TimeClock;
import Effect.EffectManager;
import SharedData.Alignment;

public abstract class Shield implements Spell {

    private static final Alignment SPELL_ALIGNMENT = Alignment.NOT_ALIGNED;
    private int duration = 12;
    private TimeClock timeClock = TimeClock.Singleton();
    private EffectManager effectManager = EffectManager.Singleton();

    public Shield() {}

    public void cast() {
        int startTime = timeClock.getCurrentHour();
        Charecter character = Charecter.getInstance();

        int baseDefense = 10;
        int agility = Integer.parseInt(character.getCharInfo().get(10));
        int dexterityModifier = (agility - 10) / 2;

        int armorBonus = Integer.parseInt(character.getCharInfo().get(19));
        int shieldBonus = Integer.parseInt(character.getCharInfo().get(20));
        int extraDefense = 10;

        int totalDefense = baseDefense + dexterityModifier + armorBonus + shieldBonus + extraDefense;
        character.setDefense(totalDefense);

        effectManager.registerEffect("Shield", startTime, duration, this::removeSpellEffect);
    }

    private void removeSpellEffect() {
        Charecter character = Charecter.getInstance();

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
