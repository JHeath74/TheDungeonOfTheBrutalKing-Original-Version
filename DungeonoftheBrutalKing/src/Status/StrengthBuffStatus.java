
package Status;

import DungeonoftheBrutalKing.Charecter;

public class StrengthBuffStatus extends Status {
    private final int bonus;
    private boolean applied;

    public StrengthBuffStatus(int bonus, int duration) {
        super("strength_buff", duration, false, StatusType.StrengthBuff_STATUS);
        this.bonus = Math.max(0, bonus);
        this.applied = false;
    }

    @Override
    public void onApply(Charecter target) {
        if (applied || bonus == 0) return;
        target.setStrength(target.getStrength() + bonus);
        applied = true;
    }

    @Override
    public void onExpire(Charecter target) {
        if (!applied || bonus == 0) return;
        target.setStrength(target.getStrength() - bonus);
        applied = false;
    }

    public int getBonus() {
        return bonus;
    }
}
