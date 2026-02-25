
package Status;

import DungeonoftheBrutalKing.Charecter;
import Enemies.Enemies;

/**
 * Applies a lightning shock effect for a fixed number of turns.
 * Adjust method names (e.g., takeDamage/receiveDamage/addStatus) to match your codebase.
 */
public class LightningStatus extends Status {

    private int remainingTurns;
    private final int damagePerTurn;

    public LightningStatus(int turns) {
        this(turns, 1);
    }

    public LightningStatus(int turns, int damagePerTurn) {
        this.remainingTurns = Math.max(0, turns);
        this.damagePerTurn = Math.max(0, damagePerTurn);
    }

    @Override
    public StatusType getStatusType() {
        return StatusType.LIGHTNING_STATUS;
    }

    @Override
    public boolean isExpired() {
        return remainingTurns <= 0;
    }

    @Override
    public void onApply(Enemies target) {
        // Optional: hook for messaging or immediate effects
    }

    @Override
    public void onTurnStart(Enemies target) {
        if (isExpired() || target == null || target.isDead()) return;

        if (damagePerTurn > 0) {
            // Use the correct damage method in your project:
            // target.takeDamage(damagePerTurn);
            // target.receiveDamage(damagePerTurn);
        }

        remainingTurns--;
    }

    @Override
    public void onRemove(Enemies target) {
        // Optional cleanup
    }

    @Override
    public String getName() {
        return "Lightning";
    }

    @Override
    public String getDescription() {
        return "Shocked: takes lightning damage each turn.";
    }
}
