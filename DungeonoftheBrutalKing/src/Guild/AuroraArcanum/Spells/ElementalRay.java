
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
import Status.FireStatus;
import Status.IceStatus;
import Status.StunStatus;
import java.util.Random;

public class ElementalRay {
    public enum Element { FIRE, ICE, LIGHTNING }

    private Element currentElement;
    private final Random random = new Random();

    // Call this to fire the beam at a target
    public void activate(Charecter caster, Charecter target) {
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
}
