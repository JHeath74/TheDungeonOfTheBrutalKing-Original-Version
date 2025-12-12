
package Status;

import DungeonoftheBrutalKing.Charecter;

public class MindProbeStatus extends Status {
    private final double evadeBonus;

    public MindProbeStatus(int duration, double evadeBonus) {
        super("Mind Probe", duration);
        this.evadeBonus = evadeBonus;
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() + evadeBonus);
        reduceDuration(1);
    }

    @Override
    public void onExpire(Charecter character) {
        character.setEvadeChance(character.getEvadeChance() - evadeBonus);
    }
}
