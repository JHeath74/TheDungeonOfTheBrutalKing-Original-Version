
// src/Status/StunStatus.java
package Status;

import DungeonoftheBrutalKing.Character;

public class StunStatus extends Status {
    public StunStatus(int duration) {
        super("Stun", duration);
    }

    @Override
    public void applyEffect(Character character) {
        character.setStunned(true);
    }

    @Override
    public void onExpire(Character character) {
        character.setStunned(false);
    }
}
