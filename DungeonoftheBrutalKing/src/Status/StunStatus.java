package Status;

import DungeonoftheBrutalKing.Charecter;

public class StunStatus extends Status {
    public StunStatus(int duration) {
        super("Stun", duration, true, StatusType.STUN_STATUS);
    }

    @Override
    public void applyEffect(Charecter character) {
        character.setStunned(true);
    }

    @Override
    public void onExpire(Charecter character) {
        character.setStunned(false);
    }
}
