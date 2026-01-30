
package Status;

import DungeonoftheBrutalKing.Charecter;
// import your Spell class if needed
// import DungeonoftheBrutalKing.Spell;

public class EchoOfEternityAuraStatus extends Status {
    private final Charecter caster;
    private static final double HASTE_BONUS = 0.10;

    public EchoOfEternityAuraStatus(int duration, Charecter caster) {
        super("Echo of Eternity Aura", duration, false); // false: positive effect
        this.caster = caster;
    }

    @Override
    public void applyEffect(Charecter character) {
        character.addHasteModifier(HASTE_BONUS);
    }

    @Override
    public void expireEffect(Charecter character) {
        character.removeHasteModifier(HASTE_BONUS);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.removeHasteModifier(HASTE_BONUS);
    }

    // Should be called by the spell system when an enemy casts a spell
    // Uncomment and import Spell if needed
    /*
    public void onEnemySpellCast(Charecter enemy, Spell spell) {
        if (isInAura(enemy)) {
            spell.castWithStrength(enemy, 0.5); // Echo at half strength on the enemy
        }
    }
    */

    private boolean isInAura(Charecter ch) {
        // Implement distance check if needed, or always true if global
        return true;
    }
}
