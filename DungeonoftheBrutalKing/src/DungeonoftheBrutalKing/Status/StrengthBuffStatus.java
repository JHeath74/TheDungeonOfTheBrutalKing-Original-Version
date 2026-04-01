
// src/Status/StrengthBuffStatus.java
package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Charecter;

public final class StrengthBuffStatus extends Status {
    private final int bonus;

    public StrengthBuffStatus(int bonus, int durationMinutes) {
        super("Strength Buff", durationMinutes, StatusPolarity.POSITIVE, StatusType.STRENGTH_BUFF_STATUS);
        this.bonus = Math.max(0, bonus);
    }

    @Override
    public void applyEffect(Charecter target) {
        if (target == null || bonus == 0) return;
        target.setStrength(target.getStrength() + bonus);
    }

    @Override
    public void removeEffect(Charecter target) {
        if (target == null || bonus == 0) return;
        target.setStrength(target.getStrength() - bonus);
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public String getDescription() {
        return "Strength Buff: increases strength by " + bonus + " while active.";
    }
}
