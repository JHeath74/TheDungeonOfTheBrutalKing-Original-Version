
// src/Status/MindProbeStatus.java
package Status;

import DungeonoftheBrutalKing.Charecter;

public class MindProbeStatus extends Status {
    private final double evadeBonus;

    public MindProbeStatus(int duration, double evadeBonus) {
        super("Mind Probe", duration, false, StatusType.MIND_PROBE_STATUS); // Added StatusType
        this.evadeBonus = evadeBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() + evadeBonus);
    }

    @Override
    public void expireEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
    }

    @Override
    public void removeEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
    }
}
