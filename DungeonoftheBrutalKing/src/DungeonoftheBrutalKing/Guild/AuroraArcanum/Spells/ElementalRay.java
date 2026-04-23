
// src/Guild/AuroraArcanum/Spells/ElementalRay.java
package DungeonoftheBrutalKing.Guild.AuroraArcanum.Spells;

import DungeonoftheBrutalKing.Character;
import DungeonoftheBrutalKing.Enemies.Enemies;
import DungeonoftheBrutalKing.SharedData.Guild;
import DungeonoftheBrutalKing.Spells.Spell;
import DungeonoftheBrutalKing.Status.FireStatus;
import DungeonoftheBrutalKing.Status.IceStatus;
import DungeonoftheBrutalKing.Status.StunStatus;

import java.util.List;
import java.util.Random;

public class ElementalRay implements Spell {
    public enum Element { FIRE, ICE, LIGHTNING }
    private static final Guild SPELL_GUILD = Guild.AURORA_ARCANUM;

    private Element currentElement;
    private final Random random = new Random();

    private boolean canUseSpell(Character caster) {
        return caster != null && caster.getGuild() == SPELL_GUILD;
    }

    @Override
    public void cast(Character caster, Character target) {
        if (!canUseSpell(caster) || target == null) return;
        currentElement = Element.values()[random.nextInt(Element.values().length)];
        applyEffect(caster, target, currentElement);
    }

    private void applyEffect(Character caster, Character target, Element element) {
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
        return SPELL_GUILD;
    }

    @Override
    public int getRequiredMagicPoints() {
        return 10;
    }

    @Override
    public void cast(int toonWisdom) { }

    @Override
    public void castWithIntelligence(int toonIntelligence) { }

    @Override
    public void cast(int toonWisdom, int toonIntelligence) { }

    @Override
    public String getName() {
        return "Elemental Ray";
    }

    @Override
    public void cast(Character caster, List<Character> allCharacters) {
        if (canUseSpell(caster) && allCharacters != null && !allCharacters.isEmpty()) {
            cast(caster, allCharacters.get(0));
        }
    }

    @Override
    public void cast(Character caster) { }

    @Override
    public void cast() { }

    @Override
    public String getDescription() {
        return "Elemental Ray: Fires a random elemental beam (fire, ice, or lightning) at a target. Only available to AuroraArcanum guild members.";
    }

    @Override
    public void castWithStrength(Character enemy, double strength) {
        // Not applicable for this spell, so do nothing
    }

	@Override
	public void cast(Character caster, Enemies target) {
		// TODO Auto-generated method stub
		
	}
}
