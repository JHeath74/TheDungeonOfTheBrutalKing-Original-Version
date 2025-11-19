package Spells;

import Alignment.Alignment;
import DungeonoftheBrutalKing.Character;

public abstract class Light implements Spell {

    public Light() {
        super();

        String name = "Light";
        int requiredint = 30;
        int requiredwis = 30;

        String charintelligence = Character.getInstance().getCharInfo().get(8).toString();
        String charwisdom = Character.getInstance().getCharInfo().get(9).toString();
    }

    @Override
    public void cast() {
        System.out.println("Casting the Light spell...");
        // Add specific behavior for the Light spell here
    }

    @Override
    public boolean isGuildSpell() {
        return false; // Explicitly mark this as a non-guild spell
    }

    @Override
    public Alignment getSpellAlignment() {
        return Alignment.NOT_ALIGNED;
    }
}
