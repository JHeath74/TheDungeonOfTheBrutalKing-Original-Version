
package Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Charecter;
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
                // Example: deal extra fire damage
                int fireDamage = 15 + caster.getIntelligence();
                target.setHitPoints(target.getHitPoints() - fireDamage);
                Status.FireStatus.applyEffect(target);
                break;
            case ICE:
                // Example: deal ice damage and slow target
                int iceDamage = 10 + (int)(caster.getIntelligence() * 0.8);
                target.setHitPoints(target.getHitPoints() - iceDamage);
                // Optionally, set a "frozen" or "slowed" status here
                break;
            case LIGHTNING:
                // Example: deal lightning damage and stun target
                int lightningDamage = 12 + (int)(caster.getIntelligence() * 1.2);
                target.setHitPoints(target.getHitPoints() - lightningDamage);
                // Optionally, set a "stunned" status here
                break;
        }
    }

    public Element getCurrentElement() {
        return currentElement;
    }
}
