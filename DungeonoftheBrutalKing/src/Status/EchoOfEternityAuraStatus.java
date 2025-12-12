
java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class EchoOfEternityAuraStatus extends Status {
    private final Charecter caster;
    private static final double HASTE_BONUS = 0.10;

    public EchoOfEternityAuraStatus(int duration, Charecter caster) {
        super(duration);
        this.caster = caster;
    }

    @Override
    public void onApply(Charecter character) {
        // Grant 10% haste to the caster (player)
        character.addHasteModifier(HASTE_BONUS);
    }

    @Override
    public void onExpire(Charecter character) {
        // Remove the haste bonus when the aura expires
        character.removeHasteModifier(HASTE_BONUS);
    }

    // Should be called by the spell system when an enemy casts a spell
    public void onEnemySpellCast(Charecter enemy, Spell spell) {
        if (isInAura(enemy)) {
            spell.castWithStrength(enemy, 0.5); // Echo at half strength on the enemy
        }
    }

    private boolean isInAura(Charecter ch) {
        // Implement distance check if needed, or always true if global
        return true;
    }

    @Override
    public void applyEffect(Charecter character) {
        // No periodic effect needed
    }
}
