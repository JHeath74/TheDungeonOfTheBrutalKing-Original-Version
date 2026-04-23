package DungeonoftheBrutalKing.Status;

import DungeonoftheBrutalKing.Character;

public class PerceptionBuffStatus extends Status {

    private final int bonusPerception;
    private int remainingDuration;
    private boolean applied;

    public PerceptionBuffStatus(int durationInTurns, int bonusPerception) {
        super(
            "Perception Buff",
            Math.max(0, durationInTurns),
            StatusPolarity.POSITIVE,
            StatusType.PERCEPTION_BUFF_STATUS
        );

        this.remainingDuration = durationInTurns;
        this.bonusPerception = bonusPerception;
        this.applied = false;
    }

    @Override
    public void applyEffect(Character target) {
        if (target == null || applied) {
            return;
        }
        try {
            int current = target.getPerception();
            target.setPerception(current + bonusPerception);
            applied = true;
            System.out.println(target.getName() + "'s perception sharpens by " + bonusPerception + ".");
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onTurnStart(Character target) {
        if (remainingDuration <= 0) {
            expire(target);
            return;
        }
        remainingDuration--;
        if (remainingDuration <= 0) {
            expire(target);
        }
    }

    @Override
    public void expire(Character target) {
        if (!applied || target == null) {
            return;
        }
        try {
            int current = target.getPerception();
            target.setPerception(current - bonusPerception);
            System.out.println(target.getName() + "'s heightened perception fades.");
        } catch (Exception ignored) {
        } finally {
            applied = false;
        }
    }
}
