
package Status;

import DungeonoftheBrutalKing.Charecter;
import Spells.Spell;

public class EchoOfEternityAuraStatus extends Status {
    private final Charecter caster;
    private static final double HASTE_BONUS = 0.10;

    public EchoOfEternityAuraStatus(int duration, Charecter caster) {
        super("Echo of Eternity Aura", duration, false, StatusType.ECHO_OF_ETERNITY_STATUS); // Add StatusType
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

    public void onEnemySpellCast(Charecter enemy, Spell spell) {
        if (isInAura(enemy)) {
            spell.castWithStrength(enemy, 0.15); // Reflect at 15% strength back at the enemy
        }
    }

    private boolean isInAura(Charecter ch) {
        // Implement distance check if needed, or always true if global
        return true;
    }
}
