
// src/Guild/AuroraArcanum/Spells/ElementalRay.java
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import SharedData.Guild;
import Spells.Spell;
import Status.FireStatus;
import Status.IceStatus;
import Status.StunStatus;

import java.util.List;
import java.util.Random;

public class ElementalRay implements Spell {
    public enum Element { FIRE, ICE, LIGHTNING }

    private Element currentElement;
    private final Random random = new Random();

    // Main spell logic: fires the beam at a target
    @Override
    public void cast(Charecter caster, Charecter target) {
        if (caster == null || target == null) return;
        currentElement = Element.values()[random.nextInt(Element.values().length)];
        applyEffect(caster, target, currentElement);
    }

    // Applies the effect based on the element
    private void applyEffect(Charecter caster, Charecter target, Element element) {
        switch (element) {
            case FIRE:
                int fireDamage = 15 + caster.getIntelligence();
                target.setHitPoints(target.getHitPoints() - fireDamage);
                new FireStatus().applyEffect(target);
                break;
            case ICE:
                int iceDamage = 10 + (int)(caster.getIntelligence() * 0.8);
                target.setHitPoints(target.getHitPoints() - iceDamage);
                new IceStatus().applyEffect(target);
                break;
            case LIGHTNING:
                int lightningDamage = 12 + (int)(caster.getIntelligence() * 1.2);
                target.setHitPoints(target.getHitPoints() - lightningDamage);
                new StunStatus(lightningDamage).applyEffect(target);
                break;
        }
    }

    public Element getCurrentElement() {
        return currentElement;
    }

    @Override
    public boolean isGuildSpell() {
        return true;
    }

    @Override
    public Guild getSpellGuild() {
        return Guild.AURORA_ARCANUM;
    }

    @Override
    public int getRequiredMagicPoints() {
        return 10;
    }

    @Override
    public void cast(int toonWisdom) {
        // No target, so nothing happens
    }

    @Override
    public void castWithIntelligence(int toonIntelligence) {
        // No target, so nothing happens
    }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) {
        // No target, so nothing happens
    }

    @Override
    public String getName() {
        return "Elemental Ray";
    }

    @Override
    public void cast(Charecter caster, List<Charecter> allCharacters) {
        // Cast on the first target in the list (if any)
        if (caster != null && allCharacters != null && !allCharacters.isEmpty()) {
            cast(caster, allCharacters.get(0));
        }
    }

    @Override
    public void cast(Charecter caster) {
        // No target, so nothing happens
    }

    @Override
    public void cast() {
        // No caster or target, so nothing happens
    }
}
