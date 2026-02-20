
// src/Status/ManaRegenStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class ManaRegenStatus extends Status {
    private final int regenAmount;

    public ManaRegenStatus(int durationMinutes, int regenAmount) {
        super("Mana Regeneration", durationMinutes, false, StatusType.MANA_REGEN_STATUS);
        this.regenAmount = regenAmount;
    }

    @Override
    public void applyStatusEffect(Charecter target) {
        if (target != null) {
            target.setManaRegenBonus(target.getManaRegenBonus() + regenAmount);
        }
    }

    @Override
    public void expireEffect(Charecter target) {
        if (target != null) {
            target.setManaRegenBonus(target.getManaRegenBonus() - regenAmount);
        }
    }

    @Override
    public String getDescription() {
        return "Increases mana regeneration by " + regenAmount + " per turn.";
    }
}
