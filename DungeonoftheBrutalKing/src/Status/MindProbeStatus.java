
// src/Status/MindProbeStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public final class MindProbeStatus extends Status {
    private final double evadeBonus;
    private boolean applied;

    public MindProbeStatus(int duration, double evadeBonus) {
        super("Mind Probe", Math.max(0, duration), StatusPolarity.POSITIVE, StatusType.MIND_PROBE_STATUS);
        this.evadeBonus = evadeBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        if (character == null) return;
        if (applied) return;

        character.setEvadeChance(character.getEvadeChance() + evadeBonus);
        applied = true;
    }

    @Override
    public void removeEffect(Charecter character) {
        if (character == null) return;
        if (!applied) return;

        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
        applied = false;
    }

    @Override
    public String getDescription() {
        return "Mind Probe: increases evade chance while active\\.";
    }
}
