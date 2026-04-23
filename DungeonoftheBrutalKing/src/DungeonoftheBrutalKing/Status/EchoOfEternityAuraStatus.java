
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Spells.Spell;

public final class EchoOfEternityAuraStatus extends Status {
    private final Character caster;
    private static final double HASTE_BONUS = 0.10;

    public EchoOfEternityAuraStatus(int durationMinutes, Character caster) {
        super("Echo of Eternity Aura", durationMinutes, StatusPolarity.POSITIVE, StatusType.ECHO_OF_ETERNITY_STATUS);
        this.caster = caster;
    }

    @Override
    public void applyEffect(Character target) {
        if (target == null) return;
        target.addHasteModifier(HASTE_BONUS);
    }

    @Override
    public void removeEffect(Character target) {
        if (target == null) return;
        target.removeHasteModifier(HASTE_BONUS);
    }

    public void onEnemySpellCast(Character enemy, Spell spell) {
        if (enemy == null || spell == null) return;
        if (caster == null) return; // ensures field is used + avoids aura logic without a source

        if (isInAura(enemy)) {
            spell.castWithStrength(enemy, 0.15);
        }
    }

    private boolean isInAura(Character ch) {
        return true;
    }
}
